package frontend.app

import MetaData
import csstype.*
import frontend.app.Sidebar.Sidebar
import frontend.app.header.Header
import frontend.app.results.Results
import frontend.common.Area
import frontend.common.Sizes
import frontend.app.results.result
import frontend.app.searchbar.SearchBar
import frontend.getResults
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import mui.material.Typography
import mui.material.styles.TypographyVariant
import mui.system.Box
import mui.system.sx
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.main

private val scope = MainScope()

val app = FC<Props> {
    var resultList by useState(emptyList<MetaData>())
    val (maxPage, setMaxPage) = useState(0)
    val (currentPage, setCurrentPage) = useState(1)
    val (searchTerm, setSearchTerm) = useState("")
    val resultsLimit = 10

    useEffectOnce {
        scope.launch {
            val mdPage = getResults(null, resultsLimit, 0)
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

        Sidebar()

        Box {
            component = main
            sx {
                gridArea = Area.Content
            }

            // TODO: get togeher as a title component??
            Typography {
                sx{
                    textAlign = TextAlign.center
                }
                variant = TypographyVariant.h1
                +"SpaceQL"
            }
            Typography {
                sx{
                    textAlign = TextAlign.center
                }
                variant = TypographyVariant.subtitle1
                +"GraphQL-based metadata browser"
            }

            SearchBar {
                onSubmit = { input ->
                    scope.launch {
                        val mdPage = getResults(input, resultsLimit, 0)
                        setMaxPage(mdPage.totalPages)
                        resultList = mdPage.metaData
                        setSearchTerm(input)
                    }
                }
            }

            Results {
                this.resultList = resultList
                this.currentPage = currentPage
                this.maxPages = maxPage
                this.onPageSelect = { pageNum ->
                    setCurrentPage(pageNum)
                    val offset = (pageNum - 1) * resultsLimit
                    scope.launch {
                        val mdPage = getResults(searchTerm, resultsLimit, offset )
                        setMaxPage(mdPage.totalPages)
                        resultList = mdPage.metaData
                    }
                }
            }

        }

    }


}
