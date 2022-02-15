package me.javier.application

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok


@Configuration
class SimpleRoute {
//
//    fun HTML.index() {
//        head {
//            title("Hello world!")
//        }
//        body {
//            div {
//                +"Hello world"
//            }
//            div {
//                id = "root"
//            }
//            script(src = "/static/space-ql.js") {}
//        }
//    }
//
    @Bean
    open fun htmlRouter(
        @Value("classpath:/index.html") html: Resource?
    ): RouterFunction<ServerResponse?>? {
        return route(
            GET("/")
        ) { ok().contentType(MediaType.TEXT_HTML).bodyValue(html!!) }
    }

}