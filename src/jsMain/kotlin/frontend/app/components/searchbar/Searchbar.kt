package frontend.app.components.searchbar

import csstype.Display
import csstype.JustifyContent
import csstype.pct
import csstype.px
import frontend.app.components.languages.LangContext
import frontend.app.components.languages.langMap
import frontend.app.scope
import frontend.common.getSuggestions
import frontend.common.getTitles
import kotlinx.coroutines.launch
import mui.base.AutocompleteChangeReason
import mui.material.*
import mui.system.sx
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import react.Props
import react.dom.events.FormEventHandler
import react.*
import react.dom.html.ReactHTML.form
import react.dom.onChange
import react.useState


external interface SearchbarProps : Props {
    var onSubmit: (String) -> Unit
}

val SearchBar = FC<SearchbarProps> { props ->
    val (text, setText) = useState("")
    var suggestions by useState(emptyList<String>())
    val lang = useContext(LangContext).lang

    useEffectOnce {
        scope.launch {
            val allTitles = getTitles()
            suggestions = allTitles
        }
    }

    val submitHandler: FormEventHandler<HTMLDivElement> = {
        it.preventDefault()
        setText("")
        props.onSubmit(text)
    }

    val changeHandler: FormEventHandler<HTMLDivElement> = {
        val target = it.target as HTMLInputElement
        setText(target.value)
        scope.launch{
            suggestions = getSuggestions(target.value)
        }
    }

   Box {
       id="searchbar-container"
       sx {
           display = Display.grid
           justifyContent = JustifyContent.center
           marginTop = 10.px
           marginBottom = 10.px
       }
       component = form
       onSubmit = submitHandler

       @Suppress("UPPER_BOUND_VIOLATED")
       Autocomplete<AutocompleteProps<String>> {
           sx {width = 100.pct }
//           disablePortal = true
           this.noOptionsText = ReactNode(langMap["autocompleteNoOpt"]!![lang]!!)
           options = suggestions.toTypedArray()
           onChange = {_,newValue,reason,_ ->
               when(reason){
                   AutocompleteChangeReason.clear -> setText("")
                   else -> setText(newValue as String)
               }
           }

           renderInput = { params ->
               TextField.create{
                   +params
                   sx {width = 100.pct}
                   label = ReactNode(langMap["search"]!![lang]!!)
                   variant = FormControlVariant.outlined
                   value = text
                   placeholder = langMap["searchPlaceholder"]!![lang]!!
                   onChange = changeHandler
               }
           }
       }

   }
}