import java.util.Date

sealed class Level {
    object Help : Level() {
        val name = "Help"
    }

    data class Easy(val id: String, val name: String) : Level()
    data class Medium(val id: String, val name: String) : Level()
    data class Hard(val id: String, val name: String, val multiplier: Float) : Level()
}

// 扩展function
fun Level.Medium.printInfo() {
    println("Medium class: $id")
}

// 扩展properties  这种扩展的不能直接在后面写=多少，必须通过get定义来扩展
val Level.Medium.info: String
    get() = "asdas"

fun main() {
    val level1 = Level.Medium("id1", "name1")
    if (level1 is Level.Medium) {
        level1.printInfo()
        println("Level.Medium info: ${level1.info}")
    }
}