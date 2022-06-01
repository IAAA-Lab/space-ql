package frontend.app.Sidebar


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

                        Typography{
                            variant = TypographyVariant.subtitle1
                            +"Resource Type"
                        }
                        List {
                            listOf("Dataset","Service","Other").forEach { type ->
                                ListItem{
                                    sx {
                                        paddingLeft=10.px
                                    }
                                    ListItemIcon {
                                        Checkbox{
                                            onChange={_, checked->
                                                console.log(type)
                                                console.log(checked)
                                            }
                                        }
                                    }
                                    ListItemText{
                                        primary=ReactNode(type)
                                    }
                                    Typography{
                                        variant = TypographyVariant.body2
                                        +"(2)"
                                    }
                                }
                            }

                        }
                        Divider{}
                        Typography{
                            variant = TypographyVariant.subtitle1
                            +"Related resources"
                        }
                        List {
                            listOf("0","1","2","3","+3").forEach { number ->
                                ListItem{
                                    sx {
                                        paddingLeft=10.px
                                    }
                                    ListItemIcon {
                                        Checkbox{
                                            checked=false
                                            onChange={_, checked->
                                                console.log(number)
                                                console.log(checked)
                                            }
                                        }
                                    }
                                    ListItemText{
                                        primary=ReactNode(number)
                                    }
                                    Typography{
                                        variant = TypographyVariant.body2
                                        +"(2)"
                                    }
                                }
                            }

                        }
                        Divider{}
                        Typography{
                            variant = TypographyVariant.subtitle1
                            +"Language"
                        }
                        List {
                            listOf("English","Spanish","Catalonian","Basque","Other").forEach { language ->
                                ListItem{
                                    sx {
                                        paddingLeft=10.px
                                    }
                                    ListItemIcon {
                                        Checkbox{
                                            onChange={_, checked->
                                                console.log(language)
                                                console.log(checked)
                                            }
                                        }
                                    }
                                    ListItemText{
                                        primary=ReactNode(language)
                                    }
                                    Typography{
                                        variant = TypographyVariant.body2
                                        +"(2)"
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
}

