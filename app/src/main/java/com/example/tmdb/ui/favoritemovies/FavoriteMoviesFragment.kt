package com.example.tmdb.ui.favoritemovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentFavoriteMoviesBinding
import com.example.tmdb.domain.model.FavoriteMovie
import com.example.tmdb.ui.base.BaseFragment
import com.example.tmdb.ui.favorites.adapter.FavoriteMoviesAdapter
import com.example.tmdb.util.LifecycleRecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMoviesFragment : BaseFragment<FragmentFavoriteMoviesBinding>(R.layout.fragment_favorite_movies) {

    private val viewModel: FavoriteMoviesViewModel by activityViewModels()

    val adapterFavorites = FavoriteMoviesAdapter { removeMovie(it) }

    override val bindingVariables: ((FragmentFavoriteMoviesBinding) -> Unit)
        get() = { binding ->
            binding.fragment = this
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycle.addObserver(LifecycleRecyclerView(binding.recyclerView))
        collectFlows(listOf(::collectFavoriteMovies))
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFavoriteMovies()
    }

    private fun removeMovie(movie: FavoriteMovie) {
        viewModel.removeMovieFromFavorites(movie)
        showSnackbar(
            message = getString(R.string.snackbar_removed_item),
            actionText = getString(R.string.snackbar_action_undo),
            anchor = true
        ) {
            viewModel.addMovieToFavorites(movie)
        }
    }

    private suspend fun collectFavoriteMovies() {
        viewModel.favoriteMovies.collect { favoriteMovies ->
            adapterFavorites.submitList(favoriteMovies)
        }
    }
}