package br.edu.example.api.core.generic.mapper;

public interface OutputMapper<M, E> {
    E toEntity(M model);
}
