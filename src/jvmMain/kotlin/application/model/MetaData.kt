package application.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "test-values")
data class MetaData(
    @Id val id: String,
    //@Field(type = FieldType.Nested, includeInParent = true)
    val data: ContentData,
    val content: String?
)

data class MetaDataPage(
    val totalPages: Int,
    val metaData: List<MetaData>
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
