package com.example.tmdb.ui.seeall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivitySeeAllBinding
import com.example.tmdb.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeAllActivity : BaseActivity<ActivitySeeAllBinding>(R.layout.activity_see_all){

    override val defineBindingVariables: ((ActivitySeeAllBinding) -> Unit)?
        get() = TODO("Not yet implemented")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_all)
    }
}