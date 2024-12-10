package com.ruslanlialko.currencyexchanger.domain.use_case

import com.ruslanlialko.currencyexchanger.domain.exception.NetworkException
import com.ruslanlialko.currencyexchanger.domain.exception.UnknownException
import com.ruslanlialko.currencyexchanger.domain.model.RatesResponse
import com.ruslanlialko.currencyexchanger.domain.repository.RatesRepository
import com.ruslanlialko.currencyexchanger.domain.use_case.base.Outcome
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class GetRatesUseCaseTest {
    private lateinit var useCase: GetRatesUseCase
    private lateinit var repository: RatesRepository

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetRatesUseCase(repository, UnconfinedTestDispatcher())
    }

    @Test
    fun `test get rates success`() = runTest {
        val mockRatesResponse =
            RatesResponse(base = "USD", date = LocalDate.now(), rates = mapOf("EUR" to 0.85))
        coEvery { repository.fetchRates() } returns mockRatesResponse

        val result = useCase().toList()

        assertTrue(result[0] is Outcome.Loading)
        assertTrue(result[1] is Outcome.Success)
        assertEquals(mockRatesResponse, (result[1] as Outcome.Success).data)
    }

    @Test
    fun `test get rates http exception`() = runTest {
        val exception = mockk<HttpException>()
        coEvery { repository.fetchRates() } throws exception

        val result = useCase().toList()

        assertTrue(result[0] is Outcome.Loading)
        assertTrue(result[1] is Outcome.Error)
        assertTrue((result[1] as Outcome.Error).error is UnknownException)
    }

    @Test
    fun `test get rates io exception`() = runTest {
        val exception = IOException("Network error")
        coEvery { repository.fetchRates() } throws exception

        val result = useCase().toList()

        assertTrue(result[0] is Outcome.Loading)
        assertTrue(result[1] is Outcome.Error)
        assertTrue((result[1] as Outcome.Error).error is NetworkException)
    }

}