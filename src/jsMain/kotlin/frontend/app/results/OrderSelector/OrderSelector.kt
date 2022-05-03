package frontend.app.results.OrderSelector

import mui.material.FormControl
import mui.material.InputLabel
import mui.material.MenuItem
import mui.material.Select
import react.FC
import react.Props
import react.ReactNode

external interface OrderSelectorProps : Props {
    var onOrderSelect: (String) -> Unit
    var resultsOrder: String
}

val OrderSelector = FC<OrderSelectorProps> { props ->
    FormControl {
        InputLabel {
            id = "order-select-label"
            +"Order"
        }
        Select {
            labelId = "order-label"
            id = "order-select"
            label = ReactNode("Order")

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