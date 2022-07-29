package frontend.app.pageview

import application.model.*
import csstype.*
import frontend.app.components.languages.LangContext
import frontend.app.components.languages.langMap
import frontend.app.components.title.title
import frontend.app.components.results.RelatedResult
import frontend.app.scope
import frontend.common.Area
import frontend.common.getSingleResult
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
import frontend.common.removeRelated
import react.router.Navigate
import react.router.NavigateOptions
import react.router.useNavigate
import react.router.useParams


val resultContent = FC<Props> { props ->

    val (data, setData) = useState(MetadataRecord())
    val navigate = useNavigate()

    val lang = useContext(LangContext).lang

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
            if(data.ID != null){
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
                    // Title of the element
                    Typography {
                        variant = TypographyVariant.h6
                        +data.title!!
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
                        +data.description!!
                    }
                    Divider{
                        variant=DividerVariant.middle
                        sx{
                            marginTop=10.px
                            marginBottom=10.px
                        }
                    }
                    // Related Resources accordion
                    Accordion {
                        AccordionSummary {
                            this.id = "relatedResources"
                            expandIcon = ExpandMore.create()

                            Typography {
                                sx{
                                    fontWeight= FontWeight.bolder
                                }
                                +langMap["RelResources"]!![lang]!!
                            }
                            Typography{
                                sx {
                                    marginLeft=20.px
                                }
                                asDynamic().color = "text.secondary"
                                +langMap["RelResSubtext"]!![lang]!!
                            }
                        }
                        AccordionDetails{
                            sx{
                                marginInline=70.px
                            }
                            val topic = data.primaryTopic
                            if(topic != null && topic is cliService){
                                topic.coupledDatasets?.forEach{
                                    if(it.related == true) {
                                        RelatedResult {
                                            this.data = it.relatedRecord!!
                                            this.removeRelated = {
                                                scope.launch {
                                                    val obtained = removeRelated(data.ID!!, it.relatedRecord?.ID!!)
                                                    setData(obtained)
                                                }
                                            }
                                        }
                                    }
                                }
                            }else if(topic != null && topic is cliDataset){
                                topic.coupledServices?.forEach{
                                    if(it.related == true){
                                        RelatedResult {
                                            this.data = it.relatedRecord!!
                                            this.removeRelated = {
                                                scope.launch {
                                                    val obtained = removeRelated(data.ID!!, it.relatedRecord?.ID!!)
                                                    setData(obtained)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                    // Contact Point Accordion
                    Accordion{
                        AccordionSummary {
                            this.id = "ContactPoint"
                            expandIcon = ExpandMore.create()

                            Typography {
                                sx{
                                    fontWeight= FontWeight.bolder
                                }
                                +langMap["ContactPoint"]!![lang]!!
                            }
                        }
                        AccordionDetails {

                            List {
                                sx {
                                    width = 70.ch
                                }
                                if(data.details?.contactPoint?.organization?.name!=null && data.details?.contactPoint?.organization?.name !="" ){
                                    ListItem {
                                        sx {
                                            paddingLeft = 10.px
                                        }
                                        ListItemText {
                                            sx {
                                                fontWeight = FontWeight.bold
                                            }
                                            primary = ReactNode(langMap["org"]!![lang]!!)
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
                                                    override var state: Any? = "Contact;${data.details?.contactPoint?.organization?.name}"
                                                })
                                            }
                                            +"${data.details?.contactPoint?.organization?.name!!} ${if(data.details?.contactPoint?.organization?.wholeName != null){"- ${data.details?.contactPoint?.organization?.wholeName}"}else{""}}"
                                        }
                                    }
                                }
                                if(data.details?.contactPoint?.organization?.subOrganization != null && data.details?.contactPoint?.organization?.subOrganization !="" ){
                                    ListItem {
                                        sx {
                                            paddingLeft = 10.px
                                        }
                                        ListItemText {
                                            sx {
                                                fontWeight = FontWeight.bold
                                            }
                                            primary = ReactNode(langMap["subOrg"]!![lang]!!)
                                        }
                                        Link {
                                            sx{
                                                marginLeft = 30.px
                                            }
                                            variant="body2"
                                            +data.details?.contactPoint?.organization?.subOrganization!!
                                        }
                                    }
                                }
                                if(data.details?.contactPoint?.individual != null && data.details?.contactPoint?.individual != ""){
                                    ListItem {
                                        sx {
                                            paddingLeft = 10.px
                                        }
                                        ListItemText {
                                            sx {
                                                fontWeight = FontWeight.bold
                                            }
                                            primary = ReactNode(langMap["individual"]!![lang]!!)
                                        }
                                        Typography {
                                            sx{
                                                marginLeft = 30.px
                                            }
                                            variant = TypographyVariant.body2
                                            +data.details?.contactPoint?.individual!!
                                        }
                                    }
                                }
                                if(data.details?.contactPoint?.mail !=null && data.details?.contactPoint?.mail != ""){
                                    ListItem {
                                        sx {
                                            paddingLeft = 10.px
                                        }
                                        ListItemText {
                                            sx {
                                                fontWeight = FontWeight.bold
                                            }
                                            primary = ReactNode(langMap["mail"]!![lang]!!)
                                        }
                                        Typography {
                                            sx{
                                                marginLeft = 30.px
                                            }
                                            variant = TypographyVariant.body2
                                            +data.details?.contactPoint?.mail!!
                                        }
                                    }
                                }
                                if(data.details?.contactPoint?.phone!=null && data.details?.contactPoint?.phone !=""){
                                    ListItem {
                                        sx {
                                            paddingLeft = 10.px
                                        }
                                        ListItemText {
                                            sx {
                                                fontWeight = FontWeight.bold
                                            }
                                            primary = ReactNode(langMap["phone"]!![lang]!!)
                                        }
                                        Typography {
                                            sx{
                                                marginLeft = 30.px
                                            }
                                            variant = TypographyVariant.body2
                                            +data.details?.contactPoint?.phone!!
                                        }
                                    }
                                }
                                if(data.details?.contactPoint?.onlineSource!=null && data.details?.contactPoint?.onlineSource !=""){
                                    ListItem {
                                        sx {
                                            paddingLeft = 10.px
                                        }
                                        ListItemText {
                                            sx {
                                                fontWeight = FontWeight.bold
                                            }
                                            primary = ReactNode(langMap["onlineSource"]!![lang]!!)
                                        }
                                        Typography {
                                            sx{
                                                marginLeft = 30.px
                                            }
                                            variant = TypographyVariant.body2
                                            +data.details?.contactPoint?.onlineSource!!
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // Extra information about the result
                    Accordion {
                        AccordionSummary {
                            this.id = "resultInfo"
                            expandIcon = ExpandMore.create()

                            Typography {
                                sx{
                                    fontWeight= FontWeight.bolder
                                }
                                +langMap["moreInfo"]!![lang]!!
                            }
                            Typography{
                                sx {
                                    marginLeft=20.px
                                }
                                asDynamic().color = "text.secondary"
                                +langMap["moreInfoSubtext"]!![lang]!!
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
                                        primary= ReactNode(langMap["lang"]!![lang]!!)
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
                                                override var state: Any? = "Language;${data.details?.language!!}"
                                            })
                                        }
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
                                        primary= ReactNode(langMap["uplDate"]!![lang]!!)
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