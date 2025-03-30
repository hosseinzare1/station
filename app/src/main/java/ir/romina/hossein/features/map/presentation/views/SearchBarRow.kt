package ir.romina.hossein.features.map.presentation.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchBarRow(
    modifier: Modifier,
    onSearchQueryChange: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchStationView(
            modifier = Modifier.weight(1f),
            onChange = onSearchQueryChange
        )
        SyncStateView(
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}