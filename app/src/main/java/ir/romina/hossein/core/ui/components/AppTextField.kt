package ir.romina.hossein.core.ui.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun AppTextField(
    label: String = "",
    onChange: (String) -> Unit = {},
    onChangeWithDebounce: (String) -> Unit = {},
    maxLines: Int = 1,
    singleLine: Boolean = true,
    debounceDelay: Long = 250L,
    modifier: Modifier = Modifier,
) {
    var text by rememberSaveable { mutableStateOf("") }
    var debouncedText by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(text) {
        onChange(text)
        delay(debounceDelay)
        debouncedText = text
        onChangeWithDebounce(debouncedText)
    }

    TextField(
        modifier = modifier,
        value = text,
        maxLines = maxLines,
        singleLine = singleLine,
        onValueChange = {
            text = it
        },
        label = {
            Text(label)
        },
    )
}