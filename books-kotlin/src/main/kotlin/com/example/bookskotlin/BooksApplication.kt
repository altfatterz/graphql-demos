package com.example.bookskotlin

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class BooksKotlinApplication

fun main(args: Array<String>) {
    runApplication<BooksKotlinApplication>(*args)
}

