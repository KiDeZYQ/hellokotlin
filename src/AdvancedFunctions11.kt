// 函数可以传入另一个函数作为参数
fun printFilteredStrings(list: List<String>, predicate: (String) -> Boolean) {
    list.forEach {
        if (predicate(it)) {
            println(it)
        }
    }
}

fun printFilteredStrings2(list: List<String>, predicate: ((String) -> Boolean)?) {
    list.forEach {
        // 注意不能像上面一样直接调用predicate(it) 因为predicate函数可能为空
        if (predicate?.invoke(it) == true) {
            println(it)
        }
    }
}

// 可以定义一个变量作为函数
val predicate: (String) -> Boolean = {
    it.startsWith("j")
}

// 也可以定义一个函数
fun getPrintPredicate(): (String) -> Boolean {
    return {it.startsWith("j")}
}


fun main() {
    val list = listOf("java", "kotlin", "python", "go", "javascript")
    printFilteredStrings(list, {
        it.startsWith("j")
    })
    // 这个不允许，因为函数未定义为可以为空
    // printFilteredStrings(list, null)
    printFilteredStrings(list, predicate)
    printFilteredStrings(list, getPrintPredicate())
    printFilteredStrings2(list, null)

    // 也可以直接使用list的各种函数，同java一样
    list.filterNotNull()
        .filter { it.startsWith("j") }
        .associate { it to it.length }
        .forEach { println("${it.key} : ${it.value}") }
}