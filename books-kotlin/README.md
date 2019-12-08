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
    "bookId": 4,
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

### Query:

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
  "id":"4"
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