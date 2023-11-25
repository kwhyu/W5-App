package com.kwhy.w5_app.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kwhy.w5_app.data.WaifuRepository
import com.kwhy.w5_app.ui.screen.detail.DetailViewModel
import com.kwhy.w5_app.ui.screen.favorite.FavoriteViewModel
import com.kwhy.w5_app.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: WaifuRepository)
    : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}