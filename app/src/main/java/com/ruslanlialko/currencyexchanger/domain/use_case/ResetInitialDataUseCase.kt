package com.ruslanlialko.currencyexchanger.domain.use_case

import com.ruslanlialko.currencyexchanger.domain.model.Balance
import com.ruslanlialko.currencyexchanger.domain.repository.BalanceRepository
import com.ruslanlialko.currencyexchanger.domain.repository.TransactionsRepository
import com.ruslanlialko.currencyexchanger.domain.use_case.base.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class ResetInitialDataUseCase(
    private val balanceRepository: BalanceRepository,
    private val transactionsRepository: TransactionsRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : SuspendUseCase<Unit, Unit>(dispatcher) {
    override suspend fun execute(params: Unit?) {

        balanceRepository.deleteAllBalances()
        balanceRepository.setBalance(Balance("EUR", 1000.0))

        transactionsRepository.deleteAllTransactions()
    }
}