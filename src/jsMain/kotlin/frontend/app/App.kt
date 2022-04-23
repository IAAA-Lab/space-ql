package frontend.app

import MetaData
import csstype.*
import frontend.app.Sidebar.Sidebar
import frontend.app.header.Header
import frontend.app.results.Results
import frontend.common.Area
import frontend.common.Sizes
import frontend.results.result
import frontend.searchbar.SearchBar
import frontend.getResults
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import mui.material.Typography
import mui.system.Box
import mui.system.sx
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.main

private val scope = MainScope()

val app = FC<Props> {
    var resultList by useState(emptyList<MetaData>())

    useEffect {
        scope.launch {
            resultList = getResults()
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

            Typography {
                sx{
                    textAlign = TextAlign.center
                }
                variant = "h1"
                +"SpaceQL"
            }
            Typography {
                sx{
                    textAlign = TextAlign.center
                }
                variant ="subtitle1"
                +"GraphQL-based metadata browser"
            }

            SearchBar {
                onSubmit = { input ->
                    scope.launch {
                        resultList = getResults(input)
                    }
                }
            }

            Results {
                this.resultList = resultList
            }

        }

    }


}
