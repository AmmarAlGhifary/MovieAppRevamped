package com.example.tmdb.ui.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.R
import com.example.tmdb.databinding.ItemImageBinding
import com.example.tmdb.domain.model.Image
import com.example.tmdb.ui.fullscreen.FullscreenActivity
import com.example.tmdb.util.Constants

class ImageAdapter(
    private val isPortrait: Boolean = false,
    private val isGrid: Boolean = false
) : ListAdapter<Image, ImageAdapter.ViewHolder>(DIFF_CALLBACK) {

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
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_image,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.apply {
            isGrid = this@ImageAdapter.isGrid
            isPortrait = this@ImageAdapter.isPortrait
            image = getItem(position)
        }
    }

    inner class ViewHolder(val view: ItemImageBinding) : RecyclerView.ViewHolder(view.root) {
        init {
            view.root.setOnClickListener {
                Intent(it.context, FullscreenActivity::class.java).apply {
                    putParcelableArrayListExtra(Constants.IMAGE_LIST, ArrayList(currentList))
                    putExtra(Constants.ITEM_POSITION, adapterPosition)
                    it.context.startActivity(this)
                }
            }
        }
    }
}