import kotlinx.serialization.Serializable

@Serializable
data class MetaData(val id: String = "",
                    val title: String = "",
                    val content: String = "",
                    val location: String = "")