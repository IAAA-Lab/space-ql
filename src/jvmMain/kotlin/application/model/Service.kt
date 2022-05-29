package application.model

import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "sql_services")
data class Service(
    val title: String,
    val coupledDatasets: List<Dataset>
)
