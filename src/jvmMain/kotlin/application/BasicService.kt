package application

import application.model.MetaData
import application.model.MetaDataPage
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
    fun getMetadata(limit: Int, offset: Int): MetaDataPage {
        val found = metadataRepository.findAll()

        val foundList = found.toList()

        val resultSize = foundList.size
        var totalPages = resultSize / limit
        if(resultSize % limit != 0) totalPages = totalPages +1

        val from = offset
        var to = from + limit

        if(to > foundList.size){
            to = foundList.size
        }

        val foundPage = foundList.subList(from, to)

        return MetaDataPage(totalPages, foundPage)
    }

    // TODO: howtographql.com/react-apollo/9-pagination/
    fun search(text: String, limit: Int, offset: Int): MetaDataPage {
        val found = metadataRepository.findByDataFileNameOrDataFileDescription(text, text, PageRequest.of(0,527))
        val foundList = found.toList()

        val resultSize = foundList.size
        var totalPages = resultSize / limit
        if(resultSize % limit != 0) totalPages = totalPages +1

        val from = offset
        var to = from + limit

        if(to > foundList.size){
            to = foundList.size
        }

        val foundPage = foundList.subList(from, to)

        return MetaDataPage(totalPages, foundPage)
    }
}