package com.example.tmdb.ui.fullscreen

import android.os.Bundle
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityFullscreenBinding
import com.example.tmdb.ui.base.BaseActivity

class FullscreenActivity : BaseActivity<ActivityFullscreenBinding>(R.layout.activity_fullscreen) {

    override val defineBindingVariables: ((ActivityFullscreenBinding) -> Unit)?
        get() = TODO("Not yet implemented")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)
    }
}