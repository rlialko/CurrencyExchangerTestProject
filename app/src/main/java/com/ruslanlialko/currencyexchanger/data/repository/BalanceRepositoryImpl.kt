package com.ruslanlialko.currencyexchanger.data.repository

import com.ruslanlialko.currencyexchanger.data.local.dao.BalanceDao
import com.ruslanlialko.currencyexchanger.data.local.mapper.Mapper
import com.ruslanlialko.currencyexchanger.domain.model.Balance
import com.ruslanlialko.currencyexchanger.domain.repository.BalanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BalanceRepositoryImpl(
    private val balanceDao: BalanceDao
) : BalanceRepository {

    override fun observeBalances(): Flow<List<Balance>> {
        return balanceDao.getBalances().map { list ->
            list.map { Mapper.fromEntity(it) }
        }
    }

    override suspend fun setBalances(balances: List<Balance>) {
        balanceDao.setBalances(balances.map { Mapper.toEntity(it) })
    }

    override suspend fun deleteAllBalances() {
        balanceDao.deleteAllBalances()
    }

    override suspend fun setBalance(balance: Balance) {
        balanceDao.addBalance(Mapper.toEntity(balance))
    }

    override suspend fun getBalance(currency: String): Balance? {
        return balanceDao.getBalanceByCurrency(currency)?.let { Mapper.fromEntity(it) }
    }

}