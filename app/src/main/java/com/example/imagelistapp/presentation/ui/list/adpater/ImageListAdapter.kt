package com.example.imagelistapp.presentation.ui.list.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.imagelistapp.data.model.Image
import com.example.imagelistapp.databinding.ItemImageBinding
import com.example.imagelistapp.presentation.ui.MainActivity
import com.example.imagelistapp.presentation.ui.list.viewholder.ImageViewHolder

class ImageListAdapter(
    private val handler: MainActivity.ItemHandler
): PagingDataAdapter<Image, ImageViewHolder>(diffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), handler
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val diffUtil = object: DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem == newItem
            }

        }
    }


}