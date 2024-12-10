package com.ruslanlialko.currencyexchanger.domain.use_case

import com.ruslanlialko.currencyexchanger.domain.model.ExchangeParameters
import com.ruslanlialko.currencyexchanger.domain.repository.RatesRepository
import com.ruslanlialko.currencyexchanger.domain.use_case.base.SuspendUseCase
import com.ruslanlialko.currencyexchanger.domain.utils.roundPrice
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CalculateReceiveAmountUseCase(
    private val ratesRepository: RatesRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : SuspendUseCase<ExchangeParameters, Double>(dispatcher) {

    override suspend fun execute(params: ExchangeParameters?): Double {
        params ?: error("Params must not be null")
        val rates = ratesRepository.getRates().rates
        val sellRate = rates[params.sellCurrency] ?: return 0.0
        val buyRate = rates[params.buyCurrency] ?: return 0.0
        val receiveAmount = params.sellAmount * buyRate / sellRate
        return receiveAmount.roundPrice()
    }
}