fun sayHello(greeting: String, vararg names: String) {
    names.forEach {
        println("$greeting $it")
    }
}

fun main() {
    val interestingThings = arrayOf("Kotlin", "Java", "Python", "Go")
    // * 表示将数组拆成一个一个的字符串
    sayHello("Hi", *interestingThings)
    sayHello(greeting = "Go", *interestingThings)
    // 1.8之前vararg传数组时必须加 *
    sayHello(names = *interestingThings, greeting = "Hello")
    // 带命名参数的现代调用（Kotlin 1.9+）：当你明确写出 names = interestingThings 时，编译器已经 100% 明确知道你正在给一个 vararg 字段赋值。为了消除 * 运算符带来的轻微性能开销（因为 * 在底层会复制一份数组）并简化语法，现代 Kotlin 允许你直接把整个数组赋值给它
    sayHello(names = interestingThings, greeting = "Hello")
}
