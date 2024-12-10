package com.ruslanlialko.currencyexchanger.domain.use_case

import com.ruslanlialko.currencyexchanger.domain.model.Transaction
import com.ruslanlialko.currencyexchanger.domain.repository.TransactionsRepository
import com.ruslanlialko.currencyexchanger.domain.use_case.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class ObserveTransactionsUseCase(
    private val transactionsRepository: TransactionsRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowUseCase<Unit, List<Transaction>>(dispatcher) {
    override fun execute(params: Unit?): Flow<List<Transaction>> =
        transactionsRepository.observeTransactions()
}
