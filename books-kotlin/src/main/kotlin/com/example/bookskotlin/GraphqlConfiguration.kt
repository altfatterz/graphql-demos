package com.example.bookskotlin

import graphql.GraphQL
import graphql.scalars.ExtendedScalars
import graphql.schema.GraphQLSchema
import graphql.schema.idl.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource

@Configuration
class GraphqlConfiguration(private val graphqlDataFetchers: GraphqlDataFetchers,
                           @Value("classpath:graphql/*.graphqls")
                           private val resources: Array<Resource>) {

    @Bean
    fun graphQL(): GraphQL? {
        return GraphQL.newGraphQL(buildGraphQLSchema()).build()
    }

    private fun buildGraphQLSchema(): GraphQLSchema {
        val schemaParser = SchemaParser()
        val typeDefinitionRegistry = TypeDefinitionRegistry()
        for (resource in resources) {
            typeDefinitionRegistry.merge(schemaParser.parse(resource.file))
        }
        val schemaGenerator = SchemaGenerator()
        return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, buildRuntimeWiring())
    }

    private fun buildRuntimeWiring(): RuntimeWiring {
        return RuntimeWiring.newRuntimeWiring()
                .scalar(ExtendedScalars.Date)
                .type(TypeRuntimeWiring.newTypeWiring("Query")
                        .dataFetcher("bookById", graphqlDataFetchers.bookByIdDataFetcher)
                        .dataFetcher("books", graphqlDataFetchers.books))
                .type(TypeRuntimeWiring.newTypeWiring("Mutation")
                        .dataFetcher("addReview", graphqlDataFetchers.addReview()))
                .build()
    }

}