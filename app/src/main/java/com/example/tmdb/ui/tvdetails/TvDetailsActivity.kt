package com.example.tmdb.ui.tvdetails

import android.os.Bundle
import androidx.activity.viewModels
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityTvDetailsBinding
import com.example.tmdb.ui.base.BaseActivity
import com.example.tmdb.ui.home.adapter.*
import com.example.tmdb.util.playYouTubeVideo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvDetailsActivity : BaseActivity<ActivityTvDetailsBinding>(R.layout.activity_tv_details) {

    private val viewModel: TvDetailsViewModel by viewModels()

    override val defineBindingVariables: (ActivityTvDetailsBinding) -> Unit
        get() = { binding ->
            binding.activity = this
            binding.lifecycleOwner = this
            binding.viewModel = viewModel
        }

    val adapterVideos = VideoAdapter { playYouTubeVideo(it) }
    val adapterCast = PersonAdapter(isCast = true)
    val adapterImages = ImageAdapter()
    val adapterRecommendations = TvAdapter()
    val adapterSeasons by lazy { SeasonAdapter(id) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_details)

        initBinding()
        viewModel.initRequest(id)
        collectFlows(listOf(::collectDetails, ::collectUiState))
    }

    private suspend fun collectDetails() {
        viewModel.details.collect { details ->
            adapterCast.submitList(details.credits.cast)
            adapterVideos.submitList(details.videos.filterVideos())
            adapterImages.submitList(details.images.backdrops)
            adapterSeasons.submitList(details.seasons)
            adapterRecommendations.submitList(details.recommendations.results)
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) showSnackbar(state.errorText!!, getString(R.string.button_retry)) {
                viewModel.retryConnection {
                    viewModel.initRequest(id)
                }
            }
        }
    }
}