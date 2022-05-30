package application.model

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import java.util.*

interface MetadataElasticsearchRepository: ElasticsearchRepository<MetadataRecord, String>{
    fun findByTitleOrDescription(title: String, description: String, pageable: Pageable): Page<MetadataRecord>
}

interface DatasetElasticsearchRepository: ElasticsearchRepository<Dataset, String>

interface ServiceElasticsearchRepository: ElasticsearchRepository<Service, String>