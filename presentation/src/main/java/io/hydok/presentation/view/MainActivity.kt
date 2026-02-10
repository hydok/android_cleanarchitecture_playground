package io.hydok.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import dagger.hilt.android.AndroidEntryPoint
import io.hydok.presentation.ui.theme.CleanArchitectureTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchSampleData("sample")

        setContent {
            var isDarkTheme by rememberSaveable { mutableStateOf(false) }
            val sampleData by viewModel.sampleData.observeAsState(emptyList())
            val isLoading by viewModel.isLoading.observeAsState(false)

            CleanArchitectureTheme(darkTheme = isDarkTheme) {
                MainScreen(
                    sampleData = sampleData,
                    isLoading = isLoading,
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { isDarkTheme = !isDarkTheme }
                )
            }
        }
    }
}
