package com.example.tmdb.ui.fullscreen

import android.os.Bundle
import android.os.Parcelable
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityFullscreenBinding
import com.example.tmdb.domain.model.Image
import com.example.tmdb.ui.base.BaseActivity
import com.example.tmdb.ui.fullscreen.adapter.FullscreenAdapter
import com.example.tmdb.util.Constants
import kotlinx.coroutines.flow.MutableStateFlow

class FullscreenActivity : BaseActivity<ActivityFullscreenBinding>(R.layout.activity_fullscreen) {

    override val defineBindingVariables: (ActivityFullscreenBinding) -> Unit
        get() = { binding ->
            binding.activity = this
            binding.lifecycleOwner = this
        }

    val isFullscreen = MutableStateFlow(false)
    val imageNumber = MutableStateFlow("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)

        initBinding()

        val imageList =
            intent.getParcelableArrayListExtra<Parcelable>(Constants.IMAGE_LIST) as List<Image>
        val position = intent.getIntExtra(Constants.ITEM_POSITION, 0)
        val totalImageCount = imageList.size

        binding.vpImages.apply {
            adapter = FullscreenAdapter { toggleUiVisibility() }.apply { submitList(imageList) }
            setCurrentItem(position, false)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    imageNumber.value = "${position + 1}/$totalImageCount"
                }
            })
        }
    }

    private fun toggleUiVisibility() {
        if (isFullscreen.value) showUi() else hideUi()
    }

    private fun hideUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.flFullscreen).let {
            it.hide(WindowInsetsCompat.Type.systemBars())
            it.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        isFullscreen.value = true
    }

    private fun showUi() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(
            window,
            binding.flFullscreen
        ).show(WindowInsetsCompat.Type.systemBars())

        isFullscreen.value = false
    }
}