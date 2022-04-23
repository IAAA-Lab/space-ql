import frontend.components.WelcomeStyles
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.attrs
import styled.css
import styled.styledDiv
import styled.styledInput

external interface WelcomeProps : Props {
    var name: String
}

val welcome = fc<WelcomeProps> { props ->
    var name by useState { props.name }

    useEffect(name) {
        // Dispara axios
    }

    styledDiv {
        css {
            +WelcomeStyles.textContainer
        }
        +"Hello, $name"
    }
    styledInput {
        css {
            +WelcomeStyles.textInput
        }
        attrs {
            type = InputType.text
            value = props.name
            onChangeFunction = { event ->
                name = (event.target as HTMLInputElement).value
            }
        }
    }
}