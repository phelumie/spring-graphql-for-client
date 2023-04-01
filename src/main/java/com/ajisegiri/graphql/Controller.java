package com.ajisegiri.graphql;


import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class Controller {
    private final HttpGraphQlClient httpGraphQlClient;



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

}
