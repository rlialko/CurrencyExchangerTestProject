package com.ruslanlialko.currencyexchanger.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ruslanlialko.currencyexchanger.data.local.converter.LocalDateConverter
import com.ruslanlialko.currencyexchanger.data.local.converter.LocalDateTimeConverter
import com.ruslanlialko.currencyexchanger.data.local.converter.MapTypeConverter
import com.ruslanlialko.currencyexchanger.data.local.dao.BalanceDao
import com.ruslanlialko.currencyexchanger.data.local.dao.RatesDao
import com.ruslanlialko.currencyexchanger.data.local.dao.TransactionDao
import com.ruslanlialko.currencyexchanger.data.local.entity.BalanceEntity
import com.ruslanlialko.currencyexchanger.data.local.entity.RatesEntity
import com.ruslanlialko.currencyexchanger.data.local.entity.TransactionEntity

@Database(
    entities = [BalanceEntity::class, TransactionEntity::class, RatesEntity::class],
    version = 1
)
@TypeConverters(LocalDateConverter::class, LocalDateTimeConverter::class, MapTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun balanceDao(): BalanceDao
    abstract fun transactionDao(): TransactionDao
    abstract fun ratesDao(): RatesDao

    companion object {
        const val DATABASE_NAME = "currency_exchanger_db"
        val INITIAL_DATA = BalanceEntity("EUR", 1000.0)
    }
}