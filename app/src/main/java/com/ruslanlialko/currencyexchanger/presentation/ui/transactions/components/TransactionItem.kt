package com.ruslanlialko.currencyexchanger.presentation.ui.transactions.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ruslanlialko.currencyexchanger.R
import com.ruslanlialko.currencyexchanger.domain.model.Transaction
import com.ruslanlialko.currencyexchanger.presentation.utils.formatDateTime

@Composable
fun TransactionItem(transaction: Transaction) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(
                id = R.string.transaction_format,
                transaction.sellValue,
                transaction.sellCurrency,
                transaction.buyValue,
                transaction.buyCurrency
            ),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(
                id = R.string.commission,
                transaction.commission,
                transaction.sellCurrency
            ),
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(
                id = R.string.date_format,
                transaction.date.formatDateTime()
            ),
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
    }
}