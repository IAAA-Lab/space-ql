package frontend

import Dataset
import MetaDataPage
import Resource
import Service
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

private val serializer = SerializersModule {
    polymorphic(Resource::class){
        subclass(Service::class)
        subclass(Dataset::class)
    }
}

val JSON = Json {
    serializersModule = serializer
}

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer(JSON) }
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
                    facets {
                        name,
                        values {
                            field,
                            docNum
                        }
                    }
                    totalPages,
                    metaData{
                        ID,
                        title,
                        description,
                        type,
                        primaryTopic{
                            __typename
                            ...on Dataset {
                                type,
                                title,
                                coupledServices{
                                    ID
                                }
                            }
                            ...on Service {
                                type,
                                title,
                                coupledDatasets{
                                    ID
                                }
                            }
                        }
                        details{
                            language,
                            uploadDate,
                            distributionFormats {
                                name
                            },
                            distributionTransfers {
                                URL
                            }
                        }
                    }
                }
            }""".trimIndent()
        )
    }

    return ret.data.search
}