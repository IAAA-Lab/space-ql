package frontend.Results

import MetaData
import kotlinx.html.classes
import react.Props
import react.dom.div
import react.dom.h1
import react.dom.p
import react.fc

external interface ResultProps : Props {
    var data: MetaData
}

val result = fc<ResultProps> { props ->
    div {
        attrs.classes = setOf("result")
        h1 {
            attrs.classes = setOf("res-title")
            +"${props.data.title}"
        }
        p {
            +"Other metadata found"
        }
    }
}