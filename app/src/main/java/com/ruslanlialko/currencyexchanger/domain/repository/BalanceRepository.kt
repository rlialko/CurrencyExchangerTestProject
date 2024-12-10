package com.ruslanlialko.currencyexchanger.domain.repository

import com.ruslanlialko.currencyexchanger.domain.model.Balance
import kotlinx.coroutines.flow.Flow

interface BalanceRepository {

    fun observeBalances(): Flow<List<Balance>>

    suspend fun setBalances(balances: List<Balance>)

    suspend fun deleteAllBalances()

    suspend fun setBalance(balance: Balance)

    suspend fun getBalance(currency: String):Balance?
}