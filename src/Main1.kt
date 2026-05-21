

val name = "Kide"
var greeting: String? = null

fun sayHello(greeting:String, name:String) = println("$greeting, $name!")

fun main() {
    greeting = "Hello"
    val greeting2 = if (greeting != null) greeting  else "Hi"
    /**
    when (greeting) {
        null -> println("Hi")
        else -> println(greeting)
    }
    */
    println(greeting2)
    println(name)
    sayHello("Hello", "World")
}