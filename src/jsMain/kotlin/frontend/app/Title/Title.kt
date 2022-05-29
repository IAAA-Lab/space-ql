package frontend.app.Title

import csstype.None
import csstype.TextAlign
import emotion.react.css
import mui.material.Typography
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.FC
import react.Props
import react.router.dom.Link

val title = FC<Props>{
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
            +"GraphQL-based metadata browser"
        }
    }
}