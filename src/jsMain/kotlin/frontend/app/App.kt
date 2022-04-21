package frontend.app

import MetaData
import frontend.results.result
import frontend.searchbar.SearchBar
import frontend.getResults
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.p

private val scope = MainScope()

val app = FC<Props> {
    var resultList by useState(emptyList<MetaData>())
    h1 {
        +"Test Browser"
    }
    div {
        id = "main-container"
        SearchBar {
                onSubmit = { input ->
                    scope.launch {
                        resultList = getResults(input)
                    }
                }
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
