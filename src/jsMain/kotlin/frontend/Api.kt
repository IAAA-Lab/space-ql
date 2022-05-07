package frontend

import MetaData
import MetaDataPage
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

suspend fun getResults(input: String?, limit: Int, offset: Int, order: String): MetaDataPage {
    val ret: GraphResponse<SearchResponse> = jsonClient.post("http://localhost:8080/graphql") {
        contentType(ContentType.Application.Json)
        var graphSearchText = ""
        if(input != null && input != "") {
            graphSearchText = """text: "$input","""
        }

        body = GraphQuery("""
            {
                search(${graphSearchText}
                limit: ${limit},
                offset: ${offset},
                order: "$order") {
                    totalPages,
                    metaData{
                        id,
                        data{
                            fileName,
                            fileDescription
                        }
                    }
                }
            }""".trimIndent()
        )
    }

    return ret.data.search
}

//suspend fun getResults(limit: Int, offset: Int): List<MetaData> {
//    val ret: GraphResponse<AllMetadataResponse> = jsonClient.post("http://localhost:8080/graphql") {
//        contentType(ContentType.Application.Json)
//        body = GraphQuery("""
//            {
//                allMetadata {
//                    data {
//                        fileName
//                        fileDescription
//                    }
//                }
//            }""".trimIndent()
//        )
//    }
//
//    return ret.data.allMetadata
//}