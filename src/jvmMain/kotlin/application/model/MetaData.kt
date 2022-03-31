package application.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.util.*

@Document(indexName = "test-values")
data class MetaData(
    @Id val id: String,
    //@Field(type = FieldType.Nested, includeInParent = true)
    val data: ContentData,
    val content: String?
)

data class ContentData (
    val language: String?,
    val uploadDate: String?,
    val scope: String?,
    val standard: String?,
    val standardVersion: String?,
    val fileName: String?,
    val fileDescription: String?,
    val contactPointIndividual: String?,
    val contactPointPhone: String?,
    val contactPointName: String?,
    val contactPointMail: String?,
    val contactPointOnlineSource: String?,
    val accessUrl: String?
)
