package com.ruslanlialko.currencyexchanger.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "rates")
data class RatesEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0,
    val base: String,
    val date: LocalDate,
    val rates: Map<String, Double>
)
