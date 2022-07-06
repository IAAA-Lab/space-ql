package frontend.app.components.sidebar

import application.model.Facets
import csstype.*
import frontend.app.components.results.OrderSelector.OrderSelector
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
    var onOrderSelect: (String) -> Unit
    var resultsOrder: String
}

val Sidebar = FC<SidebarProps> { props ->

    Box {
        sx {
            display = Display.grid
            gridTemplateColumns = array(1.fr)
            gridTemplateAreas = GridTemplateAreas(
                arrayOf(Area.Lists)
            )
        }

        Drawer {
            variant = permanent
            anchor = DrawerAnchor.left
            sx {
                gridArea = Area.Lists
                width = Sizes.Sidebar.ElementWidth
            }

            Toolbar()

            Paper {
                elevation = 6
                sx {
                    margin = 16.px
                    marginBottom = 0.px
                    marginTop = 24.px
                    padding = 24.px
                }
                OrderSelector {
                    this.resultsOrder = props.resultsOrder
                    this.onOrderSelect = props.onOrderSelect
                }
            }
            // Lista para alojar las facets, que a su vez
            // son listas de subfacets
            List {
                props.facets.forEach {
                    Facet{
                        this.title = it.name!!
                        this.subFacets = it.values!!
                        this.setChecked = { subfacet, checked -> props.setChecked(it.name!!, subfacet, checked)}
                    }
                }
            }

        }
    }
}

