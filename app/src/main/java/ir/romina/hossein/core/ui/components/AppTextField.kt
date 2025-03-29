package ir.romina.hossein.core.ui.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun AppTextField(
    label: String = "",
    onChange: (String) -> Unit = {},
    maxLines: Int = 1,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier,
) {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        modifier = modifier,
        value = text,
        maxLines = maxLines,
        singleLine = singleLine,
        onValueChange = {
            text = it
            onChange(text)
        },
        label = {
            Text(label)
        },
    )
}