package application

import application.model.*
import org.elasticsearch.search.suggest.SuggestBuilder
import org.elasticsearch.search.suggest.SuggestBuilders
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.stereotype.Service

// tag::elasticimport[]
@Service
class BasicService(
    private val metadataRepository : MetadataElasticsearchRepository,
    private val serviceRepository : ServiceElasticsearchRepository,
    private val datasetRepository : DatasetElasticsearchRepository,
    private val elasticsearchOperations: ElasticsearchOperations
) {
// end::elasticimport[]

    fun getTitles() : List<String> {
        val elements = metadataRepository.findAll()
        return elements.map { it.title }
    }

    // tag::getrecord[]
    fun getRecord(id: String) : MetadataRecord {
        // Obtiene el record
        val elsValue = metadataRepository.findById(id).get() // <1>

        // Es servicio, obtener los coupled datasets
        if(elsValue.type == "service"){
            val topic = serviceRepository.findById(id).get() // <2>

            // Para cada coupled dataset no tenemos sus propios coupled services, asi que toca cogerlos.
            // Como sabemos que es un dataset simplemente hay que coger el dataset gracias a su id
            // del índice de datasets
            for(i in 0 until topic.coupledDatasets.size){
                val ds = topic.coupledDatasets[i]
                ds.relatedRecord.primaryTopic = datasetRepository.findById(ds.relatedRecord.ID).get() // <3>
            }

            elsValue.primaryTopic = topic
        } else if(elsValue.type == "dataset"){
            val topic = datasetRepository.findById(id).get() // <2>

            for(i in 0 until topic.coupledServices.size){
                val ds = topic.coupledServices[i]
                ds.relatedRecord.primaryTopic = serviceRepository.findById(ds.relatedRecord.ID).get() // <3>
            }

            elsValue.primaryTopic = topic
        }

        return getMDRecord(elsValue)
    }
    // end::getrecord[]

    /*
     * Esta función implementa el sistema de búsqueda paginada, devolviendo los
     * resultados de la página indicada en el orden indicado.
     *
     */
    // tag::search[]
    fun search(text: String?, limit: Int, offset: Int, order: String,
               language: List<String>?, resType: List<String>?,
               related: List<String>?, contactPoints: List<String>?,
               formats: List<String>?): MetadataPage {

        var foundList = findData(text, order) // <1>

        foundList = filterFacets(foundList, language, resType, related, contactPoints, formats)

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
    // end::search[]

    private fun filterFacets(
        foundList: List<MetadataRecord>,
        language: List<String>?,
        resType: List<String>?,
        related: List<String>?,
        contactPoints: List<String>?,
        formats: List<String>?
    ): List<MetadataRecord> {
        var retList : MutableList<MetadataRecord> = ArrayList(foundList)

        if(language != null && language.isNotEmpty()){
            retList = filterLanguage(retList, language)
        }
        if(formats != null && formats.isNotEmpty()){
            retList = filterFormats(retList, formats)
        }
        if(resType != null && resType.isNotEmpty()){
            retList = filterType(retList, resType)
        }
        if(related != null && related.isNotEmpty()){
            retList = filterRelated(retList, related)
        }
        if(contactPoints != null && contactPoints.isNotEmpty()){
            retList = filterContactPoints(retList, contactPoints)
        }

        return retList
    }

    private fun filterContactPoints(
        recordList: MutableList<MetadataRecord>,
        contactPoints: List<String>
    ): MutableList<MetadataRecord> {
        var retList : MutableList<MetadataRecord> = ArrayList(recordList)

        retList = retList.filter {el ->
            el.details?.contactPoint?.name in contactPoints
        } as MutableList<MetadataRecord>

        return retList

    }

    private fun filterFormats(
        recordList: MutableList<MetadataRecord>,
        formats: List<String>
    ): MutableList<MetadataRecord> {
        var retList : MutableList<MetadataRecord> = ArrayList(recordList)

        retList = retList.filter {el ->
            el.details?.distributionFormats?.any { format -> format.name in formats  }?: false
        } as MutableList<MetadataRecord>

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
            val trueSize = topic.coupledDatasets?.filter { it.related!! }?.size
            if(plusThree){
                (trueSize in filter) || (trueSize!! > 3)
            }else{
                trueSize in filter
            }
        } else if(topic is Dataset) {
            val trueSize = topic.coupledServices?.filter { it.related!! }?.size
            if(plusThree){
                (trueSize in filter) || (trueSize!! > 3)
            }else{
                trueSize in filter
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

        if(spa){
            langFilter.add("Spanish")
        }
        if(eng){
            langFilter.add("English")
        }

        if(otherLang){
            retList = retList.filter { el -> el.details?.language in langFilter || (el.details?.language != "Spanish" && el.details?.language != "English" )  } as MutableList<MetadataRecord>
        } else if(spa || eng){
            retList = retList.filter { el -> el.details?.language in langFilter } as MutableList<MetadataRecord>
        }

        return retList

    }

    // tag::finddata[]
    fun findData(text: String?, order: String) : List<MetadataRecord> {

        val found: List<ElsMetadataRecord> = if(text != null){
            metadataRepository.findByTitleOrDescription(text, text, PageRequest.of(0,527)).toList() // <1>
        } else {
            metadataRepository.findAll().toList() // <1>
        }

        val allServices = serviceRepository.findAll() // <2>
        val allDatasets = datasetRepository.findAll() // <2>

        // Asignar los valores relacionados (primaryTopic)
        for(element in found){
            if(element.type == "service") {
                val service = allServices.find { service -> service.id == element.ID }
                element.primaryTopic = service
            } else if(element.type == "dataset") {
                val dataset = allDatasets.find { dataset -> dataset.id == element.ID }
                element.primaryTopic = dataset
            }
        }
    // end::finddata[]

        val retList = found.map{ getMDRecord(it) }
        return when(order) {
            "Date" -> {
                retList.sortedBy {
                    it.details?.uploadDate
                }
            }
            "Name" -> {
                retList.sortedBy {
                    it.title
                }
            }
            else -> {
                retList
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

        ret.add(getContactFacets(foundList))
        ret.add(getFormatFacets(foundList))

        foundList.forEach {

            // Contact points
            if(it.details?.contactPoint?.name != null){
                addDoc(ret, "Contact Points", it.details?.contactPoint?.name!!)
            }

            // Formats
            it.details?.distributionFormats?.forEach { format ->
                if(format.name != null){
                    addDoc(ret, "Formats", format.name!!)
                }
            }


            // Lang
            val lang = it.details?.language
            if (lang != null) {
                addDoc(ret, "Language", lang)
            }

            var related = 0

            // Restype
            when (it.type) {
                "service" -> {
                    addDoc(ret, "Resource type", "Service")
                    val service = it.primaryTopic as application.model.Service
                    related = service.coupledDatasets?.filter { it.related!! }?.size!!
                }
                "dataset" -> {
                    addDoc(ret, "Resource type", "Dataset")
                    val dataset = it.primaryTopic as Dataset
                    related = dataset.coupledServices?.filter { it.related!! }?.size!!
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

        for(i:Int in 0 until ret.size){
            ret[i] = Facets(ret[i].name, ret[i].values?.sortedByDescending { it.docNum }!!)
        }

        return ret
    }

    private fun getContactFacets(foundList: List<MetadataRecord>): Facets {
        val subFacets : MutableList<SubFacets> = mutableListOf()

        val distinctValues = foundList.distinctBy { it.details?.contactPoint?.name }

        distinctValues.forEach {
            subFacets.add(SubFacets(it.details?.contactPoint?.name, 0))
        }

        return Facets("Contact Points", subFacets)
    }

    private fun getFormatFacets(foundList: List<MetadataRecord>): Facets {
        val subFacets : MutableList<SubFacets> = mutableListOf()

        val allValues = foundList.flatMap { element -> mutableListOf<String>().also { it.addAll(element.details?.distributionFormats?.map {format -> format.name!! }
            ?: emptyList()) } }
        val distinctValues = allValues.distinct()

        distinctValues.forEach {
            subFacets.add(SubFacets(it, 0))
        }

        return Facets("Formats", subFacets)
    }

    // It adds 1 to the number of documents of the subfacet of a facet
    private fun addDoc(facets : List<Facets>, facet : String, subFacet : String) {
        val numDocs = facets.find { it.name == facet }?.values?.find { it.field == subFacet }?.docNum
        if (numDocs != null) {
            facets.find { it.name == facet }?.values?.find { it.field == subFacet }?.docNum = numDocs + 1
        }
    }

    fun removeRelated(recordId: String, relatedId: String): MetadataRecord {
        val value = metadataRepository.findById(recordId)

        val retValue = value.get()

        if(retValue.type == "service"){
            val service = serviceRepository.findById(recordId).get()
            val auxCoupled : MutableList<ElsRelatedElements> = ArrayList(service.coupledDatasets)

            auxCoupled.find { it.relatedRecord.ID == relatedId }?.related = false
            service.coupledDatasets = auxCoupled
            serviceRepository.save(service)

        } else if(retValue.type == "dataset"){
            val dataset = datasetRepository.findById(recordId).get()
            val auxCoupled : MutableList<ElsRelatedElements> = ArrayList(dataset.coupledServices)

            auxCoupled.find { it.relatedRecord.ID == relatedId }?.related = false
            dataset.coupledServices = auxCoupled
            datasetRepository.save(dataset)
        }

        return getRecord(recordId)
    }

    fun suggest(text: String): List<String?> {
        val suggestionBuilder = SuggestBuilders.completionSuggestion("title").prefix(text).size(5)

        val suggestBuilder = SuggestBuilder()
        suggestBuilder.addSuggestion("title",suggestionBuilder)

        val query = NativeSearchQueryBuilder()
            .withSuggestBuilder(suggestBuilder).build()

        val search = elasticsearchOperations.search(query, ElsMetadataRecord::class.java)

        if(query.query != null) println(query.query.toString())

        val ret = mutableListOf<String>()

        search.suggest
            ?.suggestions?.get(0)
            ?.entries?.get(0)
            ?.options?.forEach {
            ret.add(it.text)
        }

        return ret
    }
}