package com.example.tmdb.ui.base

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tmdb.R
import com.example.tmdb.ui.fullscreen.FullscreenActivity
import com.example.tmdb.ui.seeall.SeeAllActivity
import com.example.tmdb.util.Constants
import com.example.tmdb.util.isDarkColor
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction0

abstract class BaseActivity<B : ViewDataBinding>(@LayoutRes private val layoutId : Int) : AppCompatActivity() {

    protected val binding: B by lazy { DataBindingUtil.setContentView<B>(this, layoutId) }

    protected abstract val defineBindingVariables: ((B) -> Unit)?

    protected val id by lazy { intent.getIntExtra(Constants.DETAIL_ID, 0) }

    val backgroundColor by lazy { intent.getIntExtra(Constants.BACKGROUND_COLOR, 0) }

    protected fun initBinding() {
        when (this) {
            is FullscreenActivity -> {}
            is SeeAllActivity -> {
                if (backgroundColor != 0) {
                    setTheme(if (backgroundColor.isDarkColor()) R.style.SeeAllDarkTheme else R.style.SeeAllLightTheme)
                    window.statusBarColor = backgroundColor
                }
            }
            else -> setTheme(if (backgroundColor.isDarkColor()) R.style.DetailDarkTheme else R.style.DetailLightTheme)

        }

        binding.apply { defineBindingVariables?.invoke(this) }
    }

    protected fun collectFlows(collectors: List<KSuspendFunction0<Unit>>) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                collectors.forEach { collectors ->
                    launch { collectors.invoke() }
                }
            }
        }
    }

    protected fun showSnackbar(message: String, actionMessage: String, anchor: Boolean = false, action: () -> Unit) {
        val view = window.decorView.rootView
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction(actionMessage) { action() }
        if (anchor) snackbar.setAnchorView(R.id.bottom_nav_bar)
        snackbar.show()
    }
}