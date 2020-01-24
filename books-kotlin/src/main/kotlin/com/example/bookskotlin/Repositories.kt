package com.example.bookskotlin

import org.springframework.data.repository.Repository
import java.util.*

interface BookRepository : Repository<Book, UUID> {
    fun save(book: Book): Book
    fun findById(id: UUID): Book?
    fun findAll() : List<Book>
}

interface AuthorRepository : Repository<Author, UUID> {
    fun save(author: Author): Author
}

interface ReviewRepository : Repository<Review, UUID> {
    fun save(review: Review): Review
}


