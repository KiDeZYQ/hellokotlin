package coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.time.withTimeout
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull

fun main() {
    // cancelTest()
    // cancelTest2()
    // noCancelFun()
    // timeout()
    timeoutAndResource()
    println(acquired)
}

fun cancelTest() = runBlocking {
    val job = launch {
        repeat(1000) { i ->
            println("job: I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L)
    println("main: I'm tired of waiting!")
    // job.cancel()
    // job.join()
    // 这个函数类似于分别调用cancel和join
    job.cancelAndJoin()
    println("main: Now I can quit.")
}

/**
 * 协程的取消是 协作 的。一段协程代码必须协作才能被取消。 所有 kotlinx.coroutines 中的挂起函数都是 可被取消的 。它们检查协程的取消， 并在取消时抛出 CancellationException。
 * 然而，如果协程正在执行计算任务，并且没有检查取消的话，那么它是不能被取消的
 * 这里job里面没有执行delay函数，一直占用cpu，会导致无法检测到协程取消
 */
fun cancelTest2() = runBlocking {
    var startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        // 一个执行计算的循环，只是为了占用 CPU
        // 可以用isActive来检测协程是否已经取消，这时不需要挂起函数也能检测到cancel而立马取消执行
        // while(i < 10)
        try {
            while(isActive) {
              if (System.currentTimeMillis() >= nextPrintTime) {
                  /**
                   * delay() is a cooperative suspend function. Under the hood, whenever a coroutine is canceled, delay()
                   * notices the cancellation state and throws a CancellationException to stop the execution flow
                   * 挂起函数能够检测取消
                   */

                      println("job: I'm sleeping ${i++} ...")
                  nextPrintTime += 500L
              }
            }
        } catch (e: Exception) {
            // 没有挂起函数，不会抛出CancellationException, 所以这里catch不到
            println("error: $e" )
        }
        finally {
            // finally中释放资源
            println("job: I'm running finally")
        }
    }
    delay(1300L)
    println("main: I'm tired of waiting!")
    job.cancelAndJoin()
    println("main: Now I can quit.")
}

fun noCancelFun() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            withContext(NonCancellable) {
                println("job: I'm running finally")
                // 如果不加withContext这行代码，调用delay挂起被取消的协程也会抛出CancellationException
                delay(1000L)
                println("job: And I've just delayed for 1 sec because I'm non-cancellable")
            }
        }
    }

    delay(1300L) // 延迟一段时间
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // 取消该作业并等待它结束
    println("main: Now I can quit.")
}

fun timeout() = runBlocking {
    val result = withTimeoutOrNull(1300L) {
        repeat(1000) {
                i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
        "Done"
    }
    println(result)

    withTimeout(1300L) {
        repeat(1000) {
            i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }

}

var acquired = 0

class Resource {
    init {
        acquired++
    }
    fun close() {
        acquired--
    }
}

fun timeoutAndResource() = runBlocking {
    repeat(10_000) { // Launch 10K coroutines
        launch {
            /**
             * 注意withTimeout和内部执行的代码是异步的，而且如果内部计时器一到，
             * 会立马中断内部代码块的执行并且抛出TimeoutCancellationException
             * 所以resource可能根本未执行，至于最后为什么close的时候不用
             * resource?.close 是因为如果timeout抛出异常了，那么也不会执行到这一句，
             * 也就是不会导致程序crash
             */
            val resource = withTimeout(60) { // Timeout of 60 ms
                delay(50) // Delay for 50 ms
                Resource() // Acquire a resource and return it from withTimeout block
            }
            resource.close() // Release the resource
        }
    }
}

fun safeTimeoutResourceClose() = runBlocking {
    repeat(10_000) { // Launch 10K coroutines
        launch {
            /**
             * 注意withTimeout和内部执行的代码是异步的，而且如果内部计时器一到，
             * 会立马中断内部代码块的执行并且抛出TimeoutCancellationException
             * 所以resource可能根本未执行，至于最后为什么close的时候不用
             * resource?.close 是因为如果timeout抛出异常了，那么也不会执行到这一句，
             * 也就是不会导致程序crash
             */
            var resource: Resource? = null
            try {
                withTimeout(60) { // Timeout of 60 ms
                    delay(50) // Delay for 50 ms
                    resource = Resource() // Acquire a resource and return it from withTimeout block
                }
            } finally {
                // 通过finally优雅关闭资源
                resource?.close() // Release the resource
            }
        }
    }
}