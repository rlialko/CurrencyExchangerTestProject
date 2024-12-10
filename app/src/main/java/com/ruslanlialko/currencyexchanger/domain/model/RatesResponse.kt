package com.ruslanlialko.currencyexchanger.domain.model

import java.time.LocalDate

data class RatesResponse(
    val base: String,
    val date: LocalDate,
    val rates: Map<String, Double>
)