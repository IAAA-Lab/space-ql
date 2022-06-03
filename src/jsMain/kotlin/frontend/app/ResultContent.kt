package frontend.app

import Dataset
import MetadataRecord
import Service
import csstype.*
import frontend.app.Title.title
import frontend.common.Area
import frontend.common.Sizes
import frontend.getSingleResult
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import mui.icons.material.Cloud
import mui.icons.material.ExpandMore
import mui.icons.material.InsertDriveFileOutlined
import mui.icons.material.Storage
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.Box
import mui.system.sx
import react.*
import frontend.app.results.result
import react.router.Navigate
import react.router.useParams

external interface ResultContentProps : Props {

}

val resultContent = FC<ResultContentProps> { props ->

    val (data, setData) = useState(MetadataRecord())

    val id = useParams()["metadata"]?: run {
        return@FC Navigate {
            to="/"
        }
    }
    useEffectOnce {
        scope.launch {
           setData(getSingleResult(id))
        }
    }



    Box {
        sx {
            gridArea = Area.Content
        }

        Box {
            title{}
            if(data.ID != ""){
                Box {
                    sx {
                        marginInline = 60.px
                    }
                    // Type of the Element
                    Typography {
                        sx {
                            fontSize = FontSize.medium
                        }
                        asDynamic().color = "text.secondary"

                        when(data.type) {
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
                    // Title of the element
                    Typography {
                        variant = TypographyVariant.h6
                        +data.title
                    }
                    Divider{
                        variant=DividerVariant.middle
                        sx{
                            marginTop=10.px
                            marginBottom=10.px
                        }
                    }
                    Typography {
                        variant = TypographyVariant.body1
                        +data.description
                    }
                    Divider{
                        variant=DividerVariant.middle
                        sx{
                            marginTop=10.px
                            marginBottom=10.px
                        }
                    }
                    Accordion {
                        AccordionSummary {
                            this.id = "relatedResources"
                            expandIcon = ExpandMore.create()

                            Typography {
                                +"Related Resources"
                            }
                            Typography{
                                asDynamic().color = "text.secondary"
                                +"Other records that could be relevant"
                            }
                        }
                        AccordionDetails{
                            sx{
                                marginInline=70.px
                            }
                            val topic = data.primaryTopic
                            if(topic != null && topic is Service){
                                topic.coupledDatasets?.forEach{
                                    result {
                                        this.data = it
                                    }
                                }
                            }else if(topic != null && topic is Dataset){
                                topic.coupledServices?.forEach{
                                    result {
                                        this.data = it
                                    }
                                }
                            }
                        }

                    }

                }
            }

        }


    }

}