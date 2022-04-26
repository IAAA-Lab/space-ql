package frontend.app.searchbar

import csstype.Display
import csstype.GridLineProperty
import csstype.JustifyContent
import csstype.px
import mui.material.*
import mui.system.sx
import org.w3c.dom.HTMLDivElement
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
import react.dom.onChange
import react.useState


external interface SearchbarProps : Props {
    var onSubmit: (String) -> Unit
}

val SearchBar = FC<SearchbarProps> { props ->
    val (text, setText) = useState("")

    val submitHandler: FormEventHandler<HTMLDivElement> = {
        it.preventDefault()
        setText("")
        props.onSubmit(text)
    }

    val changeHandler: FormEventHandler<HTMLDivElement> = {
        val target = it.target as HTMLInputElement
        setText(target.value)
    }

   Box {
       sx {
           display = Display.grid
           justifyContent = JustifyContent.center
           marginTop = 10.px
       }
       component = form

       // TODO: onSubmit
       onSubmit = submitHandler

       TextField {
           sx {
               width = 600.px
           }
           id = "searchbar-form"
           label = ReactNode("Search")
           variant = FormControlVariant.outlined
           value = text
           placeholder = "Document's title"
       // TODO: Use changeHandler on change
           onChange = changeHandler
       }

   }
}