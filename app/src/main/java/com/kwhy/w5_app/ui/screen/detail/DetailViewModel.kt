package com.kwhy.w5_app.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kwhy.w5_app.data.WaifuRepository
import com.kwhy.w5_app.model.Waifu
import com.kwhy.w5_app.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: WaifuRepository
): ViewModel()  {
    private val _uiState: MutableStateFlow<UiState<Waifu>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Waifu>>
        get() = _uiState

    fun getWaifuById(id: Int) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(repository.getWaifuById(id))
    }

    fun updateFavorite(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateFavorite(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getWaifuById(id)
            }
    }
}