package com.example.tmdb.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.ItemTvBinding
import com.example.tmdb.domain.model.Tv

class TvAdapter(
    private val isGrid: Boolean = false,
    private val isCredits: Boolean = false,
) : ListAdapter<Tv, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Tv>() {
            override fun areItemsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HorizontalViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HorizontalViewHolder -> {
                holder.view.apply {
                    isGrid = this@TvAdapter.isGrid
                    isCredits = this@TvAdapter.isCredits
                    tv = getItem(position)
                }
            }
        }
    }

    inner class HorizontalViewHolder private constructor(val view: ItemTvBinding) :
        RecyclerView.ViewHolder(view.root) {
        constructor(parent: ViewGroup) : this(
            ItemTvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
