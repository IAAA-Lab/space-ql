package frontend.app

import MetaData
import frontend.app.Sidebar.Sidebar
import frontend.app.Title.title
import frontend.app.results.Results
import frontend.app.searchbar.SearchBar
import frontend.common.Area
import frontend.getResults
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import mui.system.Box
import mui.system.sx
import react.FC
import react.Props
import react.dom.html.ReactHTML

external interface HomeProps : Props {
    var scope : CoroutineScope
    var resultsLimit : Int
    var resultsOrder: String
    var searchTerm : String
    var currentPage : Int
    var maxPage : Int
    var resultList : List<MetaData>
    var setResultList : (List<MetaData>) -> Unit
    var setMaxPage : (Int) -> Unit
    var setSearchTerm : (String) -> Unit
    var setResultsOrder : (String) -> Unit
    var setCurrentPage : (Int) -> Unit
}

val homeContent = FC<HomeProps> { props ->


    Sidebar()

    Box {
        component = ReactHTML.main
        sx {
            gridArea = Area.Content
        }

        title{}

        SearchBar {
            onSubmit = { input ->
                props.scope.launch {
                    val mdPage = getResults(input, props.resultsLimit, 0, props.resultsOrder)
                    props.setMaxPage(mdPage.totalPages)
                    props.setResultList(mdPage.metaData)
                    props.setSearchTerm(input)
                }
            }
        }

        Results {
            this.resultList = props.resultList
            this.currentPage = props.currentPage
            this.maxPages = props.maxPage
            this.resultsOrder = props.resultsOrder
            this.onOrderSelect = {
                props.setResultsOrder(it)
                props.scope.launch {
                    val mdPage = getResults(props.searchTerm, props.resultsLimit, 0, it)
                    props.setMaxPage(mdPage.totalPages)
                    props.setResultList(mdPage.metaData)
                }
            }
            this.onPageSelect = { pageNum ->
                props.setCurrentPage(pageNum)
                val offset = (pageNum - 1) * props.resultsLimit
                props.scope.launch {
                    val mdPage = getResults(props.searchTerm, props.resultsLimit, offset, props.resultsOrder )
                    props.setMaxPage(mdPage.totalPages)
                    props.setResultList(mdPage.metaData)
                }
            }

        }

    }
}