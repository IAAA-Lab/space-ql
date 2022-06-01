package frontend.app.Sidebar


import Facets
import csstype.px
import frontend.common.Area
import frontend.common.Sizes
import mui.material.*
import mui.material.DrawerAnchor.left
import mui.material.DrawerVariant.permanent
import mui.material.List
import mui.material.styles.TypographyVariant
import mui.system.Box
import mui.system.sx
import react.*
import react.dom.html.ReactHTML.div

external interface SidebarProps : Props {
    var facets : List<Facets>
    // TODO - setChecked(facet, subFacet)
}

val Sidebar = FC<SidebarProps> {

    Box {
        component = div
        sx {
            gridArea = Area.Sidebar
        }

        Drawer {
            variant = permanent
            anchor = left

            Box {
                Toolbar()

                // Lista para alojar las facets, que a su vez
                // son listas de subfacets
                List {
                    sx { width = Sizes.Sidebar.Width }

                    it.facets.forEach {
                        Facet{
                            this.title = it.name!!
                            this.subFacets = it.values!!
                            // TODO this.setCheked = { subfacet -> setChecked(it.name, subfacet)}
                        }
                    }

                        Divider{}
                }
            }
        }
    }
}

