import frontend.app.app
import react.dom.render
import kotlinx.browser.document
import kotlinx.html.dom.create
import react.create

fun main() {
    val rootDiv = document.getElementById("root")!!
    render(app.create(), rootDiv)
}
