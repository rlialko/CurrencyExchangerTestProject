package com.ruslanlialko.currencyexchanger.data.repository

import com.ruslanlialko.currencyexchanger.data.local.dao.TransactionDao
import com.ruslanlialko.currencyexchanger.data.local.mapper.Mapper
import com.ruslanlialko.currencyexchanger.domain.model.Transaction
import com.ruslanlialko.currencyexchanger.domain.repository.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionsRepositoryImpl(
    private val transactionDao: TransactionDao
) : TransactionsRepository {

    override suspend fun deleteAllTransactions() {
        transactionDao.deleteAllTransactions()
    }

    override suspend fun getTransactionsCount(): Int {
        return transactionDao.getTransactionsCount()
    }

    override suspend fun addTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(Mapper.toEntity(transaction))
    }

    override fun observeTransactions(): Flow<List<Transaction>> {
        return transactionDao.getTransactions().map { list -> list.map { Mapper.fromEntity(it) } }
    }

}