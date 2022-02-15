import kotlinx.css.*
import styled.StyleSheet

// This creates a stylesheet named as the class that allows us to use the
// different 'val's as reusable CSS
object WelcomeStyles : StyleSheet("WelcomeStyles", isStatic = true) {
    val textContainer by css {
        padding(5.px)

        backgroundColor = rgb(8, 97, 22)
        color = rgb(56, 246, 137)
    }

    val textInput by css {
        margin(vertical = 5.px)

        fontSize = 14.px
    }
} 
