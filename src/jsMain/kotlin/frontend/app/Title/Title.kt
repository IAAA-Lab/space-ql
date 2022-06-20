package frontend.app.Title

import csstype.None
import csstype.TextAlign
import emotion.react.css
import frontend.app.Languages.langMap
import mui.material.Typography
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.FC
import react.Props
import react.router.dom.Link

external interface TitleProps : Props {
    var lang : String
}

val title = FC<TitleProps>{
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
        if(it.lang.isNotEmpty()){
            Typography {
                sx{
                    textAlign = TextAlign.center
                }
                variant = TypographyVariant.subtitle1
                +langMap["title"]!![it.lang]!!
            }
        }
    }
}