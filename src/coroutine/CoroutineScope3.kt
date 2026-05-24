package coroutine

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    main2
}

val main2 = runBlocking { // 仅作为测试容器，别在意
    println("--- 测试 coroutineScope ---")

    // 1. 启动任务 A
    launch {
        doTaskA()
    }

    // 2. 启动任务 B
    launch {
        repeat(5) { i ->
            println("任务 B 正在主线程欢快地运行... ($i)")
            delay(200)
        }
    }
}

suspend fun doTaskA() {
    // coroutineScope 是挂起函数
    /**
     * coroutineScope和runBlocking都可以定义协程上下文，不同的是
     * runBlocking会挂起主线程等待scope中的所有协程执行完才继续执行
     * coroutineScope 是“让路”：它只是让当前协程暂停（Suspend），
     * 但它会把主线程立刻释放（Release）出来，让主线程可以去跑别的协程或处理 UI 交互
     */
    coroutineScope {
        println("任务 A 在 coroutineScope 开始（在 ${Thread.currentThread().name}）")

        delay(1000) // 🧠 重点！协程挂起了，但它把 main 线程“释放”了！

        println("任务 A 在 coroutineScope 结束")
    }
}
