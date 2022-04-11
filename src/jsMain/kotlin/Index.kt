import frontend.app.app
import react.dom.render
import kotlinx.browser.document
import react.create

fun main() {
    kotlinext.js.require("./static/style.css")
    val rootDiv = document.getElementById("root")!!
    render(app.create(), rootDiv)
}
