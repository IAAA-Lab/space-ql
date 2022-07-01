package frontend.app.components.header

import csstype.Color
import frontend.app.components.languages.LangContext
import mui.material.ToggleButton
import mui.material.ToggleButtonGroup
import mui.material.ToggleButtonGroupColor
import mui.system.sx
import react.FC
import react.Props
import react.useContext

// Used to change the language of the application, (between Spanish and English)
val LanguageButton = FC<Props> {

    val lang = useContext(LangContext).lang
    val setter = useContext(LangContext).setLang

    fun setLang(newLang : String) {
        setter(newLang)
    }

    ToggleButtonGroup {
        sx {
            backgroundColor = Color("white")
        }

        exclusive = true
        onChange = {_, newLang -> setLang(newLang as String)}
        value = lang
        color = ToggleButtonGroupColor.primary

        ToggleButton {
            value = "Esp"
            +"Esp"
        }
        ToggleButton {
            value = "Eng"
            +"Eng"
        }
    }
}