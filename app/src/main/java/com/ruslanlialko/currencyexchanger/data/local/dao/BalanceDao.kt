package com.ruslanlialko.currencyexchanger.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruslanlialko.currencyexchanger.data.local.entity.BalanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBalance(balance: BalanceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setBalances(balance: List<BalanceEntity>)

    @Query("SELECT * FROM balances WHERE currency = :currency")
    suspend fun getBalanceByCurrency(currency: String): BalanceEntity?

    @Query("SELECT * FROM balances")
    fun getBalances(): Flow<List<BalanceEntity>>

    @Query("DELETE FROM balances")
    suspend fun deleteAllBalances()
}