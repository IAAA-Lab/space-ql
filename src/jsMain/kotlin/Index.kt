import frontend.components.app
import frontend.reducers.State
import frontend.reducers.rootReducer
import react.dom.render
import kotlinx.browser.document
import react.redux.provider
import redux.createStore
import redux.rEnhancer

val store = createStore(::rootReducer, State(), rEnhancer())

fun main() {
   val rootDiv = document.getElementById("root")!!
    render(rootDiv){
        provider(store){
            app()
        }
    }
}
