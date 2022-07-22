package application.model


import kotlinx.serialization.*
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

// tag::mdrecord[]
@Serializable
data class MetadataRecord(
    var ID: String? = null,     // <1>
    var title: String? = null,     // <2>
    var description: String? = null,     // <3>
    var primaryTopic: Resource? = null,     // <4>
    var type: String? = null,     // <5>
    var details: ContentMetadata? = null,     // <6>
)
// end::mdrecord[]

// tag::polymorphic[]
@Polymorphic
@Serializable
abstract class Resource{
    abstract val id: String?
    abstract val title: String?
}
//end::polymorphic[]

// tag::mdpage[]
@Serializable
data class MetadataPage(
    var facets: List<Facets>? = null,   // <1>
    var totalPages: Int? = null,    // <2>
    var metaData: List<MetadataRecord>? = null    // <3>
)
// end::mdpage[]

// tag::facets[]
@Serializable
data class Facets(
    var name: String? = null,     // <1>
    var values: List<SubFacets>? = null      // <2>
)
@Serializable
data class SubFacets(
    var field : String? = null,      // <3>
    var docNum : Int? = null,     // <4>
    var checked : Boolean = false     // <5>
)

// end::facets[]

// tag::fmttrs[]
@Serializable
data class Format (
    var name: String? = null,
    var version: String? = null
    )

@Serializable
data class Transfer (
    var URL: String? = null
    )
//end::fmttrs[]
//tag::org[]
@Serializable
data class Organization(
    var name: String? = null, //<1>
    var subOrganization : String? = null,//<2>
    var wholeName : String? = null//<3>
)
//end::org[]
//tag::contact[]
@Serializable
data class ContactPoint (
    var individual: String? = null, //<1>
    var phone: String? = null,//<2>
    var organization: Organization? = null, //<3>
    var mail: String? = null,//<4>
    var onlineSource: String? = null,//<5>
)
//end::contact[]
//tag::content[]
@Serializable
data class ContentMetadata (
    var language: String? = null,
    var uploadDate: String? = null,
    var contactPoint: ContactPoint? = null,
    var accessUrl: String? = null,
    var distributionFormats: List<Format>? = null,
    var distributionTransfers: List<Transfer>? = null
)
//end::content[]