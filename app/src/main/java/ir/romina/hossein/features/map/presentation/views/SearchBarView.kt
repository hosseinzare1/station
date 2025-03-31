package ir.romina.hossein.features.map.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ir.romina.hossein.R
import ir.romina.hossein.core.ui.components.AppTextField

@Composable
fun SearchBarView(
    modifier: Modifier,
    onSearchQueryChange: (String) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppTextField(
            modifier = Modifier
                .padding(all = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface)
                .weight(1f),
            label = stringResource(R.string.search),
            onChange = onSearchQueryChange,
        )
        SyncStatusView(
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}