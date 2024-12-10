package com.ruslanlialko.currencyexchanger.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "balances")
data class BalanceEntity(
    @PrimaryKey
    val currency: String,
    val value: Double,
)