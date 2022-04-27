package application.controller

import application.BasicService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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

    // Endpoint for manual testing
    @GetMapping("/search/{text}/{page}")
    fun pub(@PathVariable text: String, @PathVariable page: Int) : ResponseEntity<List<String?>> {
        val pageSize = 10
        val offset = (page - 1) * pageSize

        val lista = basicService.search(text, pageSize, offset)

        val namesList = lista.map{ it.data.fileName}

        return ResponseEntity(namesList, HttpStatus.OK)
    }
}