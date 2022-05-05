package frontend.app

import MetaData
import csstype.*
import frontend.app.Sidebar.Sidebar
import frontend.app.Title.title
import frontend.app.header.Header
import frontend.app.results.Results
import frontend.common.Area
import frontend.common.Sizes
import frontend.app.results.result
import frontend.app.searchbar.SearchBar
import frontend.getResults
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.js.jso
import mui.material.Typography
import mui.material.styles.TypographyVariant
import mui.system.Box
import mui.system.sx
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.main
import react.router.Route
import react.router.Routes
import react.router.dom.BrowserRouter
import react.router.useLocation

public val scope = MainScope()

val app = FC<Props> {
    var resultList by useState(emptyList<MetaData>())
    val (maxPage, setMaxPage) = useState(0)
    val (currentPage, setCurrentPage) = useState(1)
    val (searchTerm, setSearchTerm) = useState("")
    val (resultsOrder, setResultsOrder) = useState("Relevance")
    val resultsLimit = 10

    useEffectOnce {
        scope.launch {
            val mdPage = getResults(null, resultsLimit, 0, resultsOrder)
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
                Sizes.Sidebar.Width, Auto.auto,
            )
            gridTemplateAreas = GridTemplateAreas(
                arrayOf(Area.Header, Area.Header),
                arrayOf(Area.Sidebar, Area.Content),
            )
        }

        Header()

        BrowserRouter {
            Routes {
                Route {
                    path = "/"
                    Route {
                        index = true
                        element = createElement(homeContent,
                        props = jso{
                            this.scope = scope
                            this.resultsLimit = resultsLimit
                            this.resultsOrder = resultsOrder
                            this.searchTerm = searchTerm
                            this.currentPage = currentPage
                            this.maxPage = maxPage
                            this.resultList = resultList
                            this.setResultList = {
                                resultList = it
                            }
                            this.setMaxPage = {
                                setMaxPage(it)
                            }
                            this.setSearchTerm = {
                                setSearchTerm(it)
                            }
                            this.setResultsOrder = {
                                setResultsOrder(it)
                            }
                            this.setCurrentPage = {
                                setCurrentPage(it)
                            }
                        })
                    }

                }
            }
        }

    }


}
