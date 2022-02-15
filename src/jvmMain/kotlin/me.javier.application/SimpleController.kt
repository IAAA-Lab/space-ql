//package me.javier.application
//
//import kotlinx.html.*
//import kotlinx.html.stream.appendHTML
//import org.springframework.stereotype.Controller
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.ResponseBody
//
//@Controller
//@RequestMapping("/")
//class SimpleController {
//
//    @GetMapping
//    @ResponseBody
//    fun index(): String {
//        return("")
//    }
//
////    private fun myKotlinHtmlView(): String {
////        val sb = StringBuilder()
////        val tagConsumer = sb.appendHTML()
////
////        with(tagConsumer) {
////            html {
////                head {
////                    title("Hello world!")
////                }
////                body {
////                    div {
////                        +"Hello world"
////                    }
////                    div {
////                        id = "root"
////                    }
////                    script(src = "space-ql.js") {}
////                }
////            }
////        }
////
////        return (sb.toString())
////    }
//}