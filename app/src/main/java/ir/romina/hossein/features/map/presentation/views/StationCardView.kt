package ir.romina.hossein.features.map.presentation.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ir.romina.hossein.R
import ir.romina.hossein.core.ui.components.AppButton
import ir.romina.hossein.features.map.domain.entities.Station

@Composable
fun StationCardView(
    station: Station,
    isSelected: Boolean,
    onNavigationTap: () -> Unit,
    onDetailsTap: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.then(
            if (isSelected) {
                Modifier.border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            } else {
                Modifier
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = station.name ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = "${stringResource(R.string.capacity)}:${station.capacity}",
                style = MaterialTheme.typography.titleSmall,
            )
            if (station.rentalMethod != null)
                Text(
                    text = (station.rentalMethod.joinToString(separator = "\n") { it.name }),
                    style = MaterialTheme.typography.titleSmall,
                )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AppButton(
                    title = stringResource(id = R.string.navigate),
                    onClick = onNavigationTap,
                )
                AppButton(
                    title = stringResource(id = R.string.details),
                    onClick = onDetailsTap,
                )
            }
        }
    }
}