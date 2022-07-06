package frontend.app.components.header

import csstype.integer
import csstype.number
import frontend.common.Area
import kotlinx.browser.window
import mui.icons.material.GitHub
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.*
import react.dom.aria.AriaHasPopup.`false`
import react.dom.aria.ariaHasPopup
import react.dom.aria.ariaLabel
import react.dom.html.ReactHTML.div


val Header = FC<Props> {props->

    AppBar {
        position = AppBarPosition.fixed
        sx {
            gridArea = Area.Header
            zIndex = integer(1_500)
        }

        Toolbar {
            Typography {
                variant = TypographyVariant.h5
                sx { flexGrow = number(1.0) }
                noWrap = true
                component = div
                +"Space-QL v1.0.0"
            }

            LanguageButton {}

            Tooltip {
                title = ReactNode("View Sources")

                IconButton {
                    ariaLabel = "source code"
                    ariaHasPopup = `false`
                    size = Size.large
                    color = IconButtonColor.inherit
                    onClick = {
                        window.location.href =
                            "https://github.com/IAAA-Lab/space-ql"
                    }

                    GitHub()
                }
            }
        }
    }
}
