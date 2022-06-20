package frontend.app.Languages

import react.createContext

data class LangContextObject(
    val lang : String,
    val setLang : (String) -> Unit
)

val LangContext = createContext<LangContextObject>()