package com.zoubelu.fartmeter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FartmeterApplication

fun main(args: Array<String>) {
    runApplication<FartmeterApplication>(*args)
}
