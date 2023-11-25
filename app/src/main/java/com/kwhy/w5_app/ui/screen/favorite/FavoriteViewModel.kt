package com.kwhy.w5_app.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kwhy.w5_app.data.WaifuRepository
import com.kwhy.w5_app.model.Waifu
import com.kwhy.w5_app.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel (
    private val repository: WaifuRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Waifu>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Waifu>>>
        get() = _uiState

    fun getWaifuFavorite() = viewModelScope.launch {
        repository.favoriteWaifu()
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateFavorite(id: Int, newState: Boolean){
        repository.updateFavorite(id, newState)
        getWaifuFavorite()
    }
}