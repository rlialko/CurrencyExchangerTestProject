package com.ruslanlialko.currencyexchanger.presentation.ui.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslanlialko.currencyexchanger.domain.model.Transaction
import com.ruslanlialko.currencyexchanger.domain.use_case.ObserveTransactionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class TransactionsViewModel(
    private val observeTransactionsUseCase: ObserveTransactionsUseCase

) : ViewModel() {

    private val _uiState: MutableStateFlow<TransactionsUiState> =
        MutableStateFlow(TransactionsUiState())
    val uiState: StateFlow<TransactionsUiState> = _uiState.asStateFlow()

    init {
        loadTransactions()
    }

    private fun loadTransactions() {

        observeTransactionsUseCase().onEach { transactionList ->
            _uiState.update {
                it.copy(
                    transactions = transactionList
                )
            }
        }.launchIn(viewModelScope)
    }


}

data class TransactionsUiState(
    val transactions: List<Transaction> = emptyList()
)