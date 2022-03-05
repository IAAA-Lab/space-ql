package frontend.reducers

import frontend.actions.WriteField
import redux.RAction

fun textField(state: String = "", action: RAction): String {
    console.log("HA CAMBIADO")
    return when(action) {
        is WriteField -> action.text
        else -> state
    }
}