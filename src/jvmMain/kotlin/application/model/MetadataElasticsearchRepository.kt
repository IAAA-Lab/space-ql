package application.model

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import java.util.*

interface MetadataElasticsearchRepository: ElasticsearchRepository<MetadataRecord, UUID>{
   /* fun findByContent(content: String, pageable: Pageable): Page<MetadataRecord>
    fun findByContentContaining(content: String, pageable: Pageable): Page<MetadataRecord>*/
//    fun findByDataFileNameOrDataFileDescription(fileName: String, fileDescription: String, pageable: Pageable): Page<MetadataRecord>

    fun findByTitleOrDescription(title: String, description: String, pageable: Pageable): Page<MetadataRecord>
/*
    fun findByDataFileNameOrDataFileDescriptionOrderByDataFileName(fileName: String, fileDescription: String, pageable: Pageable): Page<MetadataRecord>

    fun findByDataFileNameOrDataFileDescriptionOrderByDataUploadDate(fileName: String, fileDescription: String, pageable: Pageable): Page<MetadataRecord>
*/


}