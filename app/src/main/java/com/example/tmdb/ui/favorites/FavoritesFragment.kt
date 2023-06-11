package com.example.tmdb.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentFavoritesBinding
import com.example.tmdb.ui.base.BaseFragment
import com.example.tmdb.ui.base.FragmentAdapter
import com.example.tmdb.util.LifecycleViewPager
import com.google.android.material.tabs.TabLayoutMediator


class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(R.layout.fragment_favorites) {

    override val bindingVariables: ((FragmentFavoritesBinding) -> Unit)?
        get() = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = FragmentAdapter(this)
        viewLifecycleOwner.lifecycle.addObserver(LifecycleViewPager(binding.viewPager))

        mediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val tabTitles = listOf(getString(R.string.tab_title_1), getString(R.string.tab_title_2))
            tab.text = tabTitles[position]
        }

        mediator?.attach()
    }
}