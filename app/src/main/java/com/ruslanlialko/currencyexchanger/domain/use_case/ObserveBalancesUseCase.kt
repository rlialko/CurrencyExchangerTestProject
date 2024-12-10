package com.ruslanlialko.currencyexchanger.domain.use_case

import com.ruslanlialko.currencyexchanger.domain.model.Balance
import com.ruslanlialko.currencyexchanger.domain.repository.BalanceRepository
import com.ruslanlialko.currencyexchanger.domain.use_case.base.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class ObserveBalancesUseCase(
    private val balanceRepository: BalanceRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowUseCase<Unit, List<Balance>>(dispatcher) {
    override fun execute(params: Unit?): Flow<List<Balance>> =
        balanceRepository.observeBalances()
}
