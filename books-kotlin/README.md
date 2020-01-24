### Queries:

#### books

```graphql
query {
  books {
    title  
  }
}
```

result

```json
{
  "data": {
    "books": [
      {
        "id": "98c4aa18-5182-4d26-89e7-b2b1145cee9b",
        "title": "Harry Potter and the Philosopher's Stone"
      },
      {
        "id": "784d271c-f533-4e9e-a3ea-e8df759ee157",
        "title": "Moby Dick"
      },
      {
        "id": "719eeafb-f306-49d6-ae3d-17f362c0fb64",
        "title": "Interview with the vampire"
      }
    ]
  }
}
```

check the logs for the created SQL query:

```bash
Hibernate: 
    select
        book0_.id as id1_1_,
        book0_.author_id as author_i4_1_,
        book0_.page_count as page_cou2_1_,
        book0_.title as title3_1_ 
    from
        book book0_
```

Querying also for books:

```graphql
query {
  books {
    title
    author {
      firstName
    }  
  }
}
```

In the logs we will have a query for the `books` table and as many queries for the `authors` table as many entries the `books` table has. This is the N+1 query problem which we would like to avoid. 

One ways is to write `@Query` annotation on the `findAll` method. Other way is to use `@NamedEntityGraph`

```java
@NamedEntityGraph(name = "books", attributeNodes = [
    NamedAttributeNode("author"),
    NamedAttributeNode("reviews")])
class Book
```  

and 

```java
@EntityGraph(value = "books")
fun findAll() : List<Book>
```

This way we get the following query:

```bash
Hibernate: 
    select
        book0_.id as id1_1_0_,
        reviews1_.id as id1_2_1_,
        author2_.id as id1_0_2_,
        book0_.author_id as author_i4_1_0_,
        book0_.page_count as page_cou2_1_0_,
        book0_.title as title3_1_0_,
        reviews1_.comment as comment2_2_1_,
        reviews1_.stars as stars3_2_1_,
        reviews1_.book_id as book_id4_2_0__,
        reviews1_.id as id1_2_0__,
        author2_.first_name as first_na2_0_2_,
        author2_.last_name as last_nam3_0_2_ 
    from
        book book0_ 
    left outer join
        review reviews1_ 
            on book0_.id=reviews1_.book_id 
    left outer join
        author author2_ 
            on book0_.author_id=author2_.id
```

The drawback is that the above query will executed even for a simple query like

```bash
query {
  books {
    title
  }
}
```

instead of 

```bash
Hibernate: 
    select
        book0_.id as id1_1_,
        book0_.author_id as author_i4_1_,
        book0_.page_count as page_cou2_1_,
        book0_.title as title3_1_ 
    from
        book book0_
```  

#### bookById

Here the `BookByIdQuery` is the `operationName` and via use also `variables`.  

```graphql
query BookByIdQuery($id: ID!) {
  bookById(id:$id) {
    title
    author{
      firstName
    }
    reviews {
      stars
      comment
    }
  }
}    
```

variables

```json
{
  "id":"98c4aa18-5182-4d26-89e7-b2b1145cee9b"
}
```

Result:

```json
{
  "data": {
    "bookById": {
      "title": "Harry Potter and the Philosopher's Stone",
      "author": {
        "firstName": "Joanne"
      },
      "reviews": [
        {
          "stars": 3,
          "comment": "So so!"
        }
      ]
    }
  }
}
```


### Mutation

```graphql
mutation AddReview($input: AddReviewInput!) {
  addReview(input:$input) 
}
```

Variables

```json
{
  "input":{
    "bookId": "5ec62cf5-c6fc-4165-80c1-3a7c8cd7b6d3",
    "stars": 3,
    "comment": "So so!"
  }
}
```

Result:

```json
{
  "data": {
    "addReview": true
  }
}
```

Boost the performance of your Spring Data JPA application

https://blog.ippon.tech/boost-the-performance-of-your-spring-data-jpa-application/

