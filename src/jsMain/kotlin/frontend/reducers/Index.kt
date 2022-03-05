package frontend.reducers

import redux.RAction

data class State(
    val text: String = ""
)

fun rootReducer(
    state: State,
    action: Any
) = State(
    textField(state.text, action.unsafeCast<RAction>())
)
