package frontend.app

import application.model.Facets
import application.model.MetadataRecord
import csstype.*
import frontend.app.components.languages.LangContext
import frontend.app.components.languages.LangContextObject
import frontend.app.components.header.Header
import frontend.app.pageview.homeContent
import frontend.app.pageview.resultContent
import frontend.common.Area
import frontend.common.Sizes
import frontend.common.getResults
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.js.jso
import mui.system.Box
import mui.system.sx
import react.*
import react.router.Route
import react.router.Routes
import react.router.dom.BrowserRouter

val scope = MainScope()

val handler = CoroutineExceptionHandler { _, exception ->
    println("CoroutineExceptionHandler got $exception")
}

val app = FC<Props> {
    var resultList by useState(emptyList<MetadataRecord>())
    val (maxPage, setMaxPage) = useState(0)
    val (currentPage, setCurrentPage) = useState(1)
    val (searchTerm, setSearchTerm) = useState("")
    val (resultsOrder, setResultsOrder) = useState("Relevance")
    var facetsList by useState(mutableListOf<Facets>())
    val langFacet by useState(mutableListOf<String>())
    val typeFacet by useState(mutableListOf<String>())
    val contactFacet by useState(mutableListOf<String>())
    val formatFacet by useState(mutableListOf<String>())
    val relatedFacet by useState(mutableListOf<String>())
    val (language, setLanguage) = useState("Esp")
    val resultsLimit = 10

    useEffectOnce {
        scope.launch(handler) {
            val mdPage = getResults(null, resultsLimit, 0, resultsOrder, langFacet, typeFacet, relatedFacet, contactFacet, formatFacet)
            setMaxPage(mdPage.totalPages!!)
            resultList = mdPage.metaData!!
            facetsList = mdPage.facets as MutableList<Facets>
        }
    }

    fun updateWithFacets() {
        if(facetsList.isNotEmpty()){
            // Update the facets filter applied
            getLangFacets(facetsList, langFacet)
            getTypeFacets(facetsList, typeFacet)
            getRelatedFacets(facetsList, relatedFacet)
            getContactFacets(facetsList, contactFacet)
            getFormatFacets(facetsList, formatFacet)

            scope.launch {
                val mdPage = getResults(searchTerm, resultsLimit, 0, resultsOrder, langFacet, typeFacet, relatedFacet, contactFacet, formatFacet)
                setMaxPage(mdPage.totalPages!!)
                resultList = mdPage.metaData!!
                var facetsAux : MutableList<Facets>  = ArrayList(facetsList)

                facetsAux.forEach {facet ->
                    facet.values?.forEach {subfacet ->
                        var number = mdPage.facets?.find{ el -> el.name == facet.name}
                            ?.values?.find{ el -> el.field == subfacet.field}?.docNum
                        if(number == null) number = 0
                        subfacet.docNum = number
                    }
                }

                facetsList = facetsAux
            }
        }

        // resource type - Service, Dataset, Other
        // related documents - 0 1 2 3 +3
    }

    fun getResultsProp(term: String, offset: Int, order: String) {
        scope.launch(handler) {
            try{
                val mdPage = getResults(term, resultsLimit, offset, order, langFacet, typeFacet, relatedFacet, contactFacet, formatFacet)
                setMaxPage(mdPage.totalPages!!)
                resultList = mdPage.metaData!!
                var facetsAux : MutableList<Facets>  = ArrayList(facetsList)

                facetsAux.forEach {facet ->
                    facet.values?.forEach {subfacet ->
                        subfacet.docNum =
                            mdPage.facets?.find { el -> el.name == facet.name }?.values?.find { el -> el.field == subfacet.field }?.docNum
                                ?: 0
                    }
                }

                facetsList = facetsAux
            } finally {

            }
        }

    }

    val setLang = { newLang : String ->
        setLanguage(newLang)
    }


    LangContext.Provider {
        value = LangContextObject(language, setLang)

        Box {
            sx {
                display = Display.grid
                gridTemplateRows = array(
                    Sizes.Header.Height,
                    Auto.auto,
                )
                gridTemplateAreas = GridTemplateAreas(
                    arrayOf(Area.Header),
                    arrayOf(Area.Content),
                )
            }

            Header()

            BrowserRouter {
                Routes {
                    Route {
                        path="/"
                        Route {
                            index = true
                            element = createElement(
                                homeContent,
                                props = jso{
                                    this.facets = facetsList
                                    this.setChecked = { facet, subfacet, checked ->

                                        // Esta copia es necesaria porque si no el estado
                                        // no se actualiza y el componente no se vuelve
                                        // a renderizar (Deep copy)
                                        val facetsAux : MutableList<Facets> = ArrayList(facetsList)

                                        facetsAux
                                            .find { el -> el.name == facet }
                                            ?.values
                                            ?.find{ el -> el.field == subfacet}?.checked = checked

                                        facetsList = facetsAux
                                        updateWithFacets()
                                    }
                                    this.setSingleCheck = { checkedFacet, checkedSubFacet ->
                                        val facetsAux : MutableList<Facets> = ArrayList(facetsList)

                                        facetsAux.forEach {facet ->
                                            facet.values?.forEach {
                                                it.checked = it.field == checkedSubFacet
                                            }
                                        }

                                        facetsList = facetsAux
                                        updateWithFacets()
                                    }
                                    this.resultsLimit = resultsLimit
                                    this.resultsOrder = resultsOrder
                                    this.searchTerm = searchTerm
                                    this.currentPage = currentPage
                                    this.maxPage = maxPage
                                    this.resultList = resultList
                                    this.setResultList = {resultList = it}
                                    this.setMaxPage = {setMaxPage(it)}
                                    this.setSearchTerm = {setSearchTerm(it)}
                                    this.setResultsOrder = {setResultsOrder(it)}
                                    this.setCurrentPage = {setCurrentPage(it)}
                                    this.getResultsProp = {term, offset, order ->
                                        getResultsProp(term, offset, order) }
                                })
                        }
                        Route {
                            path = "id"
                            Route {
                                path=":metadata"
                                element = createElement(resultContent)
                            }
                        }
                    }
                }
            }

        }
    }
}

