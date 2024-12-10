package com.ruslanlialko.currencyexchanger.domain.repository

import com.ruslanlialko.currencyexchanger.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionsRepository {

    suspend fun deleteAllTransactions()

    suspend fun getTransactionsCount(): Int

    suspend fun addTransaction(transaction: Transaction)

    fun observeTransactions(): Flow<List<Transaction>>
}