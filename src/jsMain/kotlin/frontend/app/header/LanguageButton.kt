package frontend.app.header

import csstype.Color
import mui.icons.material.FormatAlignJustify
import mui.icons.material.FormatAlignLeft
import mui.material.ToggleButton
import mui.material.ToggleButtonGroup
import mui.material.ToggleButtonGroupColor
import mui.system.sx
import react.FC
import react.Props
import react.dom.aria.ariaLabel
import react.useState

external interface LangButtonProps : Props {
    var currLang : String
    var setLang : (String) -> Unit
}

val LanguageButton = FC<LangButtonProps> {

    ToggleButtonGroup {
        sx {
            backgroundColor = Color("white")
        }

        exclusive = true
        onChange = {_, newLang -> it.setLang(newLang as String)}
        value = it.currLang
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