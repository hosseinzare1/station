package ir.romina.hossein.features.map.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ir.romina.hossein.R
import ir.romina.hossein.core.enums.OperationStatus
import ir.romina.hossein.core.ui.components.AppLoadingIndicator
import ir.romina.hossein.features.map.presentation.manager.synchronize.SynchronizeIntent
import ir.romina.hossein.features.map.presentation.manager.synchronize.SynchronizeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SyncStateView(modifier: Modifier = Modifier) {

    val viewModel: SynchronizeViewModel = koinViewModel()

    val state by viewModel.state.collectAsState()


    Box(modifier = modifier.size(48.dp).background(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.small
    ), contentAlignment = Alignment.Center) {
        when (state.synchronizeOperationsStatus) {
            OperationStatus.IDLE -> Unit
            OperationStatus.LOADING -> AppLoadingIndicator(
            )

            OperationStatus.SUCCESS -> Icon(
                imageVector = Icons.Rounded.Check,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = stringResource(R.string.success)
            )

            OperationStatus.ERROR -> IconButton(
                onClick = {
                    viewModel.handleIntent(SynchronizeIntent.SyncStations)
                }) {
                Icon(
                    imageVector = Icons.Rounded.Refresh,
                    tint = MaterialTheme.colorScheme.error,
                    contentDescription = stringResource(R.string.error)
                )
            }
        }
    }


}