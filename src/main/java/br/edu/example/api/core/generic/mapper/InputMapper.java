package br.edu.example.api.core.generic.mapper;

public interface InputMapper<M, E> {
    M toModel(E entity);
}
