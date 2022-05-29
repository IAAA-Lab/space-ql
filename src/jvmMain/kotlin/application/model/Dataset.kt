package application.model

import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "sql_datasets")
data class Dataset(
    val title: String,
    val coupledServices: List<Service>
)
