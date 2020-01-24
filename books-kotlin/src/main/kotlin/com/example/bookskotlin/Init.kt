package com.example.bookskotlin

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import java.util.*

@Configuration
class Init {

    @Bean
    fun bookDemo(bookRepository: BookRepository, authorRepository: AuthorRepository): CommandLineRunner? {

        return CommandLineRunner { _: Array<String?>? ->
            val joanne = Author("Joanne", "Rowling")
            val herman = Author("Herman", "Melville")
            val anne = Author("Anne", "Rice")
            authorRepository.save(joanne)
            authorRepository.save(herman)
            authorRepository.save(anne)
            val book1 = Book("Harry Potter and the Philosopher's Stone", 223, LocalDate.parse("1987-05-11"), joanne)
            val book2 = Book("Moby Dick", 635, LocalDate.parse("1985-06-12"), herman)
            val book3 = Book("Interview with the vampire", 371, LocalDate.parse("1993-06-12"), anne)
            bookRepository.save(book1)
            bookRepository.save(book2)
            bookRepository.save(book3)
        }
    }

}