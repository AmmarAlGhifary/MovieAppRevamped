package com.example.movieapp.view.genre

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentGenreBinding
import com.example.movieapp.view.genre.adapter.GenreAdapter
import com.example.movieapp.view.genre.viewmodel.GenreViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped


@FragmentScoped
@AndroidEntryPoint
class GenreFragment : Fragment() {

    @FragmentScoped
    private var _binding : FragmentGenreBinding? = null
    private val binding get() = _binding!!
    private lateinit var genreAdapter: GenreAdapter

    private val viewModel : GenreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRV()
    }

    private fun setRV() {
        genreAdapter = GenreAdapter()
        binding.rvGenre.apply {
            adapter = genreAdapter
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
        }
        viewModel.genreResponse.observe(viewLifecycleOwner){
            binding.genres = it
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        binding.srlGenre.setOnRefreshListener {
            viewModel.getGenres()
            binding.srlGenre.isRefreshing = false
        }
    }
}