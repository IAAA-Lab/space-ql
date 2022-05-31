package application.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document


@Document(indexName = "sql_services")
data class Service(
    @Id val id: String,
    val type: String = "Service",
    val title: String,
    // Contiene los ids de los elementos relacionados, por lo que habra que coger la informacion del estado global
    val coupledDatasets: List<MetadataRecord>
)