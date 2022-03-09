package frontend.Searchbar

import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.id
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import react.Props
import react.dom.*
import react.dom.events.ChangeEventHandler
import react.dom.events.FormEventHandler
import react.fc
import react.useState


external interface SearchbarProps : Props {
    var onSubmit : (String) -> Unit
}

val SearchBar = fc<SearchbarProps> { props ->
    val (text, setText) = useState("")

    val submitHandler: FormEventHandler<HTMLFormElement> = {
        it.preventDefault()
        setText("")
        props.onSubmit(text)
    }

    val changeHandler: ChangeEventHandler<HTMLInputElement> = {
        setText(it.target.value)
    }

    div {
        attrs.id = "searchbar-container"
        form {
            attrs.onSubmit = submitHandler as FormEventHandler<*>
            attrs.id = "searchbar-form"

            input {
                attrs {
                    type = InputType.text
                    id = "searchbar"
                    name = "searchbar"
                    onChange = changeHandler as ChangeEventHandler<*>
                    value = text
                    placeholder = "Document's title"
                }
            }
        }

    }
}