package ir.romina.hossein.features.map.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import ir.romina.hossein.R
import ir.romina.hossein.features.map.domain.entities.Station

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationDetailsScreen(
    station: Station,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    station.name?.let { Text(it) }
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )

        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InfoView(
                    title = stringResource(R.string.capacity),
                    value = station.capacity.toString(),
                )
                InfoView(
                    title = stringResource(R.string.latitude),
                    value = station.lat.toString(),
                )
                InfoView(
                    title = stringResource(R.string.longitude),
                    value = station.lon.toString(),
                )
                if (station.rentalMethod != null)
                    InfoView(
                        title = stringResource(R.string.payment_methods),
                        value = station.rentalMethod.joinToString(separator = "\n") { it.name },
                    )
            }
        }
    }
}

@Composable
fun InfoView(title: String, value: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            append(title)
            append(":")
            append("\n")
            withStyle(style = SpanStyle(fontSize = MaterialTheme.typography.titleSmall.fontSize)) {
                append(value)
            }
        },
        style = MaterialTheme.typography.titleLarge,
    )
}
