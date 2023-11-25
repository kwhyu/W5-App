package com.kwhy.w5_app.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kwhy.w5_app.data.WaifuRepository
import com.kwhy.w5_app.model.Waifu
import com.kwhy.w5_app.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel (
    private val repository: WaifuRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Waifu>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Waifu>>>
        get() = _uiState

    fun waifuSearch(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchWaifu(_query.value)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateFavorite(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateFavorite(id, newState)
            .collect { isUpdated ->
                if (isUpdated) waifuSearch(_query.value)
            }
    }

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query


}