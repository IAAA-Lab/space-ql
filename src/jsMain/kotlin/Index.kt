import frontend.app.app
import react.dom.render
import kotlinx.browser.document
import kotlinx.html.dom.create
import react.create

fun main() {
    // kotlinext.js.require("./static/style.css")
    val rootDiv = document.getElementById("root")!!
//        .apply {
//        append(app.create())
//    }
    render(app.create(), rootDiv)
}
