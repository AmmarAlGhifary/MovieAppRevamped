package com.example.tmdb.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.ItemMovieBinding
import com.example.tmdb.domain.model.Movie

class MoviesAdapter(
    private val isGrid: Boolean = false,
    private val isCredits: Boolean = false,
) : ListAdapter<Movie, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class HorizontalViewHolder private constructor(val view: ItemMovieBinding) :
        RecyclerView.ViewHolder(view.root) {
        constructor(parent: ViewGroup) : this(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HorizontalViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HorizontalViewHolder -> {
                holder.view.apply {
                    isGrid = this@MoviesAdapter.isGrid
                    isCredits = this@MoviesAdapter.isCredits
                    movie = getItem(position)
                }
            }
        }
    }
}