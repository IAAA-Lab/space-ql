package application.controller

import application.BasicService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BasicController(
    private val basicService: BasicService
) {

//    @PostMapping("/test")
//    fun test(@RequestBody query:String) : ResponseEntity<ExecutionResult> {
//        val execute = graphQL.execute(query)
//        return ResponseEntity<ExecutionResult>(execute, HttpStatus.OK)
//    }

    @GetMapping("/publish")
    fun pub() : ResponseEntity<Void> {
        basicService.loadMD()
        return ResponseEntity(HttpStatus.OK)
    }
}