package com.example.tmdb.ui.favoritetv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.R
import com.example.tmdb.databinding.ItemFavoriteTvBinding
import com.example.tmdb.domain.model.FavoriteTv

class FavoriteTvAdapter(private val onItemClicked: (item: FavoriteTv) -> Unit) : ListAdapter<FavoriteTv, FavoriteTvAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteTv>() {
            override fun areItemsTheSame(oldItem: FavoriteTv, newItem: FavoriteTv): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FavoriteTv, newItem: FavoriteTv): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_favorite_tv, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.tv = getItem(position)
    }

    inner class ViewHolder(val view: ItemFavoriteTvBinding) : RecyclerView.ViewHolder(view.root) {
        init {
            view.ivRemove.setOnClickListener { onItemClicked(getItem(adapterPosition)) }
        }
    }
}