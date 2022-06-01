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
    var facetsList by useState(emptyList<Facets>())
    val resultsLimit = 10
    // TODO - Facets

    useEffectOnce {
        scope.launch {
            val mdPage = getResults(null, resultsLimit, 0, resultsOrder)
            setMaxPage(mdPage.totalPages)
            resultList = mdPage.metaData
            facetsList = mdPage.facets
        }
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
