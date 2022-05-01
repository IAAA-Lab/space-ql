package application

import application.model.MetaData
import application.model.MetaDataPage
import application.model.MetadataElasticsearchRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class BasicService(
    private val metadataRepository : MetadataElasticsearchRepository
) {

    fun search(text: String?, limit: Int, offset: Int, order: String): MetaDataPage {
        val foundList = findData(text, order)

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

    fun findData(text: String?, order: String) : List<MetaData> {
        // Elasticsearch doesn't apply sorting to text fields, only keyword fields can be used for sorting
        // So it is going to be done in the server
        val found: List<MetaData>

        found = if(text != null){
            metadataRepository.findByDataFileNameOrDataFileDescription(text, text, PageRequest.of(0,527)).toList()

        } else {
            metadataRepository.findAll().toList()
        }

        return when(order) {
            "Date" -> {
                found.sortedBy {
                    it.data.uploadDate
                }
            }
            "Name" -> {
                found.sortedBy {
                    it.data.fileName
                }
            }
            else -> {
                found
            }
        }


    }
}