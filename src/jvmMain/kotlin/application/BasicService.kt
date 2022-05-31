package application

import application.model.*
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class BasicService(
    private val metadataRepository : MetadataElasticsearchRepository,
    private val serviceRepository : ServiceElasticsearchRepository,
    private val datasetRepository : DatasetElasticsearchRepository
) {

    /*
     * Esta función implementa el sistema de búsqueda paginada, devolviendo los
     * resultados de la página indicada en el orden indicado.
     *
     */
    fun search(text: String?, limit: Int, offset: Int, order: String): MetadataPage {
        val foundList = findData(text, order)

        val resultSize = foundList.size
        var totalPages = resultSize / limit
        if(resultSize % limit != 0) totalPages += 1

        val from = offset
        var to = from + limit

        if(to > foundList.size){
            to = foundList.size
        }

        val foundPage = foundList.subList(from, to)

        for(i in 0 until foundPage.size){
            var record = foundPage[i]
            if(record.type == "service"){
                val service = serviceRepository.findById(record.ID)
                record.primaryTopic = service
            } else if(record.type == "dataset") {
                val dataset = datasetRepository.findById(record.ID)
                record.primaryTopic = dataset
            }
        }

        return MetadataPage(totalPages, foundPage)
    }

    fun findData(text: String?, order: String) : List<MetadataRecord> {
        // Elasticsearch doesn't apply sorting to text fields, only keyword fields can be used for sorting
        // So it is going to be done in the server

        val found: List<MetadataRecord> = if(text != null){
            metadataRepository.findByTitleOrDescription(text, text, PageRequest.of(0,527)).toList()
        } else {
            metadataRepository.findAll().toList()
        }

        return when(order) {
            "Date" -> {
                found.sortedBy {
                    it.details.uploadDate
                }
            }
            "Name" -> {
                found.sortedBy {
                    it.title
                }
            }
            else -> {
                found
            }
        }


    }
}