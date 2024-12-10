package com.ruslanlialko.currencyexchanger.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ruslanlialko.currencyexchanger.data.local.AppDatabase
import com.ruslanlialko.currencyexchanger.data.local.dao.BalanceDao
import com.ruslanlialko.currencyexchanger.data.local.dao.RatesDao
import com.ruslanlialko.currencyexchanger.data.local.dao.TransactionDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.dsl.module

val dataModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        get<AppDatabase>().balanceDao().addBalance(AppDatabase.INITIAL_DATA)
                    }
                }
            }).build()
    }
    single<BalanceDao> { get<AppDatabase>().balanceDao() }
    single<TransactionDao> { get<AppDatabase>().transactionDao() }
    single<RatesDao> { get<AppDatabase>().ratesDao() }
}