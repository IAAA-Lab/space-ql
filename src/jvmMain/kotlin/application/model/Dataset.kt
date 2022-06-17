package application.model

import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.annotation.Id

// tag::dataset[]
@Document(indexName = "sql_datasets")
data class Dataset(
    @Id val id: String,
    val type: String = "Dataset",
    val title: String,
    var coupledServices: List<RelatedElements>
)
// end::dataset[]
