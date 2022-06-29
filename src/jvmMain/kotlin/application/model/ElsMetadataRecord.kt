package application.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

// tag::mdrecord[]
@Document(indexName = "sql_records") // <7>
data class ElsMetadataRecord(
    @Id val ID: String, // <1>
    val title: String,  // <2>
    val description: String, // <3>
    var primaryTopic: Any? = null, // <4>
    val type: String,   // <5>
    val details: ElsContentMetadata, // <6>
)
// end::mdrecord[]
// tag::formtrans[]
data class ElsFormat (
    val name: String,
    val version: String
    )
data class ElsTransfer (
    val URL: String
    )
// end::formtrans[]

// tag::contactpoint[]
data class ElsContactPoint (
    val individual: String?,
    val phone: String?,
    val name: String?,
    val mail: String?,
    val onlineSource: String?,
)
// end::contactpoint[]

// tag::content[]
data class ElsContentMetadata (
    val language: String?,
    val uploadDate: String?,
    val contactPoint: ElsContactPoint, // <1>
    val accessUrl: String?,
    val distributionFormats: List<ElsFormat>?, // <2>
    val distributionTransfers: List<ElsTransfer>? // <2>
)
//end::content[]