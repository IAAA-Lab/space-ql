import frontend.App.App
import react.dom.render
import kotlinx.browser.document
import react.create

fun main() {
    kotlinext.js.require("./static/style.css")
   val rootDiv = document.getElementById("root")!!
    render(App.create(), rootDiv)
}
