package application.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

// tag::service[]
// tag::elasticsearch[]
@Document(indexName = "sql_services")
data class ElsService(
    @Id val id: String,
// end::elasticsearch[]
    val type: String = "Service",
    val title: String,
    var coupledDatasets: List<ElsRelatedElements>
)
// end::service[]