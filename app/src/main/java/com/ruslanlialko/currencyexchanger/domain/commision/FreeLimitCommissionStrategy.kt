package com.ruslanlialko.currencyexchanger.domain.commision

private const val FREE_LIMIT = 200
private const val COMMISSION_RATE = 0.5 / 100

class FreeLimitCommissionStrategy(
    private val freeLimit: Int = FREE_LIMIT,
    private val commissionRate: Double = COMMISSION_RATE,
) : CommissionStrategy {

    override fun calculateCommission(transactionsCount: Int, amount: Double): Double {

        return when {
            amount < freeLimit -> 0.0
            else -> amount * commissionRate
        }
    }
}