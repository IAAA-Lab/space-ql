package application.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

// tag::mdrecord[]
@Document(indexName = "sql_records") // <7>
data class MetadataRecord(
    @Id val ID: String, // <1>
    val title: String,  // <2>
    val description: String, // <3>
    var primaryTopic: Any? = null, // <4>
    val type: String,   // <5>
    val details: ContentMetadata, // <6>
)
// end::mdrecord[]

// tag::facets[]
data class SubFacets(       // <2>
    var field : String?,    // <3>
    var docNum : Int?       // <4>
)
data class Facets(
    var name: String?,      // <1>
    var values: List<SubFacets>? // <2>
)
// end::facets[]
// tag::mdpage[]
data class MetadataPage(
    val facets: List<Facets>,   // <1>
    val totalPages: Int,        // <2>
    val metaData: List<MetadataRecord>  // <3>
)
//end::mdpage[]

// tag::formtrans[]
data class Format (
    val name: String,
    val version: String
    )
data class Transfer (
    val URL: String
    )
// end::formtrans[]

// tag::contactpoint[]
data class ContactPoint (
    val individual: String?,
    val phone: String?,
    val name: String?,
    val mail: String?,
    val onlineSource: String?,
)
// end::contactpoint[]

// tag::content[]
data class ContentMetadata (
    val language: String?,
    val uploadDate: String?,
    val contactPoint: ContactPoint, // <1>
    val accessUrl: String?,
    val distributionFormats: List<Format>?, // <2>
    val distributionTransfers: List<Transfer>? // <2>
)
//end::content[]