package com.example.imagelistapp.presentation.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagelistapp.data.model.Image
import com.example.imagelistapp.databinding.ActivityMainBinding
import com.example.imagelistapp.presentation.ui.list.adpater.ImageListAdapter
import com.example.imagelistapp.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val adapter by lazy { ImageListAdapter(handler = ItemHandler()) }

    var isEmpty: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            viewModel = this@MainActivity.viewModel

            recyclerView.also { recyclerView ->
                recyclerView.layoutManager = GridLayoutManager(this@MainActivity,3)
                recyclerView.adapter = adapter
            }

            searchTextInputEditText.setOnEditorActionListener { _, actionId, _ ->
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val text = searchTextInputEditText.text.toString()
                    //viewModel?.onQueryValueChanged(text)
                    search(query = text)
                    hideInputKeyboard()
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

            adapter.addLoadStateListener { loadState ->
                emptyTextView.isVisible = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            }
        }

        hideInputKeyboard()
    }

    private fun search(query: String) {
        lifecycleScope.launch {
            viewModel.searchList(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun hideInputKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchTextInputEditText.windowToken, 0)
    }

    inner class ItemHandler {
        fun onClickItem(item: Image) {
            Log.d(TAG, "onclick $item")
            ImageFragment(item, viewModel).show(supportFragmentManager, ImageFragment.TAG)
        }
    }

    companion object {
        const val TAG = "MainActivity.MyLog"
    }
}