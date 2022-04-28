import kotlinx.serialization.Serializable

@Serializable
data class MetaData(val id: String = "",
                    val data: ContentData,
                    val content: String = "")

@Serializable
data class MetaDataPage(
    val totalPages: Int,
    val metaData: List<MetaData>
)

@Serializable
data class ContentData (
    val language: String = "",
    val uploadDate: String = "",
    val scope: String = "",
    val standard: String = "",
    val standardVersion: String = "",
    val fileName: String = "",
    val fileDescription: String = "",
    val contactPointIndividual: String = "",
    val contactPointPhone: String = "",
    val contactPointName: String = "",
    val contactPointMail: String = "",
    val contactPointOnlineSource: String = "",
    val accessUrl: String = ""
)