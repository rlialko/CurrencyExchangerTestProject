package com.ruslanlialko.currencyexchanger.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruslanlialko.currencyexchanger.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("SELECT COUNT(*) FROM transactions")
    suspend fun getTransactionsCount(): Int

    @Query("SELECT * FROM transactions")
    fun getTransactions(): Flow<List<TransactionEntity>>

    @Query("DELETE FROM transactions")
    suspend fun deleteAllTransactions()
}