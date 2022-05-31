package frontend.app.results

import Dataset
import MetadataRecord
import Service
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
    var data: MetadataRecord
}

val result = FC<ResultProps> { props ->
    val navigate = useNavigate()
    val extra = (if(props.data.description.length > 500)"..." else "")

    Card {
        sx {
            marginBottom = 10.px
        }

        this.onClick = {
            navigate("/id/${props.data.ID}",
                options = object : NavigateOptions {
                    override var replace: Boolean? = null
                    override var state: Any? = null
                })
        }

        key = props.data.ID
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
                        when(props.data.type) {
                            "service" -> Cloud()
                            "dataset" -> Storage()
                            else -> InsertDriveFileOutlined()
                        }
                    }
                    +"${props.data.title}"
                }
                Typography {
                    variant = TypographyVariant.body2
                    +"${props.data.description.take(500)}${extra}"
                }
                Typography {
                    variant = TypographyVariant.body2
                    when(props.data.type) {
                        "service" -> {
                            val topic = props.data.primaryTopic as Service
                            +"Related Datasets: ${topic.coupledDatasets?.size}"
                        }
                        "dataset" -> {
                            val topic = props.data.primaryTopic as Dataset
                            +"Related Services: ${topic.coupledServices?.size}"
                        }
                    }
                }
            }
        }

    }
}