package application.model

import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.annotation.Id

// tag::dataset[]
// tag::elasticsearch[]

@Document(indexName = "sql_datasets")
data class ElsDataset(
    @Id val id: String,
// end::elasticsearch[]
    val type: String = "Dataset",
    val title: String,
    var coupledServices: List<ElsRelatedElements>
)
// end::dataset[]
