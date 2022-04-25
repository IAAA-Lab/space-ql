package frontend.app.results

import MetaData
import csstype.*
import frontend.common.Area
import frontend.common.Sizes
import mui.material.*
import mui.system.sx
import react.FC
import react.Props

external interface ResultsProps : Props {
    var resultList : List<MetaData>
}

val Results = FC<ResultsProps> {
    Box {
        sx {
            display = Display.grid
            justifyContent = JustifyContent.center

            marginRight = 100.px
            marginLeft = 100.px

            gridTemplateRows = array(
                Sizes.Header.Height,
                Auto.auto,
            )

            gridTemplateAreas = GridTemplateAreas(
                arrayOf(Area.ResultTitle),
                arrayOf(Area.Results)
            )

            marginTop = 10.px
        }

        id = "results"
        Typography {
            sx {
                gridArea = Area.ResultTitle
            }
            variant = "h3"
            +"Results"
        }

        Box {
            sx {
                gridArea = Area.Results
            }
            it.resultList.forEach { result ->
                result {
                    data = result
                }
            }
        }
    }
}