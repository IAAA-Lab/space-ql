package frontend.app.components.sidebar

import application.model.Facets
import frontend.common.Area
import frontend.common.Sizes
import mui.material.*
import mui.material.DrawerVariant.permanent
import mui.material.List
import mui.system.Box
import mui.system.sx
import react.*
import react.dom.html.ReactHTML.div

external interface SidebarProps : Props {
    var facets : List<Facets>
    var setChecked : (String, String, Boolean) -> Unit
}

val Sidebar = FC<SidebarProps> { props ->

    Box {
        component = div
        sx {
            gridArea = Area.Sidebar
        }

        Drawer {
            variant = permanent

            Box {
                Toolbar()

                // Lista para alojar las facets, que a su vez
                // son listas de subfacets
                List {
                    sx { width = Sizes.Sidebar.Width }

                    props.facets.forEach {
                        Facet{
                            this.title = it.name!!
                            this.subFacets = it.values!!
                            this.setChecked = { subfacet, checked -> props.setChecked(it.name!!, subfacet, checked)}
                        }
                    }

                        Divider{}
                }
            }
        }
    }
}
