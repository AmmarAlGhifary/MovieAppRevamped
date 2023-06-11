package com.example.tmdb.ui.seeall

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivitySeeAllBinding
import com.example.tmdb.domain.model.*
import com.example.tmdb.ui.base.BaseActivity
import com.example.tmdb.ui.home.adapter.*
import com.example.tmdb.util.Constants
import com.example.tmdb.util.IntentType
import com.example.tmdb.util.MediaType
import com.example.tmdb.util.playYouTubeVideo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeAllActivity : BaseActivity<ActivitySeeAllBinding>(R.layout.activity_see_all) {

    private val viewModel: SeeAllViewModel by viewModels()

    override val defineBindingVariables: (ActivitySeeAllBinding) -> Unit
        get() = { binding ->
            binding.activity = this
            binding.lifecycleOwner = this
            binding.viewModel = viewModel
            binding.rvSeeAll.layoutManager = GridLayoutManager(this, if (isLandscape || intentType == IntentType.VIDEOS) 2 else 3)
        }

    private var mediaType: Parcelable? = null
    private var listId: String? = null
    private var region: String? = null
    private var list: List<Any>? = null
    private var isLandscape = false

    var intentType: Parcelable? = null
    var title: String? = null

    val movieAdapter by lazy { MoviesAdapter(isGrid = true) }
    val tvAdapter by lazy { TvAdapter(isGrid = true) }
    val personAdapter by lazy { PersonAdapter(isGrid = true) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIntentExtras()
        initBinding()
        getList()
    }

    private fun getIntentExtras() {
        intent.apply {
            intentType = getParcelableExtra(Constants.INTENT_TYPE)
            mediaType = getParcelableExtra(Constants.MEDIA_TYPE)
            listId = getStringExtra(Constants.LIST_ID)
            region = getStringExtra(Constants.REGION)
            list = when (intentType) {
                IntentType.VIDEOS, IntentType.IMAGES, IntentType.CAST, IntentType.PERSON_CREDITS, IntentType.RECOMMENDATIONS -> getParcelableArrayListExtra<Parcelable>(Constants.LIST) as List<Any>
                else -> null
            }
            isLandscape = getBooleanExtra(Constants.IS_LANDSCAPE, false)
            title = getStringExtra(Constants.TITLE) +
                    if (intentType == IntentType.GENRE) {
                        " " + if (mediaType == MediaType.MOVIE) getString(R.string.title_movies) else getString(R.string.title_tv_shows)
                    } else ""
        }
    }

    private fun getList() {
        binding.rvSeeAll.adapter = when (intentType) {
            IntentType.CAST -> PersonAdapter(isGrid = true, isCast = true).apply { submitList(list as List<Person>) }
            IntentType.IMAGES -> ImageAdapter(mediaType == MediaType.PERSON, true).apply { submitList(list as List<Image>) }
            IntentType.VIDEOS -> VideoAdapter(true) { playYouTubeVideo(it) }.apply { submitList(list as List<Video>) }
            IntentType.PERSON_CREDITS -> when (mediaType) {
                MediaType.MOVIE -> MoviesAdapter(isGrid = true, isCredits = true).apply { submitList(list as List<Movie>) }
                MediaType.TV -> TvAdapter(isGrid = true, isCredits = true).apply { submitList(list as List<Tv>) }
                else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_MEDIA_TYPE)
            }
            IntentType.RECOMMENDATIONS -> when (mediaType) {
                MediaType.MOVIE -> MoviesAdapter(isGrid = true).apply { submitList(list as List<Movie>) }
                MediaType.TV -> TvAdapter(isGrid = true).apply { submitList(list as List<Tv>) }
                else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_MEDIA_TYPE)
            }
            else -> {
                when (mediaType) {
                    MediaType.MOVIE -> movieAdapter
                    MediaType.TV -> tvAdapter
                    else -> personAdapter
                }.also { collectFlows(listOf(::collectListResult, ::collectUiState)) }
            }
        }

        viewModel.initRequest(intentType, mediaType, id, listId, region)
    }

    private suspend fun collectListResult() {
        viewModel.results.collect { results ->
            when (mediaType) {
                MediaType.MOVIE -> {
                    val movieList = results as Set<Movie>
                    movieAdapter.submitList(movieList.toList())
                }
                MediaType.TV -> {
                    val tvList = results as Set<Tv>
                    tvAdapter.submitList(tvList.toList())
                }
                MediaType.PERSON -> {
                    val personList = results as Set<Person>
                    personAdapter.submitList(personList.toList())
                }
            }
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) showSnackbar(state.errorText!!, getString(R.string.button_retry)) {
                viewModel.retryConnection {
                    viewModel.initRequest(intentType, mediaType, id, listId, region)
                }
            }
        }
    }
}