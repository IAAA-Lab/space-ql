package application.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

// tag::service[]
@Document(indexName = "sql_services")
data class Service(
    @Id val id: String,
    val type: String = "Service",
    val title: String,
    val coupledDatasets: List<RelatedElements>
)
// end::service[]