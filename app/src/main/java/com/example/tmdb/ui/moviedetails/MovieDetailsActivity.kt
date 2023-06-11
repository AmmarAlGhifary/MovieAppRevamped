package com.example.tmdb.ui.moviedetails

import android.os.Bundle
import androidx.activity.viewModels
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityMovieDetailsBinding
import com.example.tmdb.ui.base.BaseActivity
import com.example.tmdb.ui.home.adapter.ImageAdapter
import com.example.tmdb.ui.home.adapter.MoviesAdapter
import com.example.tmdb.ui.home.adapter.PersonAdapter
import com.example.tmdb.ui.home.adapter.VideoAdapter
import com.example.tmdb.util.playYouTubeVideo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : BaseActivity<ActivityMovieDetailsBinding>(R.layout.activity_movie_details) {

    private val viewModel: MovieDetailsViewModel by viewModels()

    override val defineBindingVariables: (ActivityMovieDetailsBinding) -> Unit
        get() = { binding ->
            binding.activity = this
            binding.lifecycleOwner = this
            binding.viewModel = viewModel
        }

    val adapterVideos = VideoAdapter { playYouTubeVideo(it) }
    val adapterCast = PersonAdapter(isCast = true)
    val adapterImages = ImageAdapter()
    val adapterRecommendations = MoviesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        initBinding()
        viewModel.initRequests(id)
        collectFlows(listOf(::collectDetails, ::collectUiState))
    }

    private suspend fun collectDetails() {
        viewModel.details.collect { details ->
            adapterCast.submitList(details.credits.cast)
            adapterVideos.submitList(details.videos.filterVideos())
            adapterImages.submitList(details.images.backdrops)
            adapterRecommendations.submitList(details.recommendations.results)
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) showSnackbar(state.errorText!!, getString(R.string.button_retry)) {
                viewModel.retryConnection {
                    viewModel.initRequests(id)
                }
            }
        }
    }
}