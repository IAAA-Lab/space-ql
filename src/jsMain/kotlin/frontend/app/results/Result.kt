package frontend.app.results

import MetaData
import csstype.ClassName
import csstype.px
import mui.material.Card
import mui.material.CardContent
import mui.material.PaperVariant
import mui.material.Typography
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.p
import react.key

external interface ResultProps : Props {
    var data: MetaData
}

val result = FC<ResultProps> { props ->
    Card {
        key = props.data.id

        sx {
            marginBottom = 10.px
            maxWidth = 1200.px
        }

        variant = PaperVariant.outlined
        CardContent {
            Typography {
                variant = TypographyVariant.h5
                +props.data.data.fileName
            }
            Typography {
                variant = TypographyVariant.body2
                +props.data.data.fileDescription
            }
        }
    }
}