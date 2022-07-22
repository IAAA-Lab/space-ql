package frontend.app.pageview

import application.model.Facets
import application.model.MetadataRecord
import csstype.*
import frontend.app.components.sidebar.Sidebar
import frontend.app.components.title.title
import frontend.app.components.results.Results
import frontend.app.components.searchbar.SearchBar
import frontend.common.Area
import frontend.common.Sizes
import mui.system.Box
import mui.system.sx
import react.FC
import react.Props
import react.router.useLocation
import react.useEffectOnce

external interface HomeProps : Props {
    var facets : List<Facets>
    var setChecked : (String, String, Boolean) -> Unit
    var setSingleCheck : (String, String) -> Unit
    var resultsLimit : Int
    var resultsOrder: String
    var searchTerm : String
    var currentPage : Int
    var maxPage : Int
    var resultList : List<MetadataRecord>
    var setResultList : (List<MetadataRecord>) -> Unit
    var setMaxPage : (Int) -> Unit
    var setSearchTerm : (String) -> Unit
    var setResultsOrder : (String) -> Unit
    var setCurrentPage : (Int) -> Unit
    var getResultsProp : (String, Int, String) -> Unit
}

val homeContent = FC<HomeProps> { props ->
    val location = useLocation()
    val checkedName = location.state as? String

    // The checked facet is stored into a string following the
    useEffectOnce {
        if(checkedName != null){
            props.setSearchTerm("")
            val items = checkedName.split(";")
            when(items[0]){
                "Contact" -> props.setSingleCheck("Contact Points", items[1])
                "Language" -> props.setSingleCheck("Language", items[1])
            }
        }
    }


    Box {
        sx {
            display = Display.grid
            gap = Gap.normal
            gridTemplateColumns = array(
                Sizes.Sidebar.TotalWidth,
                Sizes.ContentNoSidebar.Width
            )
            gridTemplateAreas = GridTemplateAreas(
                arrayOf(Area.Sidebar, Area.Results)
            )
            justifyItems = JustifyItems.stretch
            alignItems = AlignItems.stretch
            gridArea = Area.Content
        }

        Sidebar{
            this.facets = props.facets
            this.setChecked = { facet, subfacet, checked -> props.setChecked(facet, subfacet, checked)}
            this.resultsOrder = props.resultsOrder
            this.onOrderSelect = {
                props.setResultsOrder(it)
                props.getResultsProp(props.searchTerm, 0, it)
            }
        }

        Box {
            id="main-container"
            sx {
                gridArea = Area.Results
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

                this.onPageSelect = { pageNum ->
                    props.setCurrentPage(pageNum)
                    val offset = (pageNum - 1) * props.resultsLimit
                    props.getResultsProp(props.searchTerm, offset, props.resultsOrder)
                }

            }

        }
    }


//    Box {
//        component = ReactHTML.main
//        sx {
//            display = Display.grid
//            gridTemplateColumns = array(
//                Sizes.Sidebar.Width,
//                Auto.auto
//            )
//            gridTemplateAreas = GridTemplateAreas(
//                arrayOf(Area.Sidebar, Area.ResultsContent)
//            )
//
//            gridArea = Area.Content
//        }
//        Sidebar{
//            this.facets = props.facets
//            this.setChecked = { facet, subfacet, checked -> props.setChecked(facet, subfacet, checked)}
//        }
//
//        Box {
//            sx {
//                gridArea = Area.ResultsContent
//            }
//
//            title{}
//
//            SearchBar {
//                onSubmit = { input ->
//                    props.getResultsProp(input, 0, props.resultsOrder)
//                    props.setSearchTerm(input)
//                }
//            }
//
//            Results {
//                this.resultList = props.resultList
//                this.currentPage = props.currentPage
//                this.maxPages = props.maxPage
//                this.resultsOrder = props.resultsOrder
//                this.onOrderSelect = {
//                    props.setResultsOrder(it)
//                    props.getResultsProp(props.searchTerm, 0, it)
//                }
//                this.onPageSelect = { pageNum ->
//                    props.setCurrentPage(pageNum)
//                    val offset = (pageNum - 1) * props.resultsLimit
//                    props.getResultsProp(props.searchTerm, offset, props.resultsOrder)
//                }
//
//            }
//
//        }
//
//
//    }
}