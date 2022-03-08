package frontend

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

suspend fun getResults(input: String) {
    val ret: HttpResponse = jsonClient.post("http://localhost:8080/graphql") {
        contentType(ContentType.Application.Json)
        body = GraphQuery("{allMetadata { title}}")
    }
    val value: String = ret.receive()
    console.log(value)
}