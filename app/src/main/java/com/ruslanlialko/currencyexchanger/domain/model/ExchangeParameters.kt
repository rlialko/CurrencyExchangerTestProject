package com.ruslanlialko.currencyexchanger.domain.model

data class ExchangeParameters(
    val sellCurrency: String,
    val buyCurrency: String,
    val sellAmount: Double
)