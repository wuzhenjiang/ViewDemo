package com.okay.demo

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
     fun addition_isCorrect() {
//        look{
//            println("haha before")
//            return
//            println("haha end")
//        }
        runRunnable{
            println("aaa")
            return@runRunnable
            println("bbb")
        }
    }

   inline fun runRunnable(crossinline block:()->Unit){
       println("ccc")
        val runnable= Runnable {
            block()
        }
       println("ddd")
        runnable.run()
    }
}