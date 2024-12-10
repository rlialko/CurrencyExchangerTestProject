package com.ruslanlialko.currencyexchanger.data.remote

import com.ruslanlialko.currencyexchanger.domain.model.RatesResponse
import retrofit2.http.GET

interface RatesService {

    @GET("currency-exchange-rates")
    suspend fun getRates(): RatesResponse
}