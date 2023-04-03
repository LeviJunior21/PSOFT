package com.ufcg.psoft.mercadofacil.Repository;
import java.util.List;

public interface LoteRepository<T, ID> {
    T save(T lote);
    T find(Long id);

    List<T> findAll();
    T update(T lote);
    void delete(T lote);
    void deleteAll();
}