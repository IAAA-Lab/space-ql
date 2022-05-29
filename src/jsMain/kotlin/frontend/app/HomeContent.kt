package frontend.app

import MetaData
import csstype.Auto
import csstype.Display
import csstype.GridTemplateAreas
import csstype.array
import frontend.app.Sidebar.Sidebar
import frontend.app.Title.title
import frontend.app.results.Results
import frontend.app.searchbar.SearchBar
import frontend.common.Area
import frontend.common.Sizes
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
    var getResultsProp : (String, Int, String) -> Unit
}

val homeContent = FC<HomeProps> { props ->
    Box {
        component = ReactHTML.main
        sx {
            display = Display.grid
            gridTemplateColumns = array(
                Sizes.Sidebar.Width,
                Auto.auto
            )
            gridTemplateAreas = GridTemplateAreas(
                arrayOf(Area.Sidebar, Area.ResultsContent)
            )

            gridArea = Area.Content
        }
        Sidebar()

        Box {
            sx {
                gridArea = Area.ResultsContent
            }

            title{}

            SearchBar {
                onSubmit = { input ->
                    props.getResultsProp(input, 0, props.resultsOrder)
                    props.setSearchTerm(input)
                }
            }

            Results {
                this.resultList = props.resultList
                this.currentPage = props.currentPage
                this.maxPages = props.maxPage
                this.resultsOrder = props.resultsOrder
                this.onOrderSelect = {
                    props.setResultsOrder(it)
                    props.getResultsProp(props.searchTerm, 0, it)
                }
                this.onPageSelect = { pageNum ->
                    props.setCurrentPage(pageNum)
                    val offset = (pageNum - 1) * props.resultsLimit
                    props.getResultsProp(props.searchTerm, offset, props.resultsOrder)
                }

            }

        }


    }
}