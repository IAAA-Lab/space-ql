package frontend.app.results.PageNav

import csstype.Display
import csstype.JustifyContent
import mui.material.*
import mui.system.sx
import org.w3c.dom.HTMLButtonElement
import react.FC
import react.Props
import react.dom.events.MouseEventHandler
import react.useState


external interface PageNavProps : Props {
    /* Cuando se pulse un número de página se propagará al contenedor
     * padre que modificará el estado realizando la petición en base
     * al número de página solicitado
     */
    var onPageClick: (Int) -> Unit
    // next y prev = onPageClick(page-1) ???
    // NO -> Hay que gestionar la posibilidad de que
    // vaya a un grupo de menos número (haya que cargar nueva
    // lista de números de página

}

val PageNav = FC<PageNavProps> {
    // handle next
    // page decrement (...)
    // page numbers
    // page increment (...)
    // handle prev
    val (currentPage, setCurrentPage) = useState(1)
    val (firstPage, setFirstPage) = useState(1)
    val (lastPage, setLastPage) = useState(10)
    val maxPageNum = 10

    val nextPageHandler : MouseEventHandler<HTMLButtonElement> = {
        setCurrentPage(currentPage + 1)

        if(currentPage + 1 > lastPage){
            setLastPage(lastPage + maxPageNum)
            setFirstPage(firstPage + maxPageNum)
        }
    }

    val prevPageHandler : MouseEventHandler<HTMLButtonElement> = {
        setCurrentPage(currentPage - 1)

        if(currentPage - 1 < firstPage && currentPage - 1 > 0){
            setLastPage(lastPage - maxPageNum)
            setFirstPage(firstPage - maxPageNum)
        }
    }

    val pageClickHandler : MouseEventHandler<HTMLButtonElement> = {
        val pageStr = it.currentTarget.value
        setCurrentPage(pageStr.toInt())
    }

    Box{
        sx {
            justifyContent = JustifyContent.center
            display = Display.flex
        }
        id = "PageNav"
        ButtonGroup {
            variant = ButtonGroupVariant.outlined
            Button {
                if(currentPage == 1){
                    disabled = true
                }
                onClick = prevPageHandler
                +"Prev"
            }

            for (page in firstPage..lastPage ){
                Button {
                    if(currentPage == page){
                        variant = ButtonVariant.contained
                    }
                    value = page.toString()
                    onClick = pageClickHandler
                    +"${page}"
                }
            }

            Button {
                onClick = nextPageHandler
                +"Next"
            }
        }
    }


}