private fun getContactFacets(
    facetsList: MutableList<Facets>,
    contactFacet: MutableList<String>
) {
    val contactPoints = facetsList.find {el -> el.name == "Contact Points"}

    contactPoints?.values?.forEach {
        if (it.checked){
            contactFacet.add(it.field!!)
        } else {
            do{
                val obtained = contactFacet.remove(it.field!!)
            } while(obtained)
        }
    }
}
private fun getFormatFacets(
    facetsList: MutableList<Facets>,
    formatFacet: MutableList<String>
) {
    val formats = facetsList.find {el -> el.name == "Formats"}

    formats?.values?.forEach {
        if (it.checked){
            formatFacet.add(it.field!!)
        } else {
            do{
                val obtained = formatFacet.remove(it.field!!)
            } while(obtained)
        }
    }
}


private fun getLangFacets(
    facetsList: MutableList<Facets>,
    langFacet: MutableList<String>
) {
    val langs = facetsList.find { el -> el.name == "Language" }

    // Check whether the values are checked or not
    val spa = langs?.values?.find { el -> el.field == "Spanish" }?.checked
    val eng = langs?.values?.find { el -> el.field == "English" }?.checked
    val otherLang = langs?.values?.find { el -> el.field == "Other/Unknown" }?.checked

    if (spa == true) {
        langFacet.add("Spanish")
    } else {
        do {
            val obtained = langFacet.remove("Spanish")
        } while (obtained)

    }

    if (eng == true) {
        langFacet.add("English")
    } else {
        do {
            val obtained = langFacet.remove("English")
        } while (obtained)
    }

    if (otherLang == true) {
        langFacet.add("Other/Unknown")
    } else {
        do {
            val obtained = langFacet.remove("Other/Unknown")
        } while (obtained)
    }
}

private fun getTypeFacets(
    facetsList: MutableList<Facets>,
    typeFacet: MutableList<String>
) {
    val types = facetsList.find { el -> el.name == "Resource type" }

    val serv = types?.values?.find { el -> el.field == "Service" }?.checked
    val dat = types?.values?.find { el -> el.field == "Dataset" }?.checked
    val otherType = types?.values?.find { el -> el.field == "Other" }?.checked

    if (serv == true) {
        typeFacet.add("Service")
    } else {
        do {
            val obtained = typeFacet.remove("Service")
        } while (obtained)

    }

    if (dat == true) {
        typeFacet.add("Dataset")
    } else {
        do {
            val obtained = typeFacet.remove("Dataset")
        } while (obtained)
    }

    if (otherType == true) {
        typeFacet.add("Other")
    } else {
        do {
            val obtained = typeFacet.remove("Other")
        } while (obtained)
    }
}

private fun getRelatedFacets(
    facetsList: MutableList<Facets>,
    relatedFacet: MutableList<String>
) {
    val relateds = facetsList.find { el -> el.name == "Related Resources" }

    val zero = relateds?.values?.find { el -> el.field == "0" }?.checked
    val one = relateds?.values?.find { el -> el.field == "1" }?.checked
    val two = relateds?.values?.find { el -> el.field == "2" }?.checked
    val three = relateds?.values?.find { el -> el.field == "3" }?.checked
    val plusThree = relateds?.values?.find { el -> el.field == "+3" }?.checked

    if (zero == true) {
        relatedFacet.add("0")
    } else {
        do {
            val obtained = relatedFacet.remove("0")
        } while (obtained)

    }

    if (one == true) {
        relatedFacet.add("1")
    } else {
        do {
            val obtained = relatedFacet.remove("1")
        } while (obtained)
    }

    if (two == true) {
        relatedFacet.add("2")
    } else {
        do {
            val obtained = relatedFacet.remove("2")
        } while (obtained)
    }

    if (three == true) {
        relatedFacet.add("3")
    } else {
        do {
            val obtained = relatedFacet.remove("3")
        } while (obtained)
    }

    if (plusThree == true) {
        relatedFacet.add("+3")
    } else {
        do {
            val obtained = relatedFacet.remove("+3")
        } while (obtained)
    }
}