package com.ruslanlialko.currencyexchanger.data.local.mapper

import com.ruslanlialko.currencyexchanger.data.local.entity.BalanceEntity
import com.ruslanlialko.currencyexchanger.data.local.entity.RatesEntity
import com.ruslanlialko.currencyexchanger.data.local.entity.TransactionEntity
import com.ruslanlialko.currencyexchanger.domain.model.Balance
import com.ruslanlialko.currencyexchanger.domain.model.RatesResponse
import com.ruslanlialko.currencyexchanger.domain.model.Transaction

object Mapper {

    fun toEntity(ratesResponse: RatesResponse): RatesEntity {
        return RatesEntity(
            base = ratesResponse.base,
            date = ratesResponse.date,
            rates = ratesResponse.rates
        )
    }

    fun fromEntity(ratesEntity: RatesEntity): RatesResponse {
        return RatesResponse(
            base = ratesEntity.base,
            date = ratesEntity.date,
            rates = ratesEntity.rates
        )
    }

    fun fromEntity(entity: BalanceEntity): Balance {
        return Balance(
            currency = entity.currency,
            value = entity.value
        )
    }

    fun toEntity(balance: Balance): BalanceEntity {
        return BalanceEntity(
            currency = balance.currency,
            value = balance.value
        )
    }

    fun toEntity(transaction: Transaction): TransactionEntity {
        return TransactionEntity(
            sellCurrency = transaction.sellCurrency,
            buyCurrency = transaction.buyCurrency,
            sellValue = transaction.sellValue,
            buyValue = transaction.buyValue,
            commission = transaction.commission,
            date = transaction.date
        )
    }

    fun fromEntity(entity: TransactionEntity): Transaction {
        return Transaction(
            sellCurrency = entity.sellCurrency,
            buyCurrency = entity.buyCurrency,
            sellValue = entity.sellValue,
            buyValue = entity.buyValue,
            commission = entity.commission,
            date = entity.date
        )
    }

}