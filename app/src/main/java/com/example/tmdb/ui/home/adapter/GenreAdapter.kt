package com.example.tmdb.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.R
import com.example.tmdb.databinding.ItemGenreBinding
import com.example.tmdb.domain.model.Genre
import com.example.tmdb.util.MediaType

class GenreAdapter(private val mediaType: MediaType) :
    ListAdapter<Pair<Int, String>, GenreAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pair<Int, String>>() {
            override fun areItemsTheSame(
                oldItem: Pair<Int, String>,
                newItem: Pair<Int, String>
            ): Boolean {
                return oldItem.first == newItem.first
            }

            override fun areContentsTheSame(
                oldItem: Pair<Int, String>,
                newItem: Pair<Int, String>
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(val view: ItemGenreBinding) : RecyclerView.ViewHolder(view.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_genre, parent, false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.apply {
            mediaType = this@GenreAdapter.mediaType
            genre = Genre(id = getItem(position).first, name = getItem(position).second)
        }
    }
}