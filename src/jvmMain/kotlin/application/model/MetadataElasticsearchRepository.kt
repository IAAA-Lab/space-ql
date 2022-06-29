package application.model

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import java.util.*

// tag::repository[]
// <1>
interface MetadataElasticsearchRepository: ElasticsearchRepository<ElsMetadataRecord, String> {
    fun findByTitleOrDescription(title: String,         // <4>
                                 description: String,
                                 pageable: Pageable): Page<ElsMetadataRecord>
}
interface DatasetElasticsearchRepository: ElasticsearchRepository<ElsDataset, String> // <2>
interface ServiceElasticsearchRepository: ElasticsearchRepository<ElsService, String> // <3>
// end::repository[]