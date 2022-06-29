package application.model

import kotlinx.serialization.Serializable

@Serializable
data class RelatedElements(
    var related : Boolean? = null,
    var relatedRecord : MetadataRecord? = null
)