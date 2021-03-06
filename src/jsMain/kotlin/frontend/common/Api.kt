package frontend.common

import application.model.*
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlinx.serialization.json.Json

// tag::serializerModule[]
private val serializer = SerializersModule {
    polymorphic(Resource::class){
        subclass(cliService::class) // <1>
        subclass(cliDataset::class) // <1>
    }
}

val JSON = Json {
    serializersModule = serializer
}

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer(JSON) }
}
// end::serializerModule[]

// tag::singleRes[]
suspend fun getSingleResult(id: String) : MetadataRecord {
    val ret: GraphResponse<RecordResponse> = jsonClient.post("http://localhost:8080/graphql") {
        contentType(ContentType.Application.Json)
// end::singleRes[]
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
                        organization {
                            name,
                            subOrganization,
                            wholeName
                        },
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

suspend fun getResults(input: String?, limit: Int, offset: Int, order: String, language: List<String>,
                       resType: List<String>, related: List<String>,
                       contactPoints: List<String>, formats: List<String>): MetadataPage {
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
                contactPoints: [${contactPoints.joinToString { "\"${it}\"" }}],
                formats: [${formats.joinToString { "\"${it}\"" }}]) {
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


suspend fun getSuggestions(input: String?): List<String> {
    val ret: GraphResponse<SuggestReponse> = jsonClient.post("http://localhost:8080/graphql") {
        contentType(ContentType.Application.Json)

        body = GraphQuery("""
            {
                getSuggestions( text: "$input" )
            }""".trimIndent()
        )
    }

    return ret.data.getSuggestions
}

suspend fun getTitles(): List<String> {
    val ret: GraphResponse<TitlesResponse> = jsonClient.post("http://localhost:8080/graphql") {
        contentType(ContentType.Application.Json)

        body = GraphQuery("""
            {
                getTitles
            }""".trimIndent()
        )
    }

    return ret.data.getTitles
}



suspend fun removeRelated(recordId: String, relatedId: String) : MetadataRecord{

    val ret: GraphResponse<RemoveRelatedResponse> = jsonClient.post("http://localhost:8080/graphql") {
        contentType(ContentType.Application.Json)

        body = GraphQuery("""
            mutation {
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