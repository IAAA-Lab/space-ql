package frontend.app

import csstype.*
import frontend.app.Title.title
import frontend.common.Area
import mui.material.Typography
import mui.material.styles.TypographyVariant
import mui.system.Box
import mui.system.sx
import react.FC
import react.Props
import react.router.Navigate
import react.router.useParams
import react.useEffectOnce

external interface ResultContentProps : Props {

}

val resultContent = FC<ResultContentProps> { props ->
    val id = useParams()["metadata"]?: run {
        return@FC Navigate {
            to="/"
        }
    }
    useEffectOnce {
        // TODO: Call backend to obtain all the data
    }



    Box {
        sx {
            gridArea = Area.Content
        }

        Box {

            title{}

            Box {
                Typography {
                    sx{
                        textAlign = TextAlign.center
                    }
                    variant = TypographyVariant.body2
                    +id
                }
            }
        }


    }

}