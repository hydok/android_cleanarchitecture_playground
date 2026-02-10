package io.hydok.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.hydok.domain.model.SampleModel

@Composable
fun MainScreen(
    sampleData: List<SampleModel>,
    isLoading: Boolean,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isLoading) {
                Text(
                    text = "Loading...",
                    color = MaterialTheme.colorScheme.onBackground
                )
            } else {
                Text(
                    text = sampleData.joinToString { it.name },
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onToggleTheme) {
                Text(if (isDarkTheme) "Light Mode" else "Dark Mode")
            }
        }
    }
}
