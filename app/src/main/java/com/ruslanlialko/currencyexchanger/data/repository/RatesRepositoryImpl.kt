package com.ruslanlialko.currencyexchanger.data.repository

import com.ruslanlialko.currencyexchanger.data.local.dao.RatesDao
import com.ruslanlialko.currencyexchanger.data.local.mapper.Mapper
import com.ruslanlialko.currencyexchanger.data.remote.RatesService
import com.ruslanlialko.currencyexchanger.domain.model.RatesResponse
import com.ruslanlialko.currencyexchanger.domain.repository.RatesRepository

class RatesRepositoryImpl(
    private val ratesService: RatesService,
    private val ratesDao: RatesDao,
) : RatesRepository {

    override suspend fun fetchRates(): RatesResponse {
        return ratesService.getRates()
    }

    override suspend fun saveRates(ratesResponse: RatesResponse) {
        ratesDao.insertRates(Mapper.toEntity(ratesResponse))
    }

    override suspend fun getRates(): RatesResponse {
        return Mapper.fromEntity(ratesDao.getAllRates().first())
    }
}
