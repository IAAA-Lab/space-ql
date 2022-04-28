package frontend

import MetaData
import MetaDataPage
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
data class AllMetadataResponse(
    val allMetadata: List<MetaData>
)
@Serializable
data class SearchResponse(
    val search: MetaDataPage
)