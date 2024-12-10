package com.ruslanlialko.currencyexchanger.domain.commision

private const val COMMISSION = 1.0

class FlatCommissionStrategy(
    private val commission: Double = COMMISSION
) : CommissionStrategy {

    override fun calculateCommission(transactionsCount: Int, amount: Double): Double {
        return commission
    }
}