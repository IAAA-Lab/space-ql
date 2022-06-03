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

    fun getRecord(id: String) : MetadataRecord {
        // Obtiene el record
        val value = metadataRepository.findById(id)

        // Obtiene el record del Optional
        val retValue = value.get()

        // Es servicio, obtener los coupled datasets
        if(retValue.type == "service"){
            val topic = serviceRepository.findById(id).get()

            // Para cada coupled dataset no tenemos sus propios coupled services, asi que toca cogerlos
            // como sabemos que es un dataset simplemente hay que coger el dataset con su id
            // del contenedor de datasets
            for(i in 0 until topic.coupledDatasets.size){
                val ds = topic.coupledDatasets[i]
                ds.primaryTopic = datasetRepository.findById(ds.ID).get()
            }

            retValue.primaryTopic = topic

        } else if(retValue.type == "dataset"){
            val topic = datasetRepository.findById(id).get()

            for(i in 0 until topic.coupledServices.size){
                val ds = topic.coupledServices[i]
                ds.primaryTopic = serviceRepository.findById(ds.ID).get()
            }

            retValue.primaryTopic = topic
        }
        return retValue
    }

    /*
     * Esta función implementa el sistema de búsqueda paginada, devolviendo los
     * resultados de la página indicada en el orden indicado.
     *
     */
    fun search(text: String?, limit: Int, offset: Int, order: String,
               language: List<String>?, resType: List<String>?, related: List<String>?): MetadataPage {
        var foundList = findData(text, order)

        foundList = filterFacets(foundList, language, resType, related)

        val foundFacets = getFacets(foundList)

        val resultSize = foundList.size
        var totalPages = resultSize / limit
        if (resultSize % limit != 0) totalPages += 1

        var to = offset + limit

        if (to > foundList.size) {
            to = foundList.size
        }

        val foundPage = foundList.subList(offset, to)


        return MetadataPage(foundFacets, totalPages, foundPage)
    }

    private fun filterFacets(
        foundList: List<MetadataRecord>,
        language: List<String>?,
        resType: List<String>?,
        related: List<String>?
    ): List<MetadataRecord> {
        var retList : MutableList<MetadataRecord> = ArrayList(foundList)

        if(language != null && language.isNotEmpty()){
            retList = filterLanguage(retList, language)
        }
        if(resType != null && resType.isNotEmpty()){
            retList = filterType(retList, resType)
        }
        if(related != null && related.isNotEmpty()){
            retList = filterRelated(retList, related)
        }

        return retList
    }


    private fun filterRelated(
        recordList: MutableList<MetadataRecord>,
        related: List<String>
    ): MutableList<MetadataRecord> {
        var retList : MutableList<MetadataRecord> = ArrayList(recordList)

        val zero = "0" in related
        val one = "1" in related
        val two = "2" in related
        val three = "3" in related
        val plusThree = "+3" in related

        val relatedFilter = ArrayList<Int>()

        if(zero){
            relatedFilter.add(0)
        }
        if(one){
            relatedFilter.add(1)
        }
        if(two){
            relatedFilter.add(2)
        }
        if(three){
            relatedFilter.add(3)
        }


        retList = retList.filter {el ->
            checkTopic(el, relatedFilter, plusThree)
        } as MutableList<MetadataRecord>


        return retList

    }

    private fun checkTopic(record : MetadataRecord, filter : ArrayList<Int>, plusThree : Boolean ) : Boolean {
        val topic = record.primaryTopic
        return if(topic is application.model.Service) {
            if(plusThree){
                (topic.coupledDatasets.size in filter) || (topic.coupledDatasets.size > 3)
            }else{
                topic.coupledDatasets.size in filter
            }
        } else if(topic is Dataset) {
            if(plusThree){
                (topic.coupledServices.size in filter) || (topic.coupledServices.size > 3)
            }else{
                topic.coupledServices.size in filter
            }
        } else {
            false
        }
    }

    private fun filterType(
        recordList: MutableList<MetadataRecord>,
        resType: List<String>
    ): MutableList<MetadataRecord> {
        var retList : MutableList<MetadataRecord> = ArrayList(recordList)

        val serv = "Service" in resType
        val dat = "Dataset" in resType
        val otherType = "Other" in resType

        val typeFilter = ArrayList<String>()

        if(serv){
            typeFilter.add("service")
        }
        if(dat){
            typeFilter.add("dataset")
        }

        if(otherType){
            retList = retList.filter { el -> el.type in typeFilter || (el.type != "service" && el.type != "dataset" )  } as MutableList<MetadataRecord>
        } else if(serv || dat){
            retList = retList.filter { el -> el.type in typeFilter } as MutableList<MetadataRecord>
        }

        return retList

    }
    
    private fun filterLanguage(
        recordList: MutableList<MetadataRecord>,
        language: List<String>
    ): MutableList<MetadataRecord> {
        var retList : MutableList<MetadataRecord> = ArrayList(recordList)

        val spa = "Spanish" in language
        val eng = "English" in language
        val otherLang = "Other/Unknown" in language

        val langFilter = ArrayList<String>()
        val spaFilter = arrayOf("Spanish", "Español", "spa" )
        val engFilter = arrayOf("eng", "English")

        if(spa){
            langFilter.addAll(spaFilter)
        }
        if(eng){
            langFilter.addAll(engFilter)
        }

        if(otherLang){
            retList = retList.filter { el -> el.details.language in langFilter || (el.details.language !in spaFilter && el.details.language !in engFilter )  } as MutableList<MetadataRecord>
        } else if(spa || eng){
            retList = retList.filter { el -> el.details.language in langFilter } as MutableList<MetadataRecord>
        }

        return retList

    }

    fun findData(text: String?, order: String) : List<MetadataRecord> {
        // Elasticsearch doesn't apply sorting to text fields, only keyword fields can be used for sorting
        // So it is going to be done in the server

        val found: List<MetadataRecord> = if(text != null){
            metadataRepository.findByTitleOrDescription(text, text, PageRequest.of(0,527)).toList()
        } else {
            metadataRepository.findAll().toList()
        }

        // Get all the elements found and calculate the facets

        val allServices = serviceRepository.findAll()
        val allDatasets = datasetRepository.findAll()


        // Assign the related resources AKA primary topic
        for(i in 0 until found.size){
            val record = found[i]
            if(record.type == "service"){
                val service = allServices.find { service -> service.id == record.ID }
                record.primaryTopic = service
            } else if(record.type == "dataset") {
                val dataset = allDatasets.find { dataset -> dataset.id == record.ID }
                record.primaryTopic = dataset
            }
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



    // It calculates and returns the facets of the current list
    private fun getFacets(foundList: List<MetadataRecord>): List<Facets> {

        val ret : MutableList<Facets> = mutableListOf()

        ret.add(Facets("Language", mutableListOf(
            SubFacets("Spanish", 0),
            SubFacets("English", 0),
            SubFacets("Other/Unknown", 0),
        )))
        ret.add(Facets("Resource type", mutableListOf(
            SubFacets("Service", 0),
            SubFacets("Dataset", 0),
            SubFacets("Other", 0),
        )))
        ret.add(Facets("Related Resources", mutableListOf(
            SubFacets("0", 0),
            SubFacets("1", 0),
            SubFacets("2", 0),
            SubFacets("3", 0),
            SubFacets("+3", 0),
        )))
        foundList.forEach {
            // Lang
            val lang = it.details.language
            if (lang != null) {
                if( lang == "spa" || lang == "Español" || lang.contains("Spanish")) {
                    addDoc(ret, "Language", "Spanish")
                } else if(lang == "eng" || lang == "English"){
                    addDoc(ret, "Language", "English")
                } else {
                    addDoc(ret, "Language", "Other/Unknown")
                }
            }


            var related = 0

            // Restype
            when (it.type) {
                "service" -> {
                    addDoc(ret, "Resource type", "Service")
                    val service = it.primaryTopic as application.model.Service
                    related = service.coupledDatasets.size
                }
                "dataset" -> {
                    addDoc(ret, "Resource type", "Dataset")
                    val dataset = it.primaryTopic as Dataset
                    related = dataset.coupledServices.size
                }
                else -> {
                    addDoc(ret, "Resource type", "Other")
                }
            }

            // Related Resources
            when(related) {
                0 -> addDoc(ret, "Related Resources", "0")
                1 -> addDoc(ret, "Related Resources", "1")
                2 -> addDoc(ret, "Related Resources", "2")
                3 -> addDoc(ret, "Related Resources", "3")
                else -> addDoc(ret, "Related Resources", "+3")
            }
        }

        return ret
    }

    // It adds 1 to the number of documents of the subfacet of a facet
    private fun addDoc(facets : List<Facets>, facet : String, subFacet : String) {
        val numDocs = facets.find { it.name == facet }?.values?.find { it.field == subFacet }?.docNum
        if (numDocs != null) {
            facets.find { it.name == facet }?.values?.find { it.field == subFacet }?.docNum = numDocs + 1
        }
    }
}