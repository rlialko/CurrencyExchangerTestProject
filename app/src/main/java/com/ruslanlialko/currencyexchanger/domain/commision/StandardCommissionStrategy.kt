package com.ruslanlialko.currencyexchanger.domain.commision

private const val MAX_FREE_TRANSACTIONS_COUNT = 5
private const val COMMISSION_RATE = 0.7 / 100

class StandardCommissionStrategy(
    private val maxFreeTransactionsCount: Int = MAX_FREE_TRANSACTIONS_COUNT,
    private val commissionRate: Double = COMMISSION_RATE,
) : CommissionStrategy {

    override fun calculateCommission(transactionsCount: Int, amount: Double): Double {

        return when {
            transactionsCount < maxFreeTransactionsCount -> 0.0
            else -> amount * commissionRate
        }
    }
}