package application.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "sql_records")
data class MetadataRecord(
    @Id val id: String,
    //@Field(type = FieldType.Nested, includeInParent = true)
    val title: String,
    val description: String,
    // deberia ser Service o Dataset
    // TODO: No va a estar almacenado en la bbdd(?) asi que se
    // inicializa a null por ahora
    val primaryTopic: Any? = null,
    val type: String,
    val details: ContentMetadata,
//    val content: String?
)

data class MetadataPage(
    val totalPages: Int,
    val metaData: List<MetadataRecord>
)


data class Format (
    val name: String,
    val version: String
    )

data class Transfer (
    val URL: String
    )

data class ContactPoint (
    val individual: String?,
    val phone: String?,
    val name: String?,
    val mail: String?,
    val onlineSource: String?,
)

data class ContentMetadata (
    val languages: List<String>?,
    val uploadDate: String?,
//    val scope: String?,
//    val standard: String?,
//    val standardVersion: String?,
//    val fileName: String?,
//    val fileDescription: String?,
    val contactPoint: ContactPoint,
    val accessUrl: String?,
    val distributionFormats: List<Format>?,
    val distributionTransfers: List<Transfer>?
)
