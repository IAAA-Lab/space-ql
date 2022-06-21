package frontend.app.header

import csstype.Color
import frontend.app.Languages.LangContext
import mui.material.ToggleButton
import mui.material.ToggleButtonGroup
import mui.material.ToggleButtonGroupColor
import mui.system.sx
import react.FC
import react.Props
import react.useContext


val LanguageButton = FC<Props> {

    val lang = useContext(LangContext).lang
    val setter = useContext(LangContext).setLang

    fun setLang(newLang : String) {
        console.log("Button")
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