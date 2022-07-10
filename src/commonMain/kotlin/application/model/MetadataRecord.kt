package application.model


import kotlinx.serialization.*
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

@Serializable
data class MetadataRecord(
    var ID: String? = null,
    var title: String? = null,
    var description: String? = null,
    var primaryTopic: Resource? = null,
    var type: String? = null,
    var details: ContentMetadata? = null,
)

@Polymorphic
@Serializable
abstract class Resource{
    abstract val id: String?
    abstract val title: String?
}

object ResourceSerializer : JsonContentPolymorphicSerializer<Resource>(Resource::class) {
    override fun selectDeserializer(element: JsonElement) = when {
        "coupledDatasets" in element.jsonObject -> Service.serializer()
        else -> Dataset.serializer()
    }
}

@Serializable
data class MetadataPage(
    var facets: List<Facets>? = null,
    var totalPages: Int? = null,
    var metaData: List<MetadataRecord>? = null
)

@Serializable
data class SubFacets(
    var field : String? = null,
    var docNum : Int? = null,
    var checked : Boolean = false
)

@Serializable
data class Facets(
    var name: String? = null,
    var values: List<SubFacets>? = null
)

@Serializable
data class Format (
    var name: String? = null,
    var version: String? = null
    )

@Serializable
data class Transfer (
    var URL: String? = null
    )

@Serializable
data class OldContactPoint (
    var individual: String? = null,
    var phone: String? = null,
    var name: String? = null,
    var mail: String? = null,
    var onlineSource: String? = null,
)

@Serializable
data class Organization(
    var name: String? = null,
    var subOrganization : String? = null,
    var wholeName : String? = null
)

@Serializable
data class ContactPoint (
    var individual: String? = null,
    var phone: String? = null,
    var organization: Organization? = null,
    var mail: String? = null,
    var onlineSource: String? = null,
)

@Serializable
data class ContentMetadata (
    var language: String? = null,
    var uploadDate: String? = null,
    var contactPoint: ContactPoint? = null,
    var accessUrl: String? = null,
    var distributionFormats: List<Format>? = null,
    var distributionTransfers: List<Transfer>? = null
)