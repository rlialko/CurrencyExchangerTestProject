package com.ruslanlialko.currencyexchanger.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruslanlialko.currencyexchanger.data.local.entity.RatesEntity

@Dao
interface RatesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(rates: RatesEntity)

    @Query("SELECT * FROM rates")
    suspend fun getAllRates(): List<RatesEntity>
}