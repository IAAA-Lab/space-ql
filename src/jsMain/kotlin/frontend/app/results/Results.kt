package frontend.app.results

import MetaData
import csstype.*
import frontend.common.Area
import frontend.common.Sizes
import mui.material.*
import mui.system.sx
import react.FC
import react.Props
import react.key

external interface ResultProps : Props {
    var resultList : List<MetaData>
}

val Results = FC<ResultProps> {
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
                Card {
                    key = result.id

                    sx {
                        marginBottom = 10.px
                        maxWidth = 1200.px
                    }

                    variant = PaperVariant.outlined
                    CardContent {
                        Typography {
                            variant = "h5"
                            +result.data.fileName
                        }
                        Typography {
                            variant = "body2"
                            +result.data.fileDescription
                        }
                    }
                }
            }
        }
    }
}