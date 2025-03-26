package ir.romina.hossein.features.map.data.remote.datasources

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import ir.romina.hossein.features.map.data.remote.models.StationModel

class StationRemoteDataSource(private val httpClient: HttpClient) {

    suspend fun getStations(): List<StationModel> {
        val response: HttpResponse = httpClient.get("cc4571b3-9503-439f-8e51-5001b0de3217")
        return response.body()
    }

}