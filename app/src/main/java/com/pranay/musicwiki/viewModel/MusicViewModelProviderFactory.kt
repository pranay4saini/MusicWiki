package com.pranay.musicwiki.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pranay.musicwiki.Repository
import com.pranay.musicwiki.api.RetrofitHelper

class MusicViewModelProviderFactory(private val retrofitHelper: RetrofitHelper):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MusicViewModel(Repository(retrofitHelper)) as T
    }
}