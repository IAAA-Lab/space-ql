package frontend.components

import frontend.actions.WriteField
import frontend.containers.field
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import react.*
import react.dom.attrs
import react.redux.rConnect
import redux.WrapperAction
import store
import styled.css
import styled.styledInput


external interface WelcomeProps : Props {
    var text: String
    var writeField: (String) -> Unit
}

@JsExport
class Welcome(props: WelcomeProps) : RComponent<Props, State>(props) {

    override fun RBuilder.render() {
        field()
        styledInput {
            css {
                +WelcomeStyles.textInput
            }
            attrs {
                type = InputType.text
                onChangeFunction = { store.dispatch(WriteField(it.toString()))
                }
            }
        }
    }
}

val welcome: ComponentClass<Props> =
    rConnect<Welcome, WrapperAction>()(Welcome::class.js.unsafeCast<ComponentClass<Props>>())