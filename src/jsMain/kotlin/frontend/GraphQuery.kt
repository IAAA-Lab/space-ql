package frontend

import MetadataRecord
import MetaDataPage
import kotlinx.serialization.Serializable

@Serializable
data class GraphQuery(
    val query: String
    )

@Serializable
data class GraphMutation(
    val mutation: String
)

@Serializable
data class GraphResponse<T>(
    val data: T
)

@Serializable
data class AllMetadataResponse(
    val allMetadata: List<MetadataRecord>
)
@Serializable
data class SearchResponse(
    val search: MetaDataPage
)

@Serializable
data class RecordResponse(
    val getRecord: MetadataRecord
)

@Serializable
data class RemoveRelatedResponse(
    val removeRelated: MetadataRecord
)