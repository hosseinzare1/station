package ir.romina.hossein.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ir.romina.hossein.R

@Composable
fun AppFailureView(
    title: String? = null,
    description: String? = null,
    onTryAgainClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title ?: stringResource(id = R.string.something_went_wrong),
        )
        Text(
            text = description ?: stringResource(id = R.string.please_try_again)
        )
        Button(
            onClick = onTryAgainClick
        ) {
            Text(
                text = stringResource(id = R.string.try_again)
            )
        }
    }
}