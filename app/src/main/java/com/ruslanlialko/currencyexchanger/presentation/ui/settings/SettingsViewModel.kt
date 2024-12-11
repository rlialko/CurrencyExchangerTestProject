package com.ruslanlialko.currencyexchanger.presentation.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslanlialko.currencyexchanger.domain.use_case.ResetInitialDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val resetInitialDataUseCase: ResetInitialDataUseCase

) : ViewModel() {

    private val _uiState: MutableStateFlow<SettingsUiState> =
        MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun resetInitialBalance() {
        viewModelScope.launch {
            resetInitialDataUseCase()
        }
    }


}

data class SettingsUiState(
    val isLoading: Boolean = false
)
