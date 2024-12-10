package com.ruslanlialko.currencyexchanger.presentation.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ruslanlialko.currencyexchanger.R
import org.koin.androidx.compose.koinViewModel


@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            SettingsUiState.Initial -> {
                SettingsInitialState {
                    viewModel.resetInitialBalance()
                }
            }
        }
    }
}

@Composable
fun SettingsInitialState(
    onSetBalance: () -> Unit
) {

    Button(
        onClick = {
            onSetBalance()
        }, modifier = Modifier.padding(16.dp)
    ) {
        Text(text = stringResource(R.string.reset_button))
    }
}
