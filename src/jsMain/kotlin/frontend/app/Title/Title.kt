package frontend.app.Title

import csstype.None
import csstype.TextAlign
import emotion.react.css
import frontend.app.Languages.LangContext
import frontend.app.Languages.langMap
import mui.material.Typography
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.FC
import react.Props
import react.router.dom.Link
import react.useContext


val title = FC<Props>{
    val lang = useContext(LangContext).lang

    Link {
        css {
            textDecoration = None.none
        }
        to = "/"
        Typography {
            sx{
                textAlign = TextAlign.center
            }
            variant = TypographyVariant.h1
            +"SpaceQL"
        }
        Typography {
            sx{
                textAlign = TextAlign.center
            }
            variant = TypographyVariant.subtitle1
            +langMap["title"]!![lang]!!
        }
    }
}