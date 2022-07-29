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
import org.w3c.dom.HTMLButtonElement
import react.*
import react.dom.events.MouseEventHandler
import react.dom.html.ReactHTML
import react.router.NavigateOptions
import react.router.useNavigate

external interface RelatedResultProps : Props {
    var data: MetadataRecord
    var removeRelated: () -> Unit
}

val RelatedResult = FC<RelatedResultProps> { props ->

    val spainImg = "../spain.png"
    val ukImg = "../uk.png"

    val navigate = useNavigate()
    val extra = (if(props.data.description?.length!! > 500)"..." else "")
    val lang = useContext(LangContext).lang

    val removeFromRelated : MouseEventHandler<HTMLButtonElement> = {
        props.removeRelated()
    }

    Card {
        sx {
            marginBottom = 10.px
        }

        key = props.data.ID
        variant = PaperVariant.outlined
        CardActionArea {
            sx {
                marginBottom = 10.px
                maxWidth = 1200.px
            }
            CardContent {
                this.onClick = {
                    navigate("/id/${props.data.ID}",
                        options = object : NavigateOptions {
                            override var replace: Boolean? = false
                            override var state: Any? = null
                        })
                }
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
                    variant = TypographyVariant.h5
                    +props.data.title!!
                }
                Box{
                    sx{
                        display= Display.grid
                        gridTemplateColumns = array(6.fr, 1.fr)
                    }
                    Typography {
//                    variant = TypographyVariant.body2
                        sx {
                            marginBottom = 15.px
                        }
                        +"${props.data.description?.take(500)}${extra}"
                    }

                    Box{
                        sx{
                            display= Display.grid
                            gridTemplateColumns = array(1.fr)
                        }
                        props.data.details?.distributionFormats?.forEach {
                            Chip{
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
                            fontSize = 1.rem
                        }
                        val topic = props.data.primaryTopic
                        if(topic != null && topic is cliService) {
                                +"${langMap["relDatasets"]!![lang]!!}: ${topic.coupledDatasets?.size}"
                        } else if(topic != null && topic is cliDataset){
                                +"${langMap["relServices"]!![lang]!!}: ${topic.coupledServices?.size}"
                        }
                    }

                    Typography {
                        variant = TypographyVariant.body2
                        sx{
                            fontWeight = FontWeight.bold
                            fontSize = 1.rem
                            marginInline = 10.px
                        }
                        +"${langMap["lang"]!![lang]!!}: "//${props.data.details?.language}"
                    }
                    when (props.data.details?.language) {
                        "Spanish" -> {
                            ReactHTML.img {
                                css {
                                    width = 30.px
                                    height = 30.px
                                }
                                src = spainImg
                            }
                        }
                        "English" -> {
                            ReactHTML.img {
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
        CardActions {
            Button {
                color = ButtonColor.error
                onClick = removeFromRelated
                +langMap["notRelated"]!![lang]!!
            }
        }

    }
}