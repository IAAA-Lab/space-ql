package frontend.app.Sidebar

import csstype.Color
import csstype.None.none
import csstype.integer
import csstype.px
import emotion.react.css
import frontend.common.Area
import frontend.common.Sizes
import mui.icons.material.ExpandLess
import mui.icons.material.ExpandMore
import mui.material.*
import mui.material.DrawerAnchor.left
import mui.material.DrawerVariant.permanent
import mui.system.Box
import mui.system.sx
import react.*
import react.dom.html.ReactHTML.div
import react.router.dom.NavLink

val Sidebar = FC<Props> {
    var (open, setOpen) = useState(false)

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

                    List {

                        ListItemButton {
                            onClick = {
                                setOpen(!open)
                            }
                            ListItemText {
                                primary = ReactNode("Source Type")

                            }
                            if (open){
                                ExpandLess()
                            }else{
                                ExpandMore()
                            }
                        }
                        Collapse{
                            `in` = open
                            timeout="auto"
                            List {
                                ListItem{
                                    ListItemText{
                                        sx{
                                            paddingLeft= 15.px
                                        }
                                        primary = ReactNode("Service")
                                    }
                                        Checkbox{
                                            checked=false
                                        }
                                }
                                ListItem{
                                    ListItemText{
                                        sx{
                                            paddingLeft= 15.px
                                        }
                                        primary = ReactNode("Dataset")
                                    }
                                    Checkbox{
                                        checked=false
                                    }
                                }
                            }
                        }
                    }
                    Divider{}

                }
            }
        }
    }
}
