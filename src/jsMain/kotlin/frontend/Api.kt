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
    val ret: GraphResponse<SearchResponse> = jsonClient.post("http://localhost:8080/graphql") {
        contentType(ContentType.Application.Json)
        body = GraphQuery("""
            {
                search(text: "${input}") {
                    data {
                        fileName
                        fileDescription
                    }
                }
            }""".trimIndent()
        )
    }
    // If ret is HttpResponse
    // val value: String = ret.receive()

    (ret.data.search)
    println("A")

    return ret.data.search
}

suspend fun getResults(): List<MetaData> {
    val ret: GraphResponse<AllMetadataResponse> = jsonClient.post("http://localhost:8080/graphql") {
        contentType(ContentType.Application.Json)
        body = GraphQuery("""
            {
                allMetadata {
                    data {
                        fileName
                        fileDescription
                    }
                }
            }""".trimIndent()
        )
    }
    // If ret is HttpResponse
    // val value: String = ret.receive()

    (ret.data.allMetadata)

    return ret.data.allMetadata
}