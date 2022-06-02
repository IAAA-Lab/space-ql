package frontend.app

import Facets
import MetadataRecord
import csstype.*
import frontend.app.header.Header
import frontend.common.Area
import frontend.common.Sizes
import frontend.getResults
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

val app = FC<Props> {
    var resultList by useState(emptyList<MetadataRecord>())
    val (maxPage, setMaxPage) = useState(0)
    val (currentPage, setCurrentPage) = useState(1)
    val (searchTerm, setSearchTerm) = useState("")
    val (resultsOrder, setResultsOrder) = useState("Relevance")
    var facetsList by useState(mutableListOf<Facets>())
    var langFacet by useState(mutableListOf<String>())
    var typeFacet by useState(mutableListOf<String>())
    var relatedFacet by useState(mutableListOf<String>())
    val resultsLimit = 10

    useEffectOnce {
        scope.launch {
            val mdPage = getResults(null, resultsLimit, 0, resultsOrder, langFacet, typeFacet, relatedFacet)
            setMaxPage(mdPage.totalPages)
            resultList = mdPage.metaData
            facetsList = mdPage.facets as MutableList<Facets>
        }
    }

    fun updateWithFacets() {
        // TODO - treat facets on the backend or send the whole content
        if(facetsList.isNotEmpty()){
            // Language - Spanish, English, Other/Unknown
            val langs = facetsList.find { el -> el.name == "Language" }

            val spa = langs?.values?.find{ el -> el.field == "Spanish"}?.checked
            val eng = langs?.values?.find{ el -> el.field == "English"}?.checked
            val otherLang = langs?.values?.find{ el -> el.field == "Other/Unknown"}?.checked

            if(spa == true){
                langFacet.add("Spanish")
            } else{
                do{
                    val obtained = langFacet.remove("Spanish")
                }while(obtained)

            }

            if(eng == true){
                langFacet.add("English")
            } else{
                do{
                    val obtained = langFacet.remove("English")
                }while(obtained)
            }

            if(otherLang == true){
                langFacet.add("Other/Unknown")
            } else{
                do{
                    val obtained = langFacet.remove("Other/Unknown")
                }while(obtained)
            }

            scope.launch {
                val mdPage = getResults(searchTerm, resultsLimit, 0, resultsOrder, langFacet, typeFacet, relatedFacet)
                setMaxPage(mdPage.totalPages)
                resultList = mdPage.metaData
                var facetsAux : MutableList<Facets>  = ArrayList(facetsList)

                facetsAux.forEach {facet ->
                    facet.values?.forEach {subfacet ->
                        subfacet.docNum = mdPage.facets.find{el -> el.name == facet.name}?.values?.find{ el -> el.field == subfacet.field}?.docNum
                    }
                }
                facetsList = facetsAux

                // Coger las facets y cambiarle el valor de docnum por el de estas
            }
        }

        // resource type - Service, Dataset, Other
        // related documents - 0 1 2 3 +3
    }

    fun getResultsProp(term: String, offset: Int, order: String) {
        scope.launch {
            val mdPage = getResults(term, resultsLimit, offset, order, langFacet, typeFacet, relatedFacet)
            setMaxPage(mdPage.totalPages)
            resultList = mdPage.metaData
        }
    }

    Box {
        sx {
            display = Display.grid
            gridTemplateRows = array(
                Sizes.Header.Height,
                Auto.auto,
            )

            gridTemplateColumns = array(
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
                        element = createElement(homeContent,
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
