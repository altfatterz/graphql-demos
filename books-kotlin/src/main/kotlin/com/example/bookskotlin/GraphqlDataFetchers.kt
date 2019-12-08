package com.example.bookskotlin

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class GraphqlDataFetchers(private val bookRepository: BookRepository,
                          private val reviewRepository: ReviewRepository) {

    private val logger = LoggerFactory.getLogger(GraphqlDataFetchers::class.java)

    val bookByIdDataFetcher: DataFetcher<Book?>
        get() = DataFetcher { env: DataFetchingEnvironment ->
            val id = env.getArgument<Long>("id")
            logger.info("fetching book with id: {}", id)
            bookRepository.findById(id)
        }

    fun addReview(): DataFetcher<Boolean> {
        return DataFetcher { env: DataFetchingEnvironment ->
            val input = env.getArgument<HashMap<String, Any>>("input")
            val book = bookRepository.findById(input["bookId"] as Long)
            if (book == null) false

            val review = Review(input["stars"] as Int, input["comment"] as String)
            reviewRepository.save(review)

            book!!.addReview(review)
            bookRepository.save(book!!)
            true
        }
    }

}
