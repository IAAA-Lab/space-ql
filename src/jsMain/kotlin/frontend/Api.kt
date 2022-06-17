package frontend

import Dataset
import MetaDataPage
import MetadataRecord
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

suspend fun getSingleResult(id: String) : MetadataRecord{

    val ret: GraphResponse<RecordResponse> = jsonClient.post("http://localhost:8080/graphql") {
        contentType(ContentType.Application.Json)

        body = GraphQuery("""
        {
            getRecord(id:"${id}") {
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
                            related,
                            relatedRecord {
                                ID,
                                title,
                                description,
                                type,
                                details {
                                    language,
                                    uploadDate,
                                    distributionFormats {
                                        name
                                    },
                                    distributionTransfers {
                                        URL
                                    }
                                },
                                primaryTopic{
                                    __typename
                                    ...on Dataset {
                                        type,
                                        title,
                                        coupledServices{
                                            relatedRecord {
                                                ID
                                            },
                                            related
                                        }
                                    }
                                    ...on Service {
                                        type,
                                        title,
                                        coupledDatasets{
                                            relatedRecord {
                                                ID
                                            },
                                            related
                                        }
                                    }
                                }
                            }
                            
                        },
                    },
                    ...on Service {
                        type,
                        title,
                        coupledDatasets{
                            related,
                            relatedRecord {
                                ID,
                                title,
                                description,
                                type,
                                details {
                                    language,
                                    uploadDate,
                                    distributionFormats {
                                        name
                                    },
                                    distributionTransfers {
                                        URL
                                    }
                                },
                                primaryTopic{
                                    __typename
                                    ...on Dataset {
                                        type,
                                        title,
                                        coupledServices{
                                            relatedRecord {
                                                ID
                                            },
                                            related
                                        }
                                    }
                                    ...on Service {
                                        type,
                                        title,
                                        coupledDatasets{
                                            relatedRecord {
                                                ID
                                            },
                                            related
                                        }
                                    }
                                }
                            }
                            
                        }
                    }
                },
                details{
                    language,
                    uploadDate,
                    contactPoint {
                        individual,
                        phone,
                        name,
                        mail,
                        onlineSource
                    },
                    distributionFormats {
                        name
                    },
                    distributionTransfers {
                        URL
                    }
                }
        
            }
        }
        """.trimIndent()
        )
    }

    return ret.data.getRecord

}

suspend fun getResults(input: String?, limit: Int, offset: Int, order: String, language: List<String>, resType: List<String>, related: List<String>, contactPoints: List<String>): MetaDataPage {
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
                order: "$order",
                language: [${language.joinToString { "\"${it}\"" }}],
                resType: [${resType.joinToString { "\"${it}\"" }}],
                related: [${related.joinToString { "\"${it}\"" }}],
                contactPoints: [${contactPoints.joinToString { "\"${it}\"" }}]) {
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
                                    relatedRecord {
                                        ID
                                    },
                                    related
                                }
                            }
                            ...on Service {
                                type,
                                title,
                                coupledDatasets{
                                    relatedRecord {
                                        ID
                                    },
                                    related
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

suspend fun removeRelated(recordId: String, relatedId: String) : MetadataRecord{

    val ret: GraphResponse<RemoveRelatedResponse> = jsonClient.post("http://localhost:8080/graphql") {
        contentType(ContentType.Application.Json)

        body = GraphMutation("""
        {
            removeRelated( recordId: "$recordId", relatedId: "$relatedId") {
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
                            related,
                            relatedRecord {
                                ID,
                                title,
                                description,
                                type,
                                details {
                                    language,
                                    uploadDate,
                                    distributionFormats {
                                        name
                                    },
                                    distributionTransfers {
                                        URL
                                    }
                                },
                                primaryTopic{
                                    __typename
                                    ...on Dataset {
                                        type,
                                        title,
                                        coupledServices{
                                            relatedRecord {
                                                ID
                                            },
                                            related
                                        }
                                    }
                                    ...on Service {
                                        type,
                                        title,
                                        coupledDatasets{
                                            relatedRecord {
                                                ID
                                            },
                                            related
                                        }
                                    }
                                }
                            }
                            
                        },
                    },
                    ...on Service {
                        type,
                        title,
                        coupledDatasets{
                            related,
                            relatedRecord {
                                ID,
                                title,
                                description,
                                type,
                                details {
                                    language,
                                    uploadDate,
                                    distributionFormats {
                                        name
                                    },
                                    distributionTransfers {
                                        URL
                                    }
                                },
                                primaryTopic{
                                    __typename
                                    ...on Dataset {
                                        type,
                                        title,
                                        coupledServices{
                                            relatedRecord {
                                                ID
                                            },
                                            related
                                        }
                                    }
                                    ...on Service {
                                        type,
                                        title,
                                        coupledDatasets{
                                            relatedRecord {
                                                ID
                                            },
                                            related
                                        }
                                    }
                                }
                            }
                            
                        }
                    }
                },
                details{
                    language,
                    uploadDate,
                    contactPoint {
                        individual,
                        phone,
                        name,
                        mail,
                        onlineSource
                    },
                    distributionFormats {
                        name
                    },
                    distributionTransfers {
                        URL
                    }
                }
        
            }
        }
        """.trimIndent()
        )
    }

    return ret.data.removeRelated

}