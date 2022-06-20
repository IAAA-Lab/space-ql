package frontend.app.results.OrderSelector

import frontend.app.Languages.LangContext
import frontend.app.Languages.langMap
import mui.material.FormControl
import mui.material.InputLabel
import mui.material.MenuItem
import mui.material.Select
import react.FC
import react.Props
import react.ReactNode
import react.useContext

external interface OrderSelectorProps : Props {
    var onOrderSelect: (String) -> Unit
    var resultsOrder: String
}

val OrderSelector = FC<OrderSelectorProps> { props ->
    val lang = useContext(LangContext).lang

    FormControl {
        InputLabel {
            id = "order-select-label"
            +langMap["order"]!![lang]!!
        }
        Select {
            labelId = "order-label"
            id = "order-select"
            label = ReactNode(langMap["order"]!![lang]!!)

            onChange = {event,_ ->
                props.onOrderSelect(event.target.value)
            }

            value = props.resultsOrder.unsafeCast<Nothing?>()

            MenuItem {
                value = "Relevance"
                +langMap["relevance"]!![lang]!!
            }
            MenuItem {
                value = "Date"
                +langMap["date"]!![lang]!!
            }
            MenuItem {
                value = "Name"
                +langMap["name"]!![lang]!!
            }
        }
    }
}