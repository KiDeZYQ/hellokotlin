package coroutine

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // 假设我们在这里做了些有用的事
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // 假设我们在这里也做了一些有用的事
    return 29
}

fun main() {
    // sequenceExe()
    // asyncExe()
    // lazyAsyncExe()
    // asyncFun()
    // structAsync()
    errorStructAsync()
}

fun sequenceExe() = runBlocking {
    val time = measureTimeMillis {
        val one = doSomethingUsefulOne()
        val two = doSomethingUsefulTwo()
        println("The answer is ${one + two}")
    }
    println("Completed in $time ms")
}

fun asyncExe() = runBlocking {
    val time = measureTimeMillis {
        // async方法可以异步执行，类似于launch， 启动一个新的协程
        val one = async {doSomethingUsefulOne()}
        val two = async {doSomethingUsefulTwo()}
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

fun lazyAsyncExe() = runBlocking {
    val time = measureTimeMillis {
        val one = async(start = CoroutineStart.LAZY) {doSomethingUsefulOne()}
        val two = async(start = CoroutineStart.LAZY) {doSomethingUsefulTwo()}
        // 调用start手动触发执行
        one.start()
        two.start()
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

// 可以自定义async风格的封装函数
// somethingUsefulOneAsync 函数的返回值类型是 Deferred<Int>
/**
 * In Kotlin, @OptIn(DelicateCoroutinesApi::class) is an annotation that tells the compiler:
 * "I know exactly what I am doing, I understand the risks,
 * and I explicitly choose to use a 'delicate' (dangerous) Coroutine API."
 * 这里
 */
@OptIn(DelicateCoroutinesApi::class)
fun somethingUsefulOneAsync() = GlobalScope.async {
    doSomethingUsefulOne()
}

// somethingUsefulTwoAsync 函数的返回值类型是 Deferred<Int>
@OptIn(DelicateCoroutinesApi::class)
fun somethingUsefulTwoAsync() = GlobalScope.async {
    doSomethingUsefulTwo()
}

fun asyncFun() {
    var time = measureTimeMillis {
        val one = somethingUsefulOneAsync()
        val two = somethingUsefulTwoAsync()
        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("Completed in $time ms")
}

// 定义结构化的异步函数
suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}


fun structAsync() = runBlocking {
    val time = measureTimeMillis {
        println("The answer is ${concurrentSum()}")
    }
    println("Completed in $time ms")
}


suspend fun failedConcurrentSum(): Int = coroutineScope {
    val one = async<Int> {
        try {
            delay(Long.MAX_VALUE) // 模拟一个长时间的运算
            42
        } finally {
            println("First child was cancelled")
        }
    }
    val two = async<Int> {
        println("Second child throws an exception")
        throw ArithmeticException()
    }
    // two抛出了异常 同一作用域的one也会被取消
    one.await() + two.await()
}

/**
 * 最终输出：
 * Second child throws an exception
 * First child was cancelled
 * Computation failed with ArithmeticException
 */
fun errorStructAsync() = runBlocking {
    try {
        failedConcurrentSum()
    } catch (e: ArithmeticException) {
        println("Computation failed with ArithmeticException")
    }
}