package io.github.pshevche.spockk

import io.github.pshevche.spockk.lang.and
import io.github.pshevche.spockk.lang.given
import io.github.pshevche.spockk.lang.then
import io.github.pshevche.spockk.lang.`when`

class MyFirstSpecification {

    fun `adding an element to a list`() {
        given
        val myList = mutableListOf<Int>()

        `when`
        myList.add(1)

        and
        myList.add(2)

        then
        assert(myList.size == 2)
    }

}
