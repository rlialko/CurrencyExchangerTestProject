package com.ruslanlialko.currencyexchanger.domain.use_case

import com.ruslanlialko.currencyexchanger.domain.exception.ExchangeException
import com.ruslanlialko.currencyexchanger.domain.model.Balance
import com.ruslanlialko.currencyexchanger.domain.model.ExchangeParameters
import com.ruslanlialko.currencyexchanger.domain.model.Transaction
import com.ruslanlialko.currencyexchanger.domain.repository.BalanceRepository
import com.ruslanlialko.currencyexchanger.domain.repository.TransactionsRepository
import com.ruslanlialko.currencyexchanger.domain.use_case.base.Outcome
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class ExchangeUseCaseTest {
    private lateinit var useCase: ExchangeUseCase
    private lateinit var balanceRepository: BalanceRepository
    private lateinit var transactionsRepository: TransactionsRepository
    private lateinit var calculateReceiveAmountUseCase: CalculateReceiveAmountUseCase

    @Before
    fun setUp() {
        balanceRepository = mockk()
        transactionsRepository = mockk()
        calculateReceiveAmountUseCase = mockk()
        useCase = ExchangeUseCase(
            balanceRepository,
            transactionsRepository,
            calculateReceiveAmountUseCase,
            UnconfinedTestDispatcher()
        )
    }

    @Test
    fun `test exchange success`() = runTest {
        val params = ExchangeParameters("USD", "EUR", 100.0)
        val sellBalance = Balance("USD", 200.0)
        val buyBalance = Balance("EUR", 100.0)
        val calculatedBuyAmount = 85.0
        val transaction = Transaction(
            sellCurrency = "USD",
            buyCurrency = "EUR",
            sellValue = 100.0,
            buyValue = calculatedBuyAmount,
            commission = 0.0,
            date = LocalDateTime.now()
        )

        coEvery { balanceRepository.getBalance("USD") } returns sellBalance
        coEvery { balanceRepository.getBalance("EUR") } returns buyBalance
        coEvery { transactionsRepository.getTransactionsCount() } returns 1
        coEvery { calculateReceiveAmountUseCase(params) } returns calculatedBuyAmount
        coEvery { transactionsRepository.addTransaction(any()) } returns Unit
        coEvery { balanceRepository.setBalances(any()) } returns Unit

        val result = useCase(params).toList()

        assertTrue(result[0] is Outcome.Loading)
        assertTrue(result[1] is Outcome.Success)
        assertEquals(transaction.sellCurrency, (result[1] as Outcome.Success).data!!.sellCurrency)
        assertEquals(transaction.buyCurrency, (result[1] as Outcome.Success).data!!.buyCurrency)
        assertEquals(transaction.sellValue, (result[1] as Outcome.Success).data!!.sellValue, 0.0)
        assertEquals(transaction.buyValue, (result[1] as Outcome.Success).data!!.buyValue, 0.0)
        assertEquals(transaction.commission, (result[1] as Outcome.Success).data!!.commission, 0.0)

        coVerify { transactionsRepository.addTransaction(any()) }
        coVerify { balanceRepository.setBalances(any()) }
    }

    @Test
    fun `test exchange with same currencies`() = runTest {
        val params = ExchangeParameters("USD", "USD", 100.0)

        val result = useCase(params).toList()

        assertTrue(result[1] is Outcome.Error)
        assertTrue((result[1] as Outcome.Error).error is ExchangeException)
        assertEquals(
            "Currencies must not be the same",
            (result[1] as Outcome.Error).error!!.message
        )
    }

    @Test
    fun `test exchange with insufficient balance`() = runTest {
        val params = ExchangeParameters("USD", "EUR", 300.0)
        val sellBalance = Balance("USD", 200.0)

        coEvery { balanceRepository.getBalance("USD") } returns sellBalance
        coEvery { transactionsRepository.getTransactionsCount() } returns 1

        val result = useCase(params).toList()

        assertTrue(result[1] is Outcome.Error)
        assertTrue((result[1] as Outcome.Error).error is ExchangeException)
        assertEquals("Not enough balance", (result[1] as Outcome.Error).error!!.message)
    }

    @Test
    fun `test exchange with invalid amount`() = runTest {
        val params = ExchangeParameters("USD", "EUR", -100.0)

        val result = useCase(params).toList()

        assertTrue(result[1] is Outcome.Error)
        assertTrue((result[1] as Outcome.Error).error is ExchangeException)
        assertEquals("Amount must be greater than 0", (result[1] as Outcome.Error).error!!.message)
    }
}