package com.example.bookskotlin

import org.springframework.data.repository.Repository

interface BookRepository : Repository<Book, Long> {
    fun save(book: Book): Book
    fun findById(id: Long): Book?
}

interface AuthorRepository : Repository<Author, Long> {
    fun save(author: Author): Author
}

interface ReviewRepository : Repository<Review, Long> {
    fun save(review: Review): Review
}


