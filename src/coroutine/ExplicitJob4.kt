package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

val explicitJob = runBlocking {
    // launch其实返回的是一个job
    val job = launch {
        delay(1000L)
        println("world")
    }
    println("hello")
    // 可以通过join方法使得主线程等待协程执行完成
    job.join()
    println("world")
}