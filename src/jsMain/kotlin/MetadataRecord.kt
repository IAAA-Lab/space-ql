import kotlinext.js.asJsObject
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonTransformingSerializer
import kotlinx.serialization.json.jsonObject

@Serializable
data class MetadataRecord(
    val ID: String = "",
    val title: String,
    val description: String,
    val primaryTopic: Resource,
    val type: String,
    val details: ContentMetadata
)

@Polymorphic
@Serializable
abstract class Resource {
    abstract val id: String
    abstract val title: String
    abstract val __typename: String
}

@Serializable
@SerialName("Service")
data class Service(
    override val id: String,
    override val title: String,
    val coupledDatasets: List<MetadataRecord>,
    override val __typename: String = "Service"
) : Resource()

@Serializable
@SerialName("Dataset")
data class Dataset(
    override val id: String,
    override val title: String,
    val coupledServices: List<MetadataRecord>,
    override val __typename: String = "Dataset"
) : Resource()

@Serializable
data class MetaDataPage(
    val totalPages: Int,
    val metaData: List<MetadataRecord>
)

@Serializable
data class Format (
    val name: String,
    val version: String
)

@Serializable
data class Transfer (
    val URL: String
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
    val contactPoint: ContactPoint,
    val accessUrl: String = "",
    val distributionFormats: List<Format> = emptyList(),
    val distributionTransfers: List<Transfer> = emptyList()
)