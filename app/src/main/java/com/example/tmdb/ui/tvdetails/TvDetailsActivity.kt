package com.example.tmdb.ui.tvdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityTvDetailsBinding
import com.example.tmdb.ui.base.BaseActivity
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_details)
    }
}