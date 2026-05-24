package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    launch {
        doWorld()
    }

    println("hello")
}

// suspend 可以顶一个挂起函数，但是必须放在协程上下文中调用（比如runBlocking中）
suspend fun doWorld() {
    delay(1000L)
    println("world")
}