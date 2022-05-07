package frontend.app

import MetaData
import csstype.*
import frontend.app.Sidebar.Sidebar
import frontend.app.Title.title
import frontend.app.results.Results
import frontend.app.searchbar.SearchBar
import frontend.common.Area
import frontend.common.Sizes
import frontend.getResults
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import mui.material.Typography
import mui.material.styles.TypographyVariant
import mui.system.Box
import mui.system.sx
import react.FC
import react.Props
import react.dom.html.ReactHTML
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