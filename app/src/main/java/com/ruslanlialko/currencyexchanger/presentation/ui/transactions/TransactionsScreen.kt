package com.ruslanlialko.currencyexchanger.presentation.ui.transactions

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ruslanlialko.currencyexchanger.presentation.ui.transactions.components.TransactionItem
import org.koin.androidx.compose.koinViewModel


@Composable
fun TransactionsScreen(
    viewModel: TransactionsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LazyColumn {
        items(uiState.transactions.size) { index ->
            val transaction = uiState.transactions[index]
            TransactionItem(transaction = transaction)
        }
    }
}