package frontend.app.Title

import csstype.TextAlign
import mui.material.Typography
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.FC
import react.Props
import react.router.dom.Link

val title = FC<Props>{
    Link {
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