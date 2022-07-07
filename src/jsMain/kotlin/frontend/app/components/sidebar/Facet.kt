package frontend.app.components.sidebar

import application.model.SubFacets
import csstype.*
import frontend.app.components.languages.LangContext
import frontend.app.components.languages.langMap
import frontend.common.Sizes
import mui.icons.material.ExpandLess
import mui.icons.material.ExpandMore
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.*

external interface FacetProps : Props {
    var title : String
    var subFacets  : List<SubFacets>
    var setChecked : (String, Boolean) -> Unit // Porque la facet se pone desde la anterior llamada
}

val Facet = FC<FacetProps> {
    val lang = useContext(LangContext).lang
    val (isOpen, setIsOpen) = useState(false)
    val subfacetsNum = it.subFacets.filter{ subFacet -> subFacet.docNum!! > 0}.size

    Paper {
        elevation = 6
        sx {
            margin = 16.px
            padding = 24.px
        }
        Typography {
            variant = TypographyVariant.h6
            sx {
                textAlign = TextAlign.center
            }
            +langMap[it.title]!![lang]!!
        }
        List {
            sx {
                width = Sizes.Facet.Width
            }
            it.subFacets.filter{ subFacet -> subFacet.docNum!! > 0}.forEachIndexed { index, subFacet ->
                if(subFacet.docNum!! > 0){
                    ListItem{
                        sx {
                            paddingLeft=10.px
                            width = 100.pct
                            if(index >=5 && !isOpen){display = None.none}
                        }
                        ListItemIcon {
                            Checkbox{
                                checked = subFacet.checked
                                onChange={e, _->
                                    it.setChecked(subFacet.field!!, e.target.checked)
                                }
                            }
                        }
                        ListItemText{
                            primary= ReactNode(subFacet.field!!)
                        }
                        Typography{
                            variant = TypographyVariant.body2
                            +"(${subFacet.docNum})"
                        }
                    }
                }
            }
            if(subfacetsNum > 5){
                Divider{}
                ListItem{
                    sx {
                        paddingLeft = 24.px
                        paddingRight = 0.px
                        width = 100.pct
                        this.cursor = Cursor.pointer
                    }
                    onClick = {_ -> setIsOpen(!isOpen)}
                    ListItemText{
                        primary = if(isOpen){ ReactNode(langMap["showLess"]!![lang]!!)} else {ReactNode(langMap["showMore"]!![lang]!!)}
                    }
                    ListItemIcon {
                        if(isOpen){
                            ExpandLess{}
                        } else{
                            ExpandMore{}
                        }
                    }

                }
            }

        }

    }



}