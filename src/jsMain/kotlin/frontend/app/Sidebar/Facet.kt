package frontend.app.Sidebar

import application.model.SubFacets
import csstype.px
import frontend.app.Languages.LangContext
import frontend.app.Languages.langMap
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.FC
import react.Props
import react.ReactNode
import react.useContext

external interface FacetProps : Props {
    var title : String
    var subFacets  : List<SubFacets>
    var setChecked : (String, Boolean) -> Unit // Porque la facet se pone desde la anterior llamada
}

val Facet = FC<FacetProps> {
    val lang = useContext(LangContext).lang

    Typography{
        variant = TypographyVariant.subtitle1
//        +it.title
        +langMap[it.title]!![lang]!!
    }
    List {
        it.subFacets.forEach { subFacet ->
            if(subFacet.docNum!! > 0){
                ListItem{
                    sx {
                        paddingLeft=10.px
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

    }
}