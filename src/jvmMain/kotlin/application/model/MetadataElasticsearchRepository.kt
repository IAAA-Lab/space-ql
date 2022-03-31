package application.model

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import java.util.*

interface MetadataElasticsearchRepository: ElasticsearchRepository<MetaData, UUID>{
    fun findByContent(content: String, pageable: Pageable): Page<MetaData>
    fun findByContentContaining(content: String, pageable: Pageable): Page<MetaData>
    fun findByDataFileName(file_name: String, pageable: Pageable): Page<MetaData>
    fun findByDataFileNameContaining(file_name: String, pageable: Pageable): Page<MetaData>

}