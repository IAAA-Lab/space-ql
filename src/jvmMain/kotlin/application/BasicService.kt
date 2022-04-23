package application

import application.model.MetaData
import application.model.MetadataElasticsearchRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class BasicService(
    private val metadataRepository : MetadataElasticsearchRepository
) {

    /**
     * From github.com/codeniko/JsonPathKt
     */
    fun getMetadata(): List<MetaData> {
        // THIS IS A TEST IMPLEMENTATION
        val found = metadataRepository.findAll()
        return found.toList()
    }

    // TODO: howtographql.com/react-apollo/9-pagination/
    fun search(text: String): List<MetaData> {
        val found = metadataRepository.findByDataFileNameOrDataFileDescription(text, text, PageRequest.of(0,527))
        return found.toList()
    }
}