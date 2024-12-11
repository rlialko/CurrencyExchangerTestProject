package com.ruslanlialko.currencyexchanger.presentation.ui.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ruslanlialko.currencyexchanger.R
import com.ruslanlialko.currencyexchanger.domain.exception.NetworkException
import com.ruslanlialko.currencyexchanger.domain.exception.UnknownException
import com.ruslanlialko.currencyexchanger.domain.model.Transaction
import com.ruslanlialko.currencyexchanger.presentation.theme.CurrencyExchangerTheme
import com.ruslanlialko.currencyexchanger.presentation.ui.home.components.BalanceItem
import com.ruslanlialko.currencyexchanger.presentation.ui.home.components.CurrencyInputField
import com.ruslanlialko.currencyexchanger.presentation.ui.home.components.CurrencyPickerButton
import com.ruslanlialko.currencyexchanger.presentation.utils.formatAmount
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var showBottomSheetSell by remember { mutableStateOf(false) }
    var showBottomSheetBuy by remember { mutableStateOf(false) }
    var latestTransaction by remember { mutableStateOf(null as Transaction?) }

    val bottomSheetStateSell = rememberModalBottomSheetState()
    val bottomSheetStateBuy = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()


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
            containerColor = MaterialTheme.colorScheme.surface,
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
            })
    }
    if (showBottomSheetSell) {
        ModalBottomSheet(
            sheetState = bottomSheetStateSell,
            containerColor = MaterialTheme.colorScheme.surface,
            onDismissRequest = {
                showBottomSheetSell = false
            },
        ) {
            LazyColumn {
                items(uiState.balances.size) { index ->
                    val currency = uiState.balances[index].currency
                    CurrencyPickerButton(currency = currency) {
                        viewModel.selectSellCurrency(currency)
                        coroutineScope.launch {
                            bottomSheetStateSell.hide()
                            showBottomSheetSell = false
                        }
                    }
                }
            }
        }
    }

    if (showBottomSheetBuy) {
        ModalBottomSheet(
            sheetState = bottomSheetStateBuy,
            containerColor = MaterialTheme.colorScheme.surface,
            onDismissRequest = {
                showBottomSheetBuy = false
            },
        ) {
            LazyColumn {
                items(uiState.allCurrencies.size) { index ->
                    val currency = uiState.allCurrencies[index]
                    CurrencyPickerButton(currency = currency) {
                        viewModel.selectBuyCurrency(currency)
                        coroutineScope.launch {
                            bottomSheetStateBuy.hide()
                            showBottomSheetBuy = false
                        }
                    }

                }
            }
        }
    }

    Column {
        Text(
            text = stringResource(R.string.home_screen_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        Text(
            text = pluralStringResource(
                id = R.plurals.you_have_wallets, uiState.balances.size, uiState.balances.size
            ), modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 140.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(uiState.balances.size) { index ->
                BalanceItem(balance = uiState.balances[index])
            }

            item {
                if (uiState.balances.isEmpty()) {
                    CircularProgressIndicator()
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp)
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(
                        topStart = 16.dp, topEnd = 16.dp
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                uiState.isLoadingRates -> {
                    HomeLoadingState()
                }

                uiState.error != null -> {
                    HomeErrorState(uiState.error!!) {
                        viewModel.fetchRates()
                    }
                }

                else -> {

                    if (uiState.allCurrencies.isNotEmpty()) {
                        val pattern = remember { Regex("^[0-9]*\\.?[0-9]{0,2}$") }

                        CurrencyInputField(
                            label = stringResource(id = R.string.sell),
                            selectedCurrency = uiState.selectedSellCurrency,
                            onCurrencyClick = {
                                showBottomSheetSell = true
                            },
                            amount = uiState.sellAmount,
                            onAmountChange = {
                                if (it.isEmpty() || it.matches(pattern)) {
                                    viewModel.updateSellAmount(it)
                                }
                            },
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.tertiary, thickness = 0.5.dp
                        )

                        CurrencyInputField(
                            label = stringResource(id = R.string.receive),
                            selectedCurrency = uiState.selectedBuyCurrency,
                            onCurrencyClick = {
                                showBottomSheetBuy = true
                            },
                            amount = uiState.buyAmount,
                            amountReadOnly = true,
                        )
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.tertiary, thickness = 0.5.dp
                        )
                        Button(
                            onClick = {
                                viewModel.exchange()
                            }, modifier = Modifier
                                .padding(horizontal = 24.dp, vertical = 16.dp)
                                .height(56.dp)
                                .fillMaxWidth()
                        ) {
                            Text(text = stringResource(id = R.string.submit_button))
                        }
                    }
                }
            }
        }

    }
}

@Composable
private fun HomeErrorState(error: Throwable, onRetry: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .fillMaxWidth()
    ) {

        when (error) {
            is UnknownException -> {
                Text(
                    text = stringResource(id = R.string.unknown_error),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = stringResource(id = R.string.unknown_error_message),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface

                    )
                )
            }

            is NetworkException -> {
                Text(
                    text = stringResource(id = R.string.network_error),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = stringResource(id = R.string.network_error_message),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface

                    )
                )
            }
        }

        Button(
            onClick = {
                onRetry()
            }, modifier = Modifier
                .padding(16.dp)
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

@Preview
@Composable
private fun PreviewError() {
    CurrencyExchangerTheme {
        HomeErrorState(UnknownException(Exception())) {}
    }
}

@Preview
@Composable
private fun PreviewLoading() {
    CurrencyExchangerTheme {
        HomeLoadingState()
    }
}

