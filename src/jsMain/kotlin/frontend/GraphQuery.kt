package frontend

import MetaData
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