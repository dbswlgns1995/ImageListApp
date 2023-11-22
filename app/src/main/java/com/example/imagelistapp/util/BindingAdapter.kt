package com.example.imagelistapp.util

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import com.example.imagelistapp.data.model.Image

@BindingAdapter("image")
fun ImageView.setImage(image: Image?) {
    if (image == null) {
        return
    }
    setBackgroundColor(Color.parseColor(image.color))

    load(image.url){
        crossfade(300)
    }
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean) {
    isVisible = visible
}