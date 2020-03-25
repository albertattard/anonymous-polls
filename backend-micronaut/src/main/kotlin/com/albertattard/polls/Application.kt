package com.albertattard.polls

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("com.albertattard.polls")
                .mainClass(Application.javaClass)
                .start()
    }
}
