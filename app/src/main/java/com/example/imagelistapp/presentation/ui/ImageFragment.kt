package com.example.imagelistapp.presentation.ui

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.viewModelScope
import com.example.imagelistapp.R
import com.example.imagelistapp.data.model.Image
import com.example.imagelistapp.databinding.FragmentImageBinding
import com.example.imagelistapp.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch


class ImageFragment(
    private val item: Image,
    private val viewModel: MainViewModel,
) : DialogFragment(R.layout.fragment_image) {

    private lateinit var binding: FragmentImageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageBinding.inflate(inflater, container, false).also {
            it.item = item
            it.view = this
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initWindowSize()

    }

    fun finish() {
        dismiss()
    }

    fun saveImage(item: Image) {
        context?.let {
            viewModel.viewModelScope.launch {
                val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(item.url))
                    .setTitle("Image Download")
                    .setDescription("Downloading Image")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "image.jpg")
                val downloadManager = it.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                downloadManager.enqueue(request)

                // 완료 확인
            }
        }
    }

    private fun initWindowSize() {
        val width = (resources.displayMetrics.widthPixels * 9 / 10)
        val height = (resources.displayMetrics.heightPixels * 1 / 2)
        dialog?.window?.setLayout(width, height)
    }

    companion object {
        const val TAG = "ImageFragmentDialog"
    }

}