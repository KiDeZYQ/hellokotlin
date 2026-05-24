package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * runBlocking也是coroutine scope builder，将main函数和runBlocking里面的代码逻辑串联起来，外部的main线程必须等待
 * runBlocking中的协程都执行完成才退出
 */
fun main()  {
    /**
     * 输出
     * start
     * hello
     * world
     * finish
     */
    println("start ")
    runBlocking {
        /**
         * launch是一个coroutine builder
         * It launches a new coroutine concurrently with the rest of the code,
         * which continues to work independently. That's why Hello has been printed first.
         * launch外面如果没有runBlocking定义coroutine scope的话则会报错
         */
        launch {
            // delay类似于java的sleep函数
            delay(1000L)
            println("world")
        }

        println("hello")
    }

    println("finish")
}