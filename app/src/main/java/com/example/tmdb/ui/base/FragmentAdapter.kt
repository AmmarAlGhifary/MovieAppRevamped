package com.example.tmdb.ui.base

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tmdb.ui.favoritemovies.FavoriteMoviesFragment
import com.example.tmdb.ui.favorites.FavoritesFragment
import com.example.tmdb.ui.favoritetv.FavoriteTvFragment
import com.example.tmdb.util.Constants

class FragmentAdapter(private val fragment: Fragment) : FragmentStateAdapter(fragment.childFragmentManager, fragment.viewLifecycleOwner.lifecycle) {

    private val favoritesFragment = listOf(FavoriteMoviesFragment(), FavoriteTvFragment())

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (fragment) {
            is FavoritesFragment -> favoritesFragment[position]
            else -> throw IllegalArgumentException(Constants.ILLEGAL_ARGUMENT_FRAGMENT_TYPE)
        }
    }
}