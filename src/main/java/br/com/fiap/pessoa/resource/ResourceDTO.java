package br.com.fiap.pessoa.resource;

import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface ResourceDTO<Request, Response> {


    //Faça o find All by example

    ResponseEntity<Response> findById(Long id);

    ResponseEntity<Response> save(Request r);
}