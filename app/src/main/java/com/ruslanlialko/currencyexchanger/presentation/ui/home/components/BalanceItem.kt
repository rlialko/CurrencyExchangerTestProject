package com.ruslanlialko.currencyexchanger.presentation.ui.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ruslanlialko.currencyexchanger.domain.model.Balance
import com.ruslanlialko.currencyexchanger.presentation.utils.formatCurrency

@Composable
fun BalanceItem(balance: Balance) {
    Card(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = balance.value.formatCurrency(balance.currency),
            modifier = Modifier.padding(16.dp)
        )
    }
}
