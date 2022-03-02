package application.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import java.util.*

@Document(indexName = "metadata")
data class MetaData(
    @Id val id: UUID,
    val title: String,
    val content: String,
    val location: String
    //TODO: Add more fields
)
