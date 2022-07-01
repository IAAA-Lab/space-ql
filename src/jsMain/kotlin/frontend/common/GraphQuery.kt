package frontend.common

import application.model.MetadataPage
import application.model.MetadataRecord
import kotlinx.serialization.Serializable

@Serializable
data class GraphQuery(
    val query: String
    )

@Serializable
data class GraphResponse<T>(
    val data: T
)

@Serializable
data class SearchResponse(
    val search: MetadataPage
)

@Serializable
data class RecordResponse(
    val getRecord: MetadataRecord
)

@Serializable
data class RemoveRelatedResponse(
    val removeRelated: MetadataRecord
)