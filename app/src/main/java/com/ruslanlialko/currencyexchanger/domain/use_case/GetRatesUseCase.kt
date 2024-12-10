package com.ruslanlialko.currencyexchanger.domain.use_case

import com.ruslanlialko.currencyexchanger.domain.exception.NetworkException
import com.ruslanlialko.currencyexchanger.domain.exception.UnknownException
import com.ruslanlialko.currencyexchanger.domain.model.RatesResponse
import com.ruslanlialko.currencyexchanger.domain.repository.RatesRepository
import com.ruslanlialko.currencyexchanger.domain.use_case.base.FlowUseCase
import com.ruslanlialko.currencyexchanger.domain.use_case.base.Outcome
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class GetRatesUseCase(
    private val ratesRepository: RatesRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowUseCase<Unit, Outcome<RatesResponse>>(dispatcher) {
    override fun execute(params: Unit?) = flow {
        try {
            emit(Outcome.Loading())
            val data = ratesRepository.fetchRates()
            ratesRepository.saveRates(data)
            emit(Outcome.Success(data))
        } catch (e: HttpException) {
            emit(Outcome.Error(UnknownException(e)))
        } catch (e: IOException) {
            emit(Outcome.Error(NetworkException(e)))
        }
    }
}