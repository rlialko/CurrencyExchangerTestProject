package com.ruslanlialko.currencyexchanger.domain.model

import java.time.LocalDateTime

data class Transaction(
    val id: Long? = 0,
    val sellCurrency: String,
    val sellValue: Double,
    val buyCurrency: String,
    val buyValue: Double,
    val commission: Double,
    val date: LocalDateTime
)
