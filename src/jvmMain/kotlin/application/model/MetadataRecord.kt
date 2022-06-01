package application.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "sql_records")
data class MetadataRecord(
    @Id val ID: String,
    //@Field(type = FieldType.Nested, includeInParent = true)
    val title: String,
    val description: String,
    // deberia ser Service o Dataset
    var primaryTopic: Any? = null,
    val type: String,
    val details: ContentMetadata,
//    val content: String?
)

data class SubFacets(
    var field : String?,
    var docNum : Int?
)

data class Facets(
    var name: String?,
    var values: List<SubFacets>?
)
data class MetadataPage(
    val facets: List<Facets>,
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
    // Es uno solo
    val language: String?,
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
