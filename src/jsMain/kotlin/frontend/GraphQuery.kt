package frontend

import kotlinx.serialization.Serializable

@Serializable
data class GraphQuery(
    val query: String
    )

@Serializable
data class GraphResponse(
    val data: String
)