package com.ruslanlialko.currencyexchanger.presentation.ui.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ruslanlialko.currencyexchanger.R
import com.ruslanlialko.currencyexchanger.presentation.ui.transactions.components.TransactionItem
import org.koin.androidx.compose.koinViewModel


@Composable
fun TransactionsScreen(
    viewModel: TransactionsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.surface
            )
    ) {
        Text(
            text = stringResource(R.string.transactions_screen_title),
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        LazyColumn {
            items(uiState.transactions.size) { index ->
                val transaction = uiState.transactions[index]
                TransactionItem(transaction = transaction)
            }
            item {
                if (uiState.transactions.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.no_transactions),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}