package frontend.app.Sidebar

import SubFacets
import csstype.px
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.FC
import react.Props
import react.ReactNode

external interface FacetProps : Props {
    var title : String
    var subFacets  : List<SubFacets>
    var setChecked : (String, Boolean) -> Unit // Porque la facet se pone desde la anterior llamada
}

val Facet = FC<FacetProps> {
    Typography{
        variant = TypographyVariant.subtitle1
        +it.title
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