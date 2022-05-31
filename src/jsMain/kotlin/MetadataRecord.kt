
import kotlinx.serialization.*
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.jsonObject


@Serializable
data class MetadataRecord(
    val ID: String = "",
    val title: String = "",
    val description: String = "",
    val primaryTopic: Resource? = null,
    val type: String = "",
    val details: ContentMetadata? = null
)

@Polymorphic
//@Serializable(with = ResourceSerializer::class)
sealed interface Resource

@Serializable
@SerialName("Service")
data class Service(
    val title: String? = null,
    val coupledDatasets: List<MetadataRecord>? = null,
    val __typename: String = "Service"
) : Resource

@Serializable
@SerialName("Dataset")
data class Dataset(
    val title: String? = null,
    val coupledServices: List<MetadataRecord>? = null,
    val __typename: String = "Dataset"
) : Resource


//object ResourceSerializer : JsonTransformingSerializer<Resource>(PolymorphicSerializer(Resource::class)) {
//    override fun transformDeserialize(element: JsonElement): JsonElement {
//        println(element)
//        val type = element.jsonObject["type"]!!
//        val data = element.jsonObject["data"] ?: return element
//        return JsonObject(data.jsonObject.toMutableMap().also { it["type"] = type })
//    }
//}

@Serializable
data class MetaDataPage(
    val totalPages: Int,
    val metaData: List<MetadataRecord>
)

@Serializable
data class Format (
    val name: String = "",
    val version: String = ""
)

@Serializable
data class Transfer (
    val URL: String = ""
)

@Serializable
data class ContactPoint (
    val individual: String = "",
    val phone: String = "",
    val name: String = "",
    val mail: String = "",
    val onlineSource: String = "",
)

@Serializable
data class ContentMetadata (
    val language: String = "",
    val uploadDate: String = "",
    val contactPoint: ContactPoint? = null,
    val accessUrl: String = "",
    val distributionFormats: List<Format> = emptyList(),
    val distributionTransfers: List<Transfer> = emptyList()
)