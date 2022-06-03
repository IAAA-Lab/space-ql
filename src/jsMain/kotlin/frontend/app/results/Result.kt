package frontend.app.results

import Dataset
import MetadataRecord
import Service
import csstype.*
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

    console.log(props.data.type)

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
                   sx {
                       fontSize = FontSize.medium
                   }
                    asDynamic().color = "text.secondary"

                    when(props.data.type) {
                        "service" -> {
                            Icon {
                                sx {
                                    marginTop = 5.px
                                    marginRight = 5.px
                                }
                                Cloud()
                            }
                            +" Service"
                        }
                        "dataset" -> {
                            Icon {
                                sx {
                                    marginTop = 5.px
                                    marginRight = 5.px
                                }
                                Storage()
                            }
                            +" Dataset"
                        }
                        else -> {
                            Icon {
                                sx {
                                    marginTop = 5.px
                                    marginRight = 5.px
                                }
                                InsertDriveFileOutlined()
                            }
                            +" Unknown type"
                        }
                    }
                }
                Typography {
                    variant = TypographyVariant.h5
                    +props.data.title
                }
                Typography {
//                    variant = TypographyVariant.body2
                    sx {
                        marginBottom = 15.px
                    }
                    +"${props.data.description.take(500)}${extra}"
                }
                Box {
                    sx{
                        display = Display.flex
                    }
                    Typography {
                        variant = TypographyVariant.body2
                        sx{
                            fontWeight = FontWeight.bold
                            fontSize = 1.rem
                        }
                        val topic = props.data.primaryTopic
                        if(topic != null && topic is Service) {
                                +"Related Datasets: ${topic.coupledDatasets?.size}"
                        } else if(topic != null && topic is Dataset){
                                +"Related Services: ${topic.coupledServices?.size}"
                        }
                    }

                    Typography {
                        variant = TypographyVariant.body2
                        sx{
                            fontWeight = FontWeight.bold
                            fontSize = 1.rem
                            marginInline = 10.px
                        }
                        +"Language: ${props.data.details?.language}"
                    }

                }
            }
        }

    }
}