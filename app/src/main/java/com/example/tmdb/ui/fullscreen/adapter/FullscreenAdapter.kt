package com.example.tmdb.ui.fullscreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.R
import com.example.tmdb.databinding.ItemFullscreenBinding
import com.example.tmdb.domain.model.Image

class FullscreenAdapter(private val onClick: () -> Unit) : ListAdapter<Image, FullscreenAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.filePath == newItem.filePath
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_fullscreen, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.image = getItem(position)
    }

    inner class ViewHolder(val view: ItemFullscreenBinding) : RecyclerView.ViewHolder(view.root) {
        init {
            view.photoView.setOnClickListener { onClick() }
        }
    }
}