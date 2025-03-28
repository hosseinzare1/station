package ir.romina.hossein.features.map.presentation.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import ir.romina.hossein.features.map.domain.entities.StationEntity

@Composable
fun MapCircleView(
    station: StationEntity,
    maxCapacity: Short,
    onClick: (Circle) -> Unit,
) {
    val ratio: Float = rememberSaveable { station.capacity.toFloat() / maxCapacity }
    Circle(
        center = LatLng(station.lat, station.lon),
        clickable = true,
        radius = station.capacity * 10.0 + 100.0,
        strokeWidth = 2f,
        strokeColor = Color.Blue,
        fillColor = when {
            ratio < 0.4f -> Color(0xFF4CAF50)
            ratio < 0.8f -> Color(0xFFFF9800)
            else -> Color(0xFFF44336)
        },
        onClick = onClick
    )
}
