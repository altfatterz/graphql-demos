package com.example.bookskotlin

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*
import kotlin.collections.HashSet

// Here we don’t use data classes with val properties because JPA is not designed to work with immutable classes
// or the methods generated automatically by data classes.

// If you are using other Spring Data flavor, most of them are designed to support such constructs
// so you should use classes like 'data class User(val login: String, …​)'
// when using Spring Data MongoDB, Spring Data JDBC, etc.


// N+1 problem discussion
// https://stackoverflow.com/questions/49192255/spring-data-findall-does-not-fetch-eagerly

@Entity
@NamedEntityGraph(name = "books", attributeNodes = [
    NamedAttributeNode("author"),
    NamedAttributeNode("reviews")])
class Book(
        var title: String,

        @Column(name = "page_count")
        var pageCount: Int,

        @ManyToOne(fetch = FetchType.LAZY)
        var author: Author,

        @OneToMany
        @JoinColumn(name = "book_id")
        var reviews: MutableSet<Review> = HashSet(),

        @Id @GeneratedValue(generator = "UUID") @Type(type = "uuid-char") var id: UUID? = null
) {
    fun addReview(review: Review) {
        reviews.add(review)
    }
}

@Entity
class Author(
        @Column(name = "first_name")
        var firstName: String,

        @Column(name = "last_name")
        var lastname: String,

        @Id @GeneratedValue(generator = "UUID") @Type(type = "uuid-char") var id: UUID? = null)

@Entity
class Review(

        var stars: Int,

        var comment: String,

        @Id @GeneratedValue(generator = "UUID") @Type(type = "uuid-char") var id: UUID? = null
)

