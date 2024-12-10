package com.ruslanlialko.currencyexchanger.domain.use_case

import com.ruslanlialko.currencyexchanger.domain.commision.CompositeCommissionStrategy
import com.ruslanlialko.currencyexchanger.domain.commision.StandardCommissionStrategy
import com.ruslanlialko.currencyexchanger.domain.exception.ExchangeException
import com.ruslanlialko.currencyexchanger.domain.model.Balance
import com.ruslanlialko.currencyexchanger.domain.model.ExchangeParameters
import com.ruslanlialko.currencyexchanger.domain.model.Transaction
import com.ruslanlialko.currencyexchanger.domain.repository.BalanceRepository
import com.ruslanlialko.currencyexchanger.domain.repository.TransactionsRepository
import com.ruslanlialko.currencyexchanger.domain.use_case.base.FlowUseCase
import com.ruslanlialko.currencyexchanger.domain.use_case.base.Outcome
import com.ruslanlialko.currencyexchanger.domain.utils.roundPrice
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime

class ExchangeUseCase(
    private val balanceRepository: BalanceRepository,
    private val transactionsRepository: TransactionsRepository,
    private val calculateReceiveAmountUseCase: CalculateReceiveAmountUseCase,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowUseCase<ExchangeParameters, Outcome<Transaction>>(dispatcher) {


    override fun execute(params: ExchangeParameters?): Flow<Outcome<Transaction>> = flow {
        emit(Outcome.Loading())
        params ?: run {
            emit(Outcome.Error(ExchangeException("Params must not be null")))
            return@flow
        }

        if (params.sellCurrency == params.buyCurrency) {
            emit(Outcome.Error(ExchangeException("Currencies must not be the same")))
            return@flow
        }

        if (params.sellAmount <= 0) {
            emit(Outcome.Error(ExchangeException("Amount must be greater than 0")))
            return@flow
        }

        val currentSellBalance = balanceRepository.getBalance(params.sellCurrency) ?: run {
            emit(Outcome.Error(ExchangeException("Balance not found")))
            return@flow
        }

        val commissionStrategies = CompositeCommissionStrategy().apply {
            addStrategy(StandardCommissionStrategy())
            // add more strategies here
        }

        val commission = commissionStrategies.calculateCommission(
            transactionsRepository.getTransactionsCount(), params.sellAmount
        ).roundPrice()

        val sellBalanceAfterTransaction = currentSellBalance.value - params.sellAmount - commission

        if (sellBalanceAfterTransaction < 0) {
            emit(Outcome.Error(ExchangeException("Not enough balance")))
            return@flow
        }

        val newSellBalance = Balance(
            currency = params.sellCurrency, value = sellBalanceAfterTransaction
        )

        val calculatedBuyAmount = calculateReceiveAmountUseCase(params)

        val currentBuyBalance = balanceRepository.getBalance(params.buyCurrency)
            ?: Balance(currency = params.buyCurrency, value = 0.0)
        val newBuyBalance = currentBuyBalance.copy(
            value = currentBuyBalance.value + calculatedBuyAmount
        )

        val transaction = Transaction(
            sellCurrency = params.sellCurrency,
            buyCurrency = params.buyCurrency,
            sellValue = params.sellAmount,
            buyValue = calculatedBuyAmount,
            commission = commission,
            date = LocalDateTime.now()
        )

        transactionsRepository.addTransaction(transaction)
        balanceRepository.setBalances(listOf(newSellBalance, newBuyBalance))
        emit(Outcome.Success(transaction))
    }
}