package frontend.app.components.results


import application.model.*
import csstype.*
import emotion.react.css
import frontend.app.components.languages.LangContext
import frontend.app.components.languages.langMap
import mui.icons.material.Cloud
import mui.icons.material.InsertDriveFileOutlined
import mui.icons.material.Storage
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.*
import react.dom.html.ReactHTML.img
import react.router.NavigateOptions
import react.router.useNavigate
import styled.css

external interface ResultProps : Props {
    var data: MetadataRecord
}

val result = FC<ResultProps> { props ->

    val spainImg = "./spain.png"
    val ukImg = "./uk.png"
    val navigate = useNavigate()
    val lang = useContext(LangContext).lang

    val extra = (if(props.data.description?.length!! > 500)"..." else "")

    Card {
        sx {
            marginBlock = 16.px
            marginRight = 24.px
        }

        this.onClick = {
            navigate("/id/${props.data.ID}",
                options = object : NavigateOptions {
                    override var replace: Boolean? = false
                    override var state: Any? = null
                })
        }

        key = props.data.ID
        CardActionArea {
            CardContent {
                Typography {
                    variant = TypographyVariant.h4
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
                            +" ${langMap["Service"]!![lang]!!}"
                        }
                        "dataset" -> {
                            Icon {
                                sx {
                                    marginTop = 5.px
                                    marginRight = 5.px
                                }
                                Storage()
                            }
                            +" ${langMap["Dataset"]!![lang]!!}"
                        }
                        else -> {
                            Icon {
                                sx {
                                    marginTop = 5.px
                                    marginRight = 5.px
                                }
                                InsertDriveFileOutlined()
                            }
                            +" ${langMap["UnkType"]!![lang]!!}"
                        }
                    }
                }
                Typography {
                    sx {
                        marginBottom = 10.px
                    }
                    variant = TypographyVariant.h5
                    +props.data.title!!
                }
                Box{
                    sx{
                        display= Display.grid
                        gridTemplateColumns = array(6.fr, 1.fr)
                        columnGap = 24.px
                    }
                    Typography {
                        sx {
                            marginBottom = 10.px
                        }
                        variant = TypographyVariant.body2
                        +"${props.data.description?.take(500)}${extra}"
                    }
                    Box{
                        sx{
                            display = Display.grid
                            rowGap = 8.px
                        }
                        props.data.details?.distributionFormats?.forEach {
                            Chip{
                                color = ChipColor.primary
                                // TODO variant =
                                label = ReactNode(it.name!!)
                            }
                        }
                    }

                }
                Box {
                    sx{
                        display = Display.flex
                    }
                    Typography {
                        variant = TypographyVariant.body2
                        sx{
                            fontWeight = FontWeight.bold
                            fontSize = 1.3.rem
                        }
                        val topic = props.data.primaryTopic
                        if(topic != null && topic is cliService) {
                                +"${langMap["relDatasets"]!![lang]!!}: ${topic.coupledDatasets?.filter{it.related!!}?.size}"
                        } else if(topic != null && topic is cliDataset){
                                +"${langMap["relServices"]!![lang]!!}: ${topic.coupledServices?.filter{it.related!!}?.size}"
                        }
                    }

                    Typography {
                        variant = TypographyVariant.body2
                        sx{
                            fontWeight = FontWeight.bold
                            fontSize = 1.3.rem
                            marginInline = 32.px
                            marginRight = 8.px
                        }
                        +"${langMap["lang"]!![lang]!!}: "//${props.data.details?.language}"
                    }

                    when (props.data.details?.language) {
                        "Spanish" -> {
                            img {
                                css {
                                    width = 30.px
                                    height = 30.px
                                }
                                src = spainImg
                            }
                        }
                        "English" -> {
                            img {
                                css {
                                    width = 30.px
                                    height = 30.px
                                }
                                src = ukImg
                            }
                        }
                        else -> {
                            Typography {
                                variant = TypographyVariant.body2
                                sx{
                                    fontWeight = FontWeight.bold
                                    fontSize = 1.3.rem
                                }
                                +props.data.details?.language!!
                            }
                        }
                    }



                }
            }
        }

    }
}