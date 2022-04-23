package frontend.app.Sidebar

import csstype.Color
import csstype.None.none
import emotion.react.css
import frontend.common.Area
import frontend.common.Sizes
import mui.material.*
import mui.material.DrawerAnchor.left
import mui.material.DrawerVariant.permanent
import mui.system.Box
import mui.system.sx
import react.FC
import react.Props
import react.ReactNode
import react.dom.html.ReactHTML.div
import react.router.dom.NavLink

val Sidebar = FC<Props> {

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

                List {
                    sx { width = Sizes.Sidebar.Width }

                    for (element in listOf("1", "2", "3", "4", "5", "6")) {
                        List {
                            ListItem {
                                ListItemButton{
                                    selected = element == "1"
                                    ListItemText {
                                        primary = ReactNode(element)
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
