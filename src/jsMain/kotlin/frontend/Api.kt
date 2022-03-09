package frontend

import MetaData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

suspend fun getResults(input: String): List<MetaData> {
    val ret: GraphResponse<AllMetadataResponse> = jsonClient.post("http://localhost:8080/graphql") {
        contentType(ContentType.Application.Json)
        body = GraphQuery("{allMetadata {id\ntitle}}")
    }

    // If ret is HttpResponse
    // val value: String = ret.receive()

    return ret.data.allMetadata
}