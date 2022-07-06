package frontend.app.components.title

import csstype.None
import csstype.TextAlign
import emotion.react.css
import frontend.app.components.languages.LangContext
import frontend.app.components.languages.langMap
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
            id = "title1"
            sx{
                textAlign = TextAlign.center
            }
            variant = TypographyVariant.h1
            +"SpaceQL"
        }
        Typography {
            id = "title2"
            sx{
                textAlign = TextAlign.center
            }
            variant = TypographyVariant.subtitle1
            +langMap["title"]!![lang]!!
        }
    }
}