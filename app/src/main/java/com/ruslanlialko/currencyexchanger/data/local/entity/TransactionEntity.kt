package com.ruslanlialko.currencyexchanger.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val date: LocalDateTime,
    val sellCurrency: String,
    val sellValue: Double,
    val buyCurrency: String,
    val buyValue: Double,
    val commission: Double,
)