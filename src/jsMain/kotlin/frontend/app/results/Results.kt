package frontend.app.results

import MetaData
import csstype.*
import frontend.app.results.PageNav.PageNav
import frontend.common.Area
import frontend.common.Sizes
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.sx
import org.w3c.dom.HTMLInputElement
import react.FC
import react.Props
import react.ReactNode
import react.dom.events.ChangeEventHandler

external interface ResultsProps : Props {
    var resultList : List<MetaData>
    var maxPages: Int 
    var currentPage: Int
    var onPageSelect: (Int) -> Unit
    var onOrderSelect: (String) -> Unit
    var resultsOrder: String
}

val Results = FC<ResultsProps> { props ->

    val onOrderSelectHandler: ChangeEventHandler<HTMLInputElement> = {
        props.onOrderSelect(it.target.value)
    }

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
                +"Results"
            }

            FormControl {
                InputLabel {
                    id = "order-select-label"
                    +"Order"
                }
                Select {
                    labelId = "order-label"
                    id = "order-select"
                    label = ReactNode("Order")

                    // TODO: add order as state in App and add useState as props param
                    onChange = {event,_ ->
                        props.onOrderSelect(event.target.value)
                    }

                    value = props.resultsOrder.unsafeCast<Nothing?>()

                    MenuItem {
                        value = "Relevance"
                        +"Relevance"
                    }
                    MenuItem {
                        value = "Date"
                        +"Date"
                    }
                    MenuItem {
                        value = "Name"
                        +"Name"
                    }
                }
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