package org.jlleitschuh.pmdkotlin.cpd

/**
 * Project: pmd-kotlin
 * Created by Markus Pöschl
 */
class DuplCode1 {

    fun foobar() {
        val dummyList = mutableListOf<Int>()

        val value1 = 1 + 42
        dummyList.add(value1)

        val value2 = 2 + 42
        dummyList.add(value2)

        val value3 = 2 + 42
        dummyList.add(value3)
    }
}

class DuplCode2 {

    fun foobar() {
        val dummyList = mutableListOf<Int>()

        val value1 = 1 + 42
        dummyList.add(value1)

        val value2 = 2 + 42
        dummyList.add(value2)

        val value3 = 2 + 42
        dummyList.add(value3)
    }
}