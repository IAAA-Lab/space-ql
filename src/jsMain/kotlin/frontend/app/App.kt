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
    val resultsLimit = 10

    useEffectOnce {
        scope.launch {
            val mdPage = getResults(null, resultsLimit, 0, resultsOrder)
            setMaxPage(mdPage.totalPages)
            resultList = mdPage.metaData
            facetsList = mdPage.facets as MutableList<Facets>
        }
    }

    useEffect(facetsList) {
        // TODO - treat facets on the backend or send the whole content
        // idea: Mandar en el search() unos campos language, resource type y related docs
        // y en base a ellos hacer el filtrado y rehacer la busqueda.
        // deberia ser sencillo si cada campo es un arrayList de Strings que
        // contenga los campos que deben mirarse en el filter
        // ej: language = [Spanish, English, Other] y si esta vacio que no filtre en base
        // a eso
        if(facetsList.isNotEmpty()){
            var resultAux : MutableList<MetadataRecord>  = ArrayList(resultList)
            // Language - Spanish, English, Other/Unknown
            val langs = facetsList.find { el -> el.name == "Language" }

            val spa = langs?.values?.find{ el -> el.field == "Spanish"}?.checked
            val eng = langs?.values?.find{ el -> el.field == "English"}?.checked
            val otherLang = langs?.values?.find{ el -> el.field == "Other/Unknown"}?.checked

            console.log(spa, eng, otherLang)

            var langFilter = ArrayList<String>()
            val spaFilter = arrayOf("Spanish", "Español", "spa" )
            val engFilter = arrayOf("eng", "English")

            if(spa == true){
                langFilter.addAll(spaFilter)
            }
            if(eng == true){
                langFilter.addAll(engFilter)
            }

            console.log(resultAux.size)
            if(otherLang == true){
                resultAux = resultAux.filter { el -> el.details?.language in langFilter || (el.details?.language !in spaFilter && el.details?.language !in engFilter )  } as MutableList<MetadataRecord>
            } else if(spa == true || eng == true){
                resultAux = resultAux.filter { el -> el.details?.language in langFilter } as MutableList<MetadataRecord>
            }
            console.log(resultAux.size)

            resultList = resultAux
        }

        // resource type - Service, Dataset, Other
        // related documents - 0 1 2 3 +3
    }

    fun getResultsProp(term: String, offset: Int, order: String) {
        scope.launch {
            val mdPage = getResults(term, resultsLimit, offset, order)
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
