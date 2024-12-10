package com.ruslanlialko.currencyexchanger.domain.utils

import java.math.BigDecimal
import java.math.RoundingMode


fun Double.roundPrice(): Double {
    return BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN).toDouble()
}