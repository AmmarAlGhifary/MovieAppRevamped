package com.example.tmdb.ui.favoritetv

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentFavoriteTvBinding
import com.example.tmdb.domain.model.FavoriteTv
import com.example.tmdb.ui.base.BaseFragment
import com.example.tmdb.ui.favoritetv.adapter.FavoriteTvAdapter
import com.example.tmdb.util.LifecycleRecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteTvFragment : BaseFragment<FragmentFavoriteTvBinding>(R.layout.fragment_favorite_tv) {

    private val viewModel: FavoriteTvViewModel by activityViewModels()

    override val bindingVariables: (FragmentFavoriteTvBinding) -> Unit
        get() = { binding ->
            binding.fragment = this
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel
        }

    val adapterFavorites = FavoriteTvAdapter { removeTv(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycle.addObserver(LifecycleRecyclerView(binding.recyclerView))
        collectFlows(listOf(::collectFavoriteTvs))
    }

    private suspend fun collectFavoriteTvs() {
        viewModel.favoriesTv.collect { favoriteTvs ->
            adapterFavorites.submitList(favoriteTvs)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFavoriteTvs()
    }

    private fun removeTv(tv: FavoriteTv) {
        viewModel.removeTvFromFavorites(tv)

        showSnackbar(
            message = getString(R.string.snackbar_removed_item),
            actionText = getString(R.string.snackbar_action_undo),
            anchor = true
        ) {
            viewModel.addTvToFavorites(tv)
        }
    }
}