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
    // TODO - setChecked(subfacet) // Porque la facet se pone desde la anterior llamada
}

val Facet = FC<FacetProps> {
    Typography{
        variant = TypographyVariant.subtitle1
        +it.title
    }
    List {
        it.subFacets.forEach { subFacet ->
            ListItem{
                sx {
                    paddingLeft=10.px
                }
                ListItemIcon {
                    Checkbox{
                        onChange={_, checked->
                            console.log(checked)
                            // TODO - setChecked(facet, subfacet)
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