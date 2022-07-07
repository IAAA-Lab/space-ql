package frontend.app.components.results

import application.model.MetadataRecord
import csstype.*
import frontend.app.components.languages.LangContext
import frontend.app.components.languages.langMap
import frontend.app.components.results.OrderSelector.OrderSelector
import frontend.app.components.results.PageNav.PageNav
import frontend.common.Area
import frontend.common.Sizes
import mui.material.*
import mui.material.styles.TypographyVariant
import mui.system.sx
import react.FC
import react.Props
import react.useContext

external interface ResultsProps : Props {
    var resultList : List<MetadataRecord>
    var maxPages: Int 
    var currentPage: Int
    var onPageSelect: (Int) -> Unit
}

val Results = FC<ResultsProps> { props ->
    val lang = useContext(LangContext).lang

    Box {
        sx {
            display = Display.grid
            justifyContent = JustifyContent.center
        }
        Typography {
            variant = TypographyVariant.h3
            +langMap["results"]!![lang]!!
        }
        Box {
            sx {
                display = Display.grid
                justifyContent = JustifyContent.center
            }

            props.resultList.forEach { result ->
                result {
                    data = result
                }
            }
            PageNav {
                this.maxPages = props.maxPages
                this.currentPage = props.currentPage
                this.onPageClick = props.onPageSelect
            }
        }
    }
}