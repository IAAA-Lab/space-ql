package frontend.app.results.PageNav

import csstype.Display
import csstype.JustifyContent
import csstype.px
import mui.material.*
import mui.system.sx
import react.FC
import react.Props


external interface PageNavProps : Props {
    var onPageClick: (Int) -> Unit
    var currentPage: Int
    var maxPages: Int
}

val PageNav = FC<PageNavProps> { props ->
    Box {
        sx {
            justifyContent = JustifyContent.center
            display = Display.flex
            marginTop = 10.px
            marginBottom = 10.px
        }
        id = "PageNav"
        Pagination {
            count = props.maxPages
            page = props.currentPage
            color = PaginationColor.primary
            size = Size.large
            onChange = { e, number ->
                console.log("clicked " + number.toString())
                props.onPageClick(number.toInt())
            }
        }
    }



}