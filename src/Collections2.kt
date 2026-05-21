fun main() {
    array()
    list()
    map()
    // 默认情况下，kotlin的集合都是不可变的，如果要使其可变，可以使用mutable
}

fun map() {
    println("-------map basic test---------")

    val interestingThings = mapOf(1 to "one", 2 to "two", 3 to "three")
    // interestingThings = mutableMapOf()
    interestingThings.forEach { key, value -> println("$key -> $value") }
}

fun list() {
    println("-------list basic test---------")
    val interestingThings = listOf("Kotlin", "Java", "Python", "Go")
    // interestingThings = mutableListOf()
    println(interestingThings.size)
    println(interestingThings[0])
    println(interestingThings.get(0))

    println("-------list loop test---------")
    // 循环
    for (interestingThing in interestingThings) {
        println(interestingThing)
    }

    interestingThings.forEach(::println)

    interestingThings.forEach{
        println(it)
    }

    interestingThings.forEach { interestingThing ->
        println(interestingThing)
    }

    interestingThings.forEachIndexed { index, interestingThing ->
        println("$interestingThing is at index $index")
    }
}

fun array() {
    println("-------array basic test---------")
    val interestingThings = arrayOf("Kotlin", "Java", "Python", "Go")
    println(interestingThings.size)
    println(interestingThings[0])
    println(interestingThings.get(0))

    println("-------array loop test---------")
    // 循环
    for (interestingThing in interestingThings) {
        println(interestingThing)
    }

    interestingThings.forEach(::println)

    interestingThings.forEach{
        println(it)
    }

    interestingThings.forEach { interestingThing ->
        println(interestingThing)
    }

    interestingThings.forEachIndexed { index, interestingThing ->
        println("$interestingThing is at index $index")
    }
}