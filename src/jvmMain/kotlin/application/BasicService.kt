package application

import application.model.MetaData
import application.model.MetadataElasticsearchRepository
import com.jayway.jsonpath.JsonPath
import org.json.XML
import org.springframework.core.io.ClassPathResource
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class BasicService(
    private val metadataRepository : MetadataElasticsearchRepository
) {

    // github.com/codeniko/JsonPathKt
    fun getMetadata(): List<MetaData> {
        // THIS IS A TEST IMPLEMENTATION
        val found = metadataRepository.findAll()

        return found.toList()
    }

    fun search(text: String): List<MetaData> {
        val found = metadataRepository.findByDataFileNameOrDataFileDescription(text, text, PageRequest.of(0,527))

        return found.toList()
    }
}