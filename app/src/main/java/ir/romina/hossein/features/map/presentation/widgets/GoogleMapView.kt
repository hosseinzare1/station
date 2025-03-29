package ir.romina.hossein.features.map.presentation.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import ir.romina.hossein.features.map.domain.entities.Station

@Composable
fun GoogleMapView(
    stations: List<Station>,
    cameraPositionState: CameraPositionState,
    onClick: (station: Station) -> Unit,
) {
    val maxCapacity = stations.maxByOrNull { it.capacity }?.capacity ?: 0

    GoogleMap(
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false
        )
    ) {
        stations.map { station ->
            val ratio: Float = rememberSaveable { station.capacity.toFloat() / maxCapacity }
            Circle(
                center = LatLng(station.lat, station.lon),
                clickable = true,
                radius = station.capacity * 10.0 + 100.0,
                strokeWidth = 2f,
                strokeColor = MaterialTheme.colorScheme.primary,
                fillColor = when {
                    ratio < 0.4f -> Color(0xFFF44336)
                    ratio < 0.8f -> Color(0xFFFF9800)
                    else -> Color(0xFF4CAF50)
                },
                onClick = {
                    onClick(station)
                }
            )
        }
    }
}
