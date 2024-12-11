package com.ruslanlialko.currencyexchanger.presentation.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ruslanlialko.currencyexchanger.presentation.theme.CurrencyExchangerTheme
import com.ruslanlialko.currencyexchanger.presentation.utils.getFlagEmoji

@Composable
fun CurrencyInputField(
    label: String,
    selectedCurrency: String,
    onCurrencyClick: () -> Unit,
    amount: String,
    onAmountChange: ((String) -> Unit) = {},
    amountReadOnly: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {

        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp,
                lineHeight = 16.sp,
            ),
            modifier = Modifier.padding(bottom =4.dp),
        )

        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = onCurrencyClick,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                ),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
                modifier = Modifier
                    .defaultMinSize(minWidth = 60.dp)
            ) {
                Text(
                    text = selectedCurrency.getFlagEmoji(),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp,
                        lineHeight = 24.sp,
                    ),
                    modifier = Modifier.padding(end = 16.dp),
                )

                Text(
                    text = selectedCurrency,
                    style = MaterialTheme.typography.titleMedium.copy(
                    )
                )
            }

            TextField(
                value = amount,
                onValueChange = onAmountChange,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                ),
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    textAlign = TextAlign.End, color = MaterialTheme.colorScheme.onSurface
                ),
                readOnly = amountReadOnly,
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CurrencyExchangerTheme {
        CurrencyInputField(
            selectedCurrency = "USD",
            onCurrencyClick = {},
            amount = "100.0",
            label = "Amount"
        )
    }
}
