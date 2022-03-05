package frontend.components

import react.RBuilder
import react.createElement
import react.dom.h1
import react.router.Route
import react.router.Routes
import react.router.dom.BrowserRouter
import react.router.dom.NavLink

fun RBuilder.app() =
    BrowserRouter{
        Routes {
            Route {
                attrs.path = "/"
                attrs.element = createElement {
                    h1 {
                        +"Aqui estamos :,)"
                    }
                    NavLink {
                        attrs.to = "/welcome"
                        +"welcome"
                    }
                }
            }
            Route {
                attrs.path="/welcome"
                attrs.element = createElement {
                    welcome()
                }
            }
            Route {
                attrs.path="*"
                attrs.element = createElement {
                    h1 {
                        +"NO PUEDO MAS NO PUEDOOOO"
                    }
                }
            }

        }
    }