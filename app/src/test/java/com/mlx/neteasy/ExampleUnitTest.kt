package com.mlx.neteasy

import android.graphics.Point
import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        var point=com.mlx.neteasy.Test()
        var list= mutableListOf<com.mlx.neteasy.Test>()
        for (i in 0..10){

            list.add(point.copy(i,i))
        }
        list.forEach {
            println(it)
        }
    }
}