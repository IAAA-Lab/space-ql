package frontend.app.results.PageNav

import csstype.Display
import csstype.JustifyContent
import csstype.px
import mui.material.*
import mui.system.sx
import org.w3c.dom.HTMLButtonElement
import react.FC
import react.Props
import react.dom.events.MouseEventHandler
import react.useState


external interface PageNavProps : Props {
    var onPageClick: (Int) -> Unit
    var currentPage: Int
    var maxPages: Int
}

val PageNav = FC<PageNavProps> { props ->
    val maxPageNum = 10
    val (firstPage, setFirstPage) = useState(1)
    val (lastPage, setLastPage) = useState(
        if(props.maxPages > maxPageNum){
            maxPageNum
        } else{
            props.maxPages
        })

    val nextPagesList : MouseEventHandler<HTMLButtonElement> = {
        if(props.maxPages > (lastPage + maxPageNum)){
            setLastPage(lastPage + maxPageNum)
        } else{
            setLastPage(props.maxPages)
        }
        setFirstPage(firstPage + maxPageNum)
    }

    val nextPageHandler : MouseEventHandler<HTMLButtonElement> = {
        props.onPageClick(props.currentPage + 1)
        if(props.currentPage + 1 > lastPage) {
            nextPagesList(it)
        }
    }

    val prevPagesList : MouseEventHandler<HTMLButtonElement> = {
        setLastPage(lastPage - maxPageNum)
        setFirstPage(firstPage - maxPageNum)
    }

    val prevPageHandler : MouseEventHandler<HTMLButtonElement> = {
        props.onPageClick(props.currentPage - 1)
        if(props.currentPage - 1 in 1 until firstPage) {
            prevPagesList(it)
        }
    }

    val pageClickHandler : MouseEventHandler<HTMLButtonElement> = {
        val pageStr = it.currentTarget.value
        props.onPageClick(pageStr.toInt())
    }

    Box{
        sx {
            justifyContent = JustifyContent.center
            display = Display.flex
            marginTop = 10.px
            marginBottom = 10.px
        }
        id = "PageNav"
        ButtonGroup {
            variant = ButtonGroupVariant.outlined
            Button {
                if(props.currentPage == 1){
                    disabled = true
                }
                onClick = prevPageHandler
                +"Prev"
            }
            if(firstPage != 1){
                Button {
                    onClick = prevPagesList
                    +"..."
                }
            }

            for (page in firstPage..lastPage ){
                Button {
                    if(props.currentPage == page){
                        variant = ButtonVariant.contained
                    }
                    value = page.toString()
                    onClick = pageClickHandler
                    +"${page}"
                }
            }

            if(lastPage != props.maxPages){
                Button {

                    onClick = nextPagesList
                    +"..."
                }
            }

            Button {
                if(props.currentPage == props.maxPages){
                    disabled = true
                }
                onClick = nextPageHandler
                +"Next"
            }
        }
    }


}