package me.javier.application

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BasicController(val basicService: BasicService) {

    @GetMapping("/test")
    fun test() : String {
        return basicService.getTitle()
    }
}