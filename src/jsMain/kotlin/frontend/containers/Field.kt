package frontend.containers

import frontend.components.Field
import frontend.components.FieldProps
import frontend.reducers.State
import react.ComponentClass
import react.Props
import react.invoke
import react.redux.rConnect


private external interface FieldStateProps : Props {
    var text: String
}

val field: ComponentClass<Props> =
    rConnect<State, FieldStateProps, Props, FieldProps>(
        { state, _ ->
            // TODO it should be something like text = state.text
        }
    )(Field::class.js.unsafeCast<ComponentClass<FieldProps>>())
