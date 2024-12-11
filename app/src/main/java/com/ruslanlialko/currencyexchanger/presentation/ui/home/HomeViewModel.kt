package com.ruslanlialko.currencyexchanger.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslanlialko.currencyexchanger.domain.model.Balance
import com.ruslanlialko.currencyexchanger.domain.model.ExchangeParameters
import com.ruslanlialko.currencyexchanger.domain.model.Transaction
import com.ruslanlialko.currencyexchanger.domain.use_case.CalculateReceiveAmountUseCase
import com.ruslanlialko.currencyexchanger.domain.use_case.ExchangeUseCase
import com.ruslanlialko.currencyexchanger.domain.use_case.GetRatesUseCase
import com.ruslanlialko.currencyexchanger.domain.use_case.ObserveBalancesUseCase
import com.ruslanlialko.currencyexchanger.domain.use_case.base.Outcome
import com.ruslanlialko.currencyexchanger.presentation.utils.formatAmount
import com.ruslanlialko.currencyexchanger.presentation.utils.toSafeDouble
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

private const val REPEAT_TIME_MS = 5000L


class HomeViewModel(
    val getRatesUseCase: GetRatesUseCase,
    val observeBalancesUseCase: ObserveBalancesUseCase,
    val exchangeUseCase: ExchangeUseCase,
    val calculateReceiveAmountUseCase: CalculateReceiveAmountUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _uiOneTimeEvents: MutableSharedFlow<HomeOneTimeEvent> = MutableSharedFlow()
    val oneTimeEventSharedFlow = _uiOneTimeEvents.asSharedFlow()

    private var job: Job? = null

    init {
        observeBalances()
        fetchRates()
    }

    private fun observeBalances() {
        observeBalancesUseCase().onEach { balanceList ->
            _uiState.update {
                it.copy(
                    balances = balanceList
                )
            }
        }.launchIn(viewModelScope)
    }

    fun fetchRates() {
        job?.cancel()
        job = viewModelScope.launch {
            while (isActive) {
                getRatesUseCase().collect { outcome ->
                    when (outcome) {
                        is Outcome.Loading -> {
                        }

                        is Outcome.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoadingRates = false,
                                    error = null,
                                    allCurrencies = outcome.data!!.rates.keys.toList()
                                )
                            }
                        }

                        is Outcome.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoadingRates = false,
                                    error = outcome.error
                                )
                            }
                        }
                    }
                }
                delay(REPEAT_TIME_MS)
            }
        }
    }

    fun exchange() {
        exchangeUseCase(
            ExchangeParameters(
                sellCurrency = _uiState.value.selectedSellCurrency,
                buyCurrency = _uiState.value.selectedBuyCurrency,
                sellAmount = _uiState.value.sellAmount.toSafeDouble()
            )
        ).onEach { outcome ->
            when (outcome) {
                is Outcome.Success -> {
                    _uiState.update {
                        it.copy(
                            latestTransaction = outcome.data!!
                        )
                    }
                    _uiOneTimeEvents.emit(HomeOneTimeEvent.Exchanged(outcome.data!!))
                }

                is Outcome.Error -> {

                    _uiOneTimeEvents.emit(HomeOneTimeEvent.ExchangeError(outcome.error!!))
                }

                is Outcome.Loading -> {
                    // do nothing
                }

            }
        }.launchIn(viewModelScope)
    }


    fun selectSellCurrency(newCurrency: String) {
        _uiState.update {
            it.copy(
                selectedSellCurrency = newCurrency
            )
        }
        recalculateBuyAmount()
    }

    fun selectBuyCurrency(newCurrency: String) {
        _uiState.update {
            it.copy(
                selectedBuyCurrency = newCurrency
            )
        }
        recalculateBuyAmount()
    }

    fun updateSellAmount(newAmount: String) {
        val sanitizedAmount = if (newAmount.startsWith("0") && newAmount.length > 1) {
            newAmount.drop(1)
        } else {
            newAmount
        }
        _uiState.update {
            it.copy(sellAmount = sanitizedAmount.ifEmpty { "0" })
        }
        recalculateBuyAmount()
    }

    private fun recalculateBuyAmount() {
        viewModelScope.launch {
            val buyAmount = calculateReceiveAmountUseCase(
                ExchangeParameters(
                    sellCurrency = _uiState.value.selectedSellCurrency,
                    buyCurrency = _uiState.value.selectedBuyCurrency,
                    sellAmount = _uiState.value.sellAmount.toSafeDouble()
                )
            )
            _uiState.update {
                it.copy(
                    buyAmount = buyAmount.formatAmount()
                )
            }
        }
    }

    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }
}

data class HomeUiState(
    val isLoadingRates: Boolean = true,
    val selectedSellCurrency: String = "EUR",
    val selectedBuyCurrency: String = "USD",
    val sellAmount: String = "0",
    val buyAmount: String = "0",
    val error: Throwable? = null,
    val balances: List<Balance> = emptyList(),
    val allCurrencies: List<String> = emptyList(),
    val latestTransaction: Transaction? = null
)

sealed class HomeOneTimeEvent {
    data class Exchanged(val transaction: Transaction) : HomeOneTimeEvent()
    data class ExchangeError(val error: Throwable) : HomeOneTimeEvent()
}