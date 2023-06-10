package com.example.tmdb.ui.home

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentHomeBinding
import com.example.tmdb.ui.base.BaseFragment
import com.example.tmdb.ui.home.adapter.GenreAdapter
import com.example.tmdb.ui.home.adapter.MoviesAdapter
import com.example.tmdb.ui.moviedetails.adapter.PersonAdapter
import com.example.tmdb.ui.tvlist.adapter.TvAdapter
import com.example.tmdb.util.LifecycleRecyclerView
import com.example.tmdb.util.MediaType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by activityViewModels()

    override val bindingVariables: (FragmentHomeBinding) -> Unit
        get() = { binding ->
            binding.fragment = this
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel
        }

    val adapterMovies by lazy { MoviesAdapter() }
    val adapterTvs by lazy { TvAdapter() }
    val adapterPeople by lazy { PersonAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.apply {
            addObserver(LifecycleRecyclerView(binding.rvGenres))
            addObserver(LifecycleRecyclerView(binding.rvMovies))
            addObserver(LifecycleRecyclerView(binding.rvTvs))
        }

        setupSearchView()
        setupSpinner()
        collectFlows(listOf(::collectMovieSearchResults, ::collectUiState))

    }

    fun clearSearch() {
        viewModel.clearSearchResults()
        adapterMovies.submitList(null)
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.rvMovies.scrollToPosition(0)

                if (!query.isNullOrEmpty()) viewModel.fetchInitialSearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }
    private fun setupSpinner() {
        binding.spGenreMediaType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        val movieGenreIds = resources.getIntArray(R.array.movie_genre_ids).toTypedArray()
                        val movieGenreNames = resources.getStringArray(R.array.movie_genre_names)
                        binding.rvGenres.adapter = GenreAdapter(MediaType.MOVIE).apply { submitList(movieGenreIds.zip(movieGenreNames)) }
                    }
                    1 -> {
                        val tvGenreIds = resources.getIntArray(R.array.tv_genre_ids).toTypedArray()
                        val tvGenreNames = resources.getStringArray(R.array.tv_genre_names)
                        binding.rvGenres.adapter = GenreAdapter(MediaType.TV).apply { submitList(tvGenreIds.zip(tvGenreNames)) }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private suspend fun collectMovieSearchResults() {
        viewModel.movieResults.collect { movies ->
            adapterMovies.submitList(movies)
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) showSnackbar(
                message = state.errorText!!,
                actionText = getString(R.string.button_retry),
                anchor = true
            ) {
                viewModel.retryConnection {
                    viewModel.initRequests()
                }
            }
        }
    }

}