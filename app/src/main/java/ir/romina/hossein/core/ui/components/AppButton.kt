package ir.romina.hossein.core.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppButton(title: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(modifier = modifier, onClick = onClick) {
        Text(text = title)
    }
}