package frontend.app

import Dataset
import MetadataRecord
import Service
import csstype.*
import frontend.app.Title.title
import frontend.app.results.RelatedResult
import frontend.common.Area
import frontend.getSingleResult
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
import react.router.NavigateOptions
import react.router.useNavigate
import react.router.useParams

external interface ResultContentProps : Props {

}

val resultContent = FC<ResultContentProps> { props ->

    val (data, setData) = useState(MetadataRecord())
    val navigate = useNavigate()

    val id = useParams()["metadata"]?: run {
        return@FC Navigate {
            to="/"
        }
    }
    useEffect(id){
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
                        marginInline = 80.px
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
                                sx{
                                    fontWeight= FontWeight.bolder
                                }
                                +"Related Resources"
                            }
                            Typography{
                                sx {
                                    marginLeft=20.px
                                }
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
                                    if(it.related) {
                                        RelatedResult {
                                            this.data = it.relatedRecord
                                        }
                                    }
                                }
                            }else if(topic != null && topic is Dataset){
                                topic.coupledServices?.forEach{
                                    if(it.related){
                                        RelatedResult {
                                            this.data = it.relatedRecord
                                            this.removeRelated = {
                                                console.log("removed " + it.relatedRecord.ID)
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }

                    Accordion{
                        AccordionSummary {
                            this.id = "ContactPoint"
                            expandIcon = ExpandMore.create()

                            Typography {
                                sx{
                                    fontWeight= FontWeight.bolder
                                }
                                +"Contact Point"
                            }
                        }
                        AccordionDetails {

                            List {
                                sx {
                                    width = 70.ch
                                }
                                if(data.details?.contactPoint?.name!=null && data.details.contactPoint.name !="" ){
                                    ListItem {
                                        sx {
                                            paddingLeft = 10.px
                                        }
                                        ListItemText {
                                            sx {
                                                fontWeight = FontWeight.bold
                                            }
                                            primary = ReactNode("Name")
                                        }
                                        Link {
                                            sx{
                                                marginLeft = 30.px
                                            }
                                            underline=LinkUnderline.hover
                                            variant="body2"
                                            onClick={
                                                navigate(to="/", options = object : NavigateOptions {
                                                    override var replace: Boolean? = null
                                                    override var state: Any? = data.details.contactPoint.name

                                                })
                                            }
                                            +data.details.contactPoint.name
                                        }
                                    }
                                }
                                if(data.details?.contactPoint?.individual != null && data.details.contactPoint.individual != ""){
                                    ListItem {
                                        sx {
                                            paddingLeft = 10.px
                                        }
                                        ListItemText {
                                            sx {
                                                fontWeight = FontWeight.bold
                                            }
                                            primary = ReactNode("Individual")
                                        }
                                        Typography {
                                            sx{
                                                marginLeft = 30.px
                                            }
                                            variant = TypographyVariant.body2
                                            +data.details.contactPoint.individual
                                        }
                                    }
                                }
                                if(data.details?.contactPoint?.mail !=null && data.details.contactPoint.mail != ""){
                                    ListItem {
                                        sx {
                                            paddingLeft = 10.px
                                        }
                                        ListItemText {
                                            sx {
                                                fontWeight = FontWeight.bold
                                            }
                                            primary = ReactNode("Mail")
                                        }
                                        Typography {
                                            sx{
                                                marginLeft = 30.px
                                            }
                                            variant = TypographyVariant.body2
                                            +data.details.contactPoint.mail
                                        }
                                    }
                                }
                                if(data.details?.contactPoint?.phone!=null && data.details.contactPoint.phone !=""){
                                    ListItem {
                                        sx {
                                            paddingLeft = 10.px
                                        }
                                        ListItemText {
                                            sx {
                                                fontWeight = FontWeight.bold
                                            }
                                            primary = ReactNode("Phone")
                                        }
                                        Typography {
                                            sx{
                                                marginLeft = 30.px
                                            }
                                            variant = TypographyVariant.body2
                                            +data.details.contactPoint.phone
                                        }
                                    }
                                }
                                if(data.details?.contactPoint?.onlineSource!=null && data.details.contactPoint.onlineSource !=""){
                                    ListItem {
                                        sx {
                                            paddingLeft = 10.px
                                        }
                                        ListItemText {
                                            sx {
                                                fontWeight = FontWeight.bold
                                            }
                                            primary = ReactNode("Online Source")
                                        }
                                        Typography {
                                            sx{
                                                marginLeft = 30.px
                                            }
                                            variant = TypographyVariant.body2
                                            +data.details.contactPoint.onlineSource
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Accordion {
                        AccordionSummary {
                            this.id = "resultInfo"
                            expandIcon = ExpandMore.create()

                            Typography {
                                sx{
                                    fontWeight= FontWeight.bolder
                                }
                                +"More Information"
                            }
                            Typography{
                                sx {
                                    marginLeft=20.px
                                }
                                asDynamic().color = "text.secondary"
                                +"Extra information about the Metadata"
                            }
                        }
                        AccordionDetails{

                            List {
                                sx{
                                    width=52.ch
                                }
                                ListItem{
                                    sx {
                                        paddingLeft=10.px
                                    }
                                    ListItemText{
                                        sx {
                                            fontWeight= FontWeight.bolder
                                        }
                                        primary= ReactNode("Language")
                                    }
                                    Typography{
                                        variant = TypographyVariant.body2
                                        +data.details?.language!!
                                    }
                                }
                                ListItem{
                                    sx {
                                        paddingLeft=10.px
                                    }
                                    ListItemText{
                                        sx {
                                            fontWeight= FontWeight.bolder
                                        }
                                        primary= ReactNode("Upload date")
                                    }
                                    Typography{
                                        variant = TypographyVariant.body2
                                        +data.details?.uploadDate!!
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