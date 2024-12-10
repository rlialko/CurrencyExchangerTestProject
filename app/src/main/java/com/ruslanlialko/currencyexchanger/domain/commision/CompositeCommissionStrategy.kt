package com.ruslanlialko.currencyexchanger.domain.commision

class CompositeCommissionStrategy : CommissionStrategy {
    private val strategies = mutableListOf<CommissionStrategy>()

    fun addStrategy(strategy: CommissionStrategy) {
        strategies.add(strategy)
    }

    override fun calculateCommission(
        transactionsCount: Int,
        amount: Double,
    ): Double {
        return strategies.sumOf { it.calculateCommission(transactionsCount, amount) }
    }
}