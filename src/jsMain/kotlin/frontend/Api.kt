package frontend

import MetaData
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}


suspend fun getResults(input: String): List<MetaData> {
    val ret = jsonClient.post<List<MetaData>>("http://localhost:8080/graphql") {
        contentType(ContentType.Application.Json)
        body = "{\"query\": \"{allMetadata { title}}\"}"
    }

    return ret
}