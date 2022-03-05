package frontend.components

import react.Props
import react.RBuilder
import react.RComponent
import react.State
import react.dom.h1
import store
import styled.css
import styled.styledDiv


external interface FieldProps : Props {
    var text: String
}

@JsExport
class Field(props: FieldProps) : RComponent<FieldProps, State>(props) {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +WelcomeStyles.textContainer
            }
            +"Hello, ${props.text}"
        }
    }
}