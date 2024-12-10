package com.ruslanlialko.currencyexchanger.presentation.utils

import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun Double.formatCurrency(currency: String): String {
    val df = DecimalFormat("0.00")
    return "$currency ${df.format(this)}"
}

fun Double.formatAmount(): String {
    val df = DecimalFormat("0.00")
    return df.format(this)
}

fun String.toSafeDouble(): Double {
    return try {
        this.toDouble()
    } catch (e: NumberFormatException) {
        0.0
    }
}


fun LocalDateTime.formatDateTime(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return this.format(formatter)
}
