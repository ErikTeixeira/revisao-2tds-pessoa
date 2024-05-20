package br.com.fiap.pessoa.service;

import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;


/**
 * @param <Entity>   é uma classe que tem a anotação @Entity
 * @param <Request>  é um DTO de requisição
 * @param <Response> é um DTO de response
 */
public interface ServiceDTO<Entity, Request, Response> {


    Entity toEntity(Request r);

    Response toResponse(Entity e);

    List<Entity> findAll(Example<Entity> example);

    Optional<Entity> findById(Long id);

    Entity save(Entity e);

}
