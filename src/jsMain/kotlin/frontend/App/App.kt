package frontend.App

import MetaData
import frontend.Results.result
import frontend.Searchbar.SearchBar
import frontend.getResults
import kotlinx.coroutines.CoroutineScope
import kotlinx.html.id
import react.*
import react.dom.div
import react.dom.h1
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.p
import react.dom.p
import react.router.Route
import react.router.Routes
import react.router.dom.BrowserRouter



val App = FC<Props> { _ ->
    var resultList by useState(listOf(MetaData(title = "titulo",
        content = "a", id="a", location = "a")))
    h1 {
        +"Test Browser"
    }
    div {
        id = "main-container"
        SearchBar {
//                            attrs {
//                                onSubmit = { input ->
//                                    resultList = getResults(input)
//                                }
//                            }
        }
        div {
            id = "results"
            h1 {
                id = "results-title"
                +"Results"
            }
            div {
                id = "result-container"
                resultList.forEach { result ->
                    result {
                        data = result
                        key = result.id
                    }
                }

            }
        }
        div {
            id = "results-nav"
            p {
                +"Prev"
            }
            p{
                +"Next"
            }
        }
    }
}
