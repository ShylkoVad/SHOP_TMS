package by.teachmeskills.shop.repositories;

import by.teachmeskills.shop.domain.BaseEntity;

import java.util.List;

public interface BaseRepository<T extends BaseEntity> {

    T create(T entity);

    List<T> read();

    T update(T entity);

    void delete(int id);
}
