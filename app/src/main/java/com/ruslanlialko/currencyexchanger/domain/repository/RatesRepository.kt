package com.ruslanlialko.currencyexchanger.domain.repository

import com.ruslanlialko.currencyexchanger.domain.model.RatesResponse

interface RatesRepository {

    suspend fun fetchRates(): RatesResponse

    suspend fun saveRates(ratesResponse: RatesResponse)

    suspend fun getRates(): RatesResponse

}