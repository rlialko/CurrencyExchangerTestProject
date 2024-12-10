package com.ruslanlialko.currencyexchanger.domain.commision

private const val EVERY_TENTH_TRANSACTION = 10
private const val COMMISSION_RATE = 0.5 / 100

class EveryNthFreeCommissionStrategy(
    private val nthTransaction: Int = EVERY_TENTH_TRANSACTION,
    private val commissionRate: Double = COMMISSION_RATE
) : CommissionStrategy {

    override fun calculateCommission(transactionsCount: Int, amount: Double): Double {

        return when {
            transactionsCount % nthTransaction == 0 -> 0.0
            else -> amount * commissionRate
        }
    }
}