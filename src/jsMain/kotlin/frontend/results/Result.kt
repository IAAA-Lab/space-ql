package frontend.results

import MetaData
import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.p

external interface ResultProps : Props {
    var data: MetaData
}

// TODO: MUI card
val result = FC<ResultProps> { props ->
    div {
        className  = ClassName("result")
        h1 {
            className = ClassName("res-title")
            + props.data.data.fileName
        }
        p {
            +"Other metadata found"
        }
    }
}