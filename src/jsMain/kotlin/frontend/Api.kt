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


val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
        isLenient = false
        ignoreUnknownKeys = false
        allowSpecialFloatingPointValues = true
        useArrayPolymorphism = false
        SerializersModule {
            polymorphic(Resource::class) {
                Service::class  Service.serializer()
                Dataset::class  Dataset.serializer()
            }
        }
    }) }
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
                        ID,
                        title,
                        description,
                        type,
                        primaryTopic{
                            __typename
                            ...on Dataset {
                                title,
                                coupledServices{
                                    ID
                                }
                            }
                            ...on Service {
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