package com.example.tmdb.ui.seasondetails

import android.os.Bundle
import androidx.activity.viewModels
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivitySeasonDetailsBinding
import com.example.tmdb.ui.base.BaseActivity
import com.example.tmdb.ui.home.adapter.ImageAdapter
import com.example.tmdb.ui.home.adapter.PersonAdapter
import com.example.tmdb.ui.home.adapter.VideoAdapter
import com.example.tmdb.util.Constants
import com.example.tmdb.util.playYouTubeVideo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeasonDetailsActivity : BaseActivity<ActivitySeasonDetailsBinding>(R.layout.activity_season_details) {

    private val viewModel: SeasonDetailsViewModel by viewModels()

    override val defineBindingVariables: (ActivitySeasonDetailsBinding) -> Unit
        get() = { binding ->
            binding.activity = this
            binding.lifecycleOwner = this
            binding.videos
            binding.viewModel = viewModel
        }

    val adapterSeasonVideos = VideoAdapter { playYouTubeVideo(it) }
    val adapterSeasonCast = PersonAdapter(isCast = true)
    val adapterSeasonImages = ImageAdapter(isPortrait = true)

    private var seasonNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_season_details)

        initBinding()

        seasonNumber = intent.getIntExtra(Constants.SEASON_NUMBER, 0)
        viewModel.initRequest(id, seasonNumber)

        collectFlows(listOf(::collectSeasonDetails, ::collectUiState))
    }

    private suspend fun collectSeasonDetails() {
        viewModel.seasonDetails.collect { seasonDetails ->
            adapterSeasonCast.submitList(seasonDetails.credits.cast)
            adapterSeasonVideos.submitList(seasonDetails.videos.filterVideos())
            adapterSeasonImages.submitList(seasonDetails.images.posters)
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) showSnackbar(state.errorText!!, getString(R.string.button_retry)) {
                viewModel.retryConnection {
                    viewModel.initRequest(id, seasonNumber)
                }
            }
        }
    }
}