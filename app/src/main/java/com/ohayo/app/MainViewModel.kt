package com.ohayo.app

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Created by Furuichi on 30/12/2022
 */
@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState>
        get() = _uiState

    sealed class UiState {
        object Loading : UiState()
        data class Success(val userData: String) : UiState()
    }
}