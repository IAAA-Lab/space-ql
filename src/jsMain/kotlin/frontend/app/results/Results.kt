package frontend.app.results

import application.model.MetadataRecord
import csstype.*
import frontend.app.Languages.LangContext
import frontend.app.Languages.langMap
import frontend.app.results.OrderSelector.OrderSelector
import frontend.app.results.PageNav.PageNav
import frontend.common.Area
import frontend.common.Sizes
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.FC
import react.Props
import react.useContext

external interface ResultsProps : Props {
    var resultList : List<MetadataRecord>
    var maxPages: Int 
    var currentPage: Int
    var onPageSelect: (Int) -> Unit
    var onOrderSelect: (String) -> Unit
    var resultsOrder: String
}

val Results = FC<ResultsProps> { props ->
    val lang = useContext(LangContext).lang

    Box {
        sx {
            display = Display.grid
            justifyContent = JustifyContent.center

            marginRight = 100.px
            marginLeft = 100.px

            gridTemplateRows = array(
                Sizes.Header.Height,
                Auto.auto,
            )

            gridTemplateAreas = GridTemplateAreas(
                arrayOf(Area.ResultTitle),
                arrayOf(Area.Results)
            )

            marginTop = 10.px
        }

        id = "results"
        Box{
            sx {
                gridArea = Area.ResultTitle
                display = Display.flex
                justifyContent = JustifyContent.spaceBetween
            }
            Typography {

                variant = TypographyVariant.h3
                +langMap["results"]!![lang]!!
            }

            OrderSelector {
                this.resultsOrder = props.resultsOrder
                this.onOrderSelect = props.onOrderSelect
            }

        }

        Box {
            sx {
                gridArea = Area.Results
            }
            props.resultList.forEach { result ->
                result {
                    data = result
                }
            }
            PageNav{
                this.maxPages = props.maxPages
                this.currentPage = props.currentPage
                this.onPageClick = props.onPageSelect
            }
        }
    }
}