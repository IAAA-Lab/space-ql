package frontend.searchbar

import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import react.Props
import react.dom.events.ChangeEventHandler
import react.dom.events.FormEventHandler
import react.*
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input
import react.useState


external interface SearchbarProps : Props {
    var onSubmit: (String) -> Unit
}

val SearchBar = FC<SearchbarProps> { props ->
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
        id = "searchbar-container"
        form {
            onSubmit = submitHandler
            id = "searchbar-form"

            input {
                type = InputType.text
                id = "searchbar"
                name = "searchbar"
                onChange = changeHandler
                value = text
                placeholder = "Document's title"
            }
        }
    }
}