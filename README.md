# spring-graphql-for-client-project

This is a project showing how we can consume graphql endpoints using `spring-boot-starter-graphql` dependency.
<br/>
Before we begin to dive deep, there are three(3) ways of consuming graphql 
using the `spring-boot-starter-graphql` dependency, namely;
* [HttpGraphQlClient](https://docs.spring.io/spring-graphql/docs/current-SNAPSHOT/reference/html/#client.httpgraphqlclient)

* [WebSocketGraphQlClient](https://docs.spring.io/spring-graphql/docs/current-SNAPSHOT/reference/html/#client.websocketgraphqlclient)

* [RSocketGraphQlClient](https://docs.spring.io/spring-graphql/docs/current-SNAPSHOT/reference/html/#client.rsocketgraphqlclient)

In this project we will be using the HttpGraphQlClient extension. To know more about the dependency please check [here](https://docs.spring.io/spring-graphql/docs/current-SNAPSHOT/reference/html/).

# Dependencies
```
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-graphql</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webflux</artifactId>
		</dependency>

		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>io.projectreactor</groupId>
			<artifactId>reactor-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.graphql</groupId>
			<artifactId>spring-graphql-test</artifactId>
			<scope>test</scope>
		</dependency>
```
I use lombok so as to reduce boilerplate code,if you don't please feel free to remove it .

**Note**: The `spring-boot-starter-graphql` uses WebClient to execute GraphQL requests,so the webflux dependency has to be added.

# Configurations
```
@Bean
    public HttpGraphQlClient httpGraphQlClient(){
        return HttpGraphQlClient.builder().url("https://countries.trevorblades.com/graphql")
                .build();
    }

```

The url which is `https://countries.trevorblades.com/graphql` is a public GraphQL API for information about countries, continents, and languages.
To know more about the API check [here](https://studio.apollographql.com/public/countries/variant/current/home).

# Testing
Our API only supports `GET` requests(in REST),there will be no mutation.

The method below queries our api and requests for name,capital and current field.
```

    @GetMapping("countries")
    public Mono<Object> getCountries(){
        var document= """
                query{
                  countries{
                    name
                    capital
                    currency
                    
                  }
                }
                """;
        return httpGraphQlClient.document(document).retrieve("countries")
                .toEntity(Object.class);

    }

```

In scenarios where we have to pass in parameters and make it dynamic,the below code shows that
```
    @GetMapping("continent")
    public Mono<Object> getContinent(@RequestParam("code") String code){
        var document= """
              query continentWithCode($code: ID!) {
                    continent(code: $code){
                        name
                    }
              }
                """;

        return httpGraphQlClient.document(document)
                .variable("code",code)
                .retrieve("continent")
                .toEntity(Object.class);
    }

```

In case we need to pass in headers we can:

```
HttpGraphQlClient.builder(webClient)
                        .url(uri)
                        .headers(header->{
                            header.set("Content-Type","application/json");
                        }).build().document(document)
                .retrieve("continent")
                .toEntity(Object.class);

       
```
The webclient can be configured and  injected:

```
    @Bean
    public WebClient webclient(){
        return WebClient.builder().build();
    }
```
