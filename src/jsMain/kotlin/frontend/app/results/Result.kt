package frontend.app.results

import MetaData
import csstype.px
import mui.icons.material.Cloud
import mui.icons.material.InsertDriveFileOutlined
import mui.icons.material.Storage
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.FC
import react.Props
import react.key
import react.router.NavigateOptions
import react.router.useNavigate

external interface ResultProps : Props {
    var data: MetaData
}

val result = FC<ResultProps> { props ->
    val navigate = useNavigate()
    val extra = (if(props.data.data.fileDescription.length > 500)"..." else "")

    Card {

        this.onClick = {
            navigate("/id/${props.data.id}",
                options = object : NavigateOptions {
                    override var replace: Boolean? = null
                    override var state: Any? = null
                })
        }

        key = props.data.id
        variant = PaperVariant.outlined
        CardActionArea {
            sx {
                marginBottom = 10.px
                maxWidth = 1200.px
            }
            CardContent {

                Typography {
                    variant = TypographyVariant.h5
                    Icon {
                        sx {
                            marginTop = 5.px
                            marginRight = 5.px
                        }
                        when(props.data.data.scope) {
                            "service" -> Cloud()
                            "dataset" -> Storage()
                            else -> InsertDriveFileOutlined()
                        }
                    }
                    +"${props.data.data.fileName}"
                }
                Typography {
                    variant = TypographyVariant.body2
                    +"${props.data.data.fileDescription.take(500)}${extra}"
                }
            }
        }

    }
}