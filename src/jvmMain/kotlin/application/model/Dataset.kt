package application.model

import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.annotation.Id

@Document(indexName = "sql_datasets")
data class Dataset(
    @Id val id: String,
    val title: String,
    // Contiene los ids de los elementos relacionados, por lo que habra que coger la informacion del estado global
    val coupledServices: List<MetadataRecord>
)
