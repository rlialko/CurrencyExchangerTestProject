package com.ruslanlialko.currencyexchanger.presentation.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ruslanlialko.currencyexchanger.R
import com.ruslanlialko.currencyexchanger.domain.exception.NetworkException
import com.ruslanlialko.currencyexchanger.domain.exception.UnknownException
import com.ruslanlialko.currencyexchanger.domain.model.Transaction
import com.ruslanlialko.currencyexchanger.presentation.theme.CurrencyExchangerTheme
import com.ruslanlialko.currencyexchanger.presentation.ui.home.components.BalanceItem
import com.ruslanlialko.currencyexchanger.presentation.ui.home.components.CurrencyInputField
import com.ruslanlialko.currencyexchanger.presentation.utils.formatAmount
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var latestTransaction by remember { mutableStateOf(null as Transaction?) }

    LaunchedEffect(Unit) {
        viewModel.oneTimeEventSharedFlow.collect {
            when (it) {
                is HomeOneTimeEvent.ExchangeError -> {
                    Toast.makeText(
                        context, it.error.message, Toast.LENGTH_LONG
                    ).show()
                }

                is HomeOneTimeEvent.Exchanged -> {
                    latestTransaction = it.transaction
                    showDialog = true
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = stringResource(id = R.string.currency_converted)) },
            text = {
                latestTransaction?.let {
                    Text(
                        text = stringResource(
                            id = R.string.exchange_success_message,
                            it.sellValue.formatAmount(),
                            it.sellCurrency,
                            it.buyValue.formatAmount(),
                            it.buyCurrency,
                            it.commission.formatAmount()
                        )
                    )
                }
            },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text(text = stringResource(id = R.string.done))
                }
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            uiState.isLoading -> {
                HomeLoadingState()
            }

            uiState.error != null -> {
                HomeErrorState(uiState.error!!) {
                    viewModel.fetchRates()
                }
            }

            else -> {
                HomeSuccessState(
                    uiState, viewModel
                )
            }
        }
    }
}

@Composable
fun HomeSuccessState(
    uiState: HomeUiState, viewModel: HomeViewModel
) {

    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(uiState.balances.size) { index ->
            BalanceItem(balance = uiState.balances[index])
        }
    }

    uiState.allCurrencies?.let { currencies ->
        val pattern = remember { Regex("^[0-9]*\\.?[0-9]{0,2}$") }

        CurrencyInputField(painter = painterResource(R.drawable.ic_arrow_up),
            color = Color(0xFFE53935),
            label = stringResource(id = R.string.sell),
            selectedCurrency = uiState.selectedSellCurrency,
            currencies = uiState.balances.map { it.currency },
            onCurrencySelected = { viewModel.selectSellCurrency(it) },
            amount = uiState.sellAmount,
            onAmountChange = {
                if (it.isEmpty() || it.matches(pattern)) {
                    viewModel.updateSellAmount(it)
                }
            })

        CurrencyInputField(
            painter = painterResource(R.drawable.ic_arrow_down),
            color = Color(0xFF43A047),
            label = stringResource(id = R.string.receive),
            selectedCurrency = uiState.selectedBuyCurrency,
            currencies = currencies,
            onCurrencySelected = { viewModel.selectBuyCurrency(it) },
            amount = uiState.buyAmount,
            amountReadOnly = true,
        )

        Button(
            onClick = {
                viewModel.exchange()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = stringResource(id = R.string.submit))
        }
    }
}

@Composable
private fun HomeErrorState(error: Throwable, onRetry: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        when (error) {
            is UnknownException -> {
                Text(
                    text = stringResource(id = R.string.unknown_error),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(text = stringResource(id = R.string.unknown_error_message))
            }

            is NetworkException -> {
                Text(text = stringResource(id = R.string.network_error))
                Text(text = stringResource(id = R.string.network_error_message))
            }
        }

        Button(
            onClick = {
                onRetry()
            }, modifier = Modifier.padding(16.dp)
        ) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}


@Composable
fun HomeLoadingState() {
    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@PreviewLightDark
@Composable
private fun PreviewError() {
    CurrencyExchangerTheme {
        HomeErrorState(UnknownException(Exception())) {}
    }
}

@PreviewLightDark
@Composable
private fun PreviewLoading() {
    CurrencyExchangerTheme {
        HomeLoadingState()
    }
}

