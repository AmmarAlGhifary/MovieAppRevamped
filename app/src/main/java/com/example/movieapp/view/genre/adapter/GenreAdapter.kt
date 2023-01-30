package com.example.movieapp.view.genre.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.model.genres.Genre
import com.example.movieapp.databinding.ItemGenreBinding
import com.example.movieapp.view.genre.GenreFragmentDirections

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, callback)
    private var dataGenre : List<Genre>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false))

    override fun getItemCount(): Int = dataGenre.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val genre = dataGenre[position]

        holder.bind(dataGenre[position])
        holder.itemView.setOnClickListener { mViews ->
            val direction = GenreFragmentDirections.actionGenreFragmentToMoviesFragment(genre)
            mViews.findNavController().navigate(direction)
        }
    }

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemGenreBinding.bind(itemView)

        fun bind(data: Genre) {
            binding.genres = data
        }
    }

}
