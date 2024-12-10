package com.ruslanlialko.currencyexchanger.domain.commision

interface CommissionStrategy {
    fun calculateCommission(
        transactionsCount: Int,
        amount: Double,
    ): Double
}