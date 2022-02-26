package application.controller

import graphql.ExecutionResult
import graphql.GraphQL
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BasicController(
    val graphQL: GraphQL
) {

    @PostMapping("/test")
    fun test(@RequestBody query:String) : ResponseEntity<ExecutionResult> {
        val execute = graphQL.execute(query)
        return ResponseEntity<ExecutionResult>(execute, HttpStatus.OK)
    }
}