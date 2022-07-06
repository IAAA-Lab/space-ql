package frontend.app.components.sidebar

import application.model.SubFacets
import csstype.TextAlign
import csstype.pct
import csstype.px
import frontend.app.components.languages.LangContext
import frontend.app.components.languages.langMap
import frontend.common.Sizes
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
            it.subFacets.forEach { subFacet ->
                if(subFacet.docNum!! > 0){
                    ListItem{
                        sx {
                            paddingLeft=10.px
                            width = 100.pct
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



}