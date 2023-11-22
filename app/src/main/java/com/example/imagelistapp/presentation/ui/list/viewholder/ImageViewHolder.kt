package com.example.imagelistapp.presentation.ui.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.imagelistapp.data.model.Image
import com.example.imagelistapp.databinding.ItemImageBinding
import com.example.imagelistapp.presentation.ui.MainActivity

class ImageViewHolder(
    private val binding: ItemImageBinding,
    private val handler: MainActivity.ItemHandler
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Image) {
        binding.item = item
        binding.executePendingBindings()
        binding.handler = handler
    }
}