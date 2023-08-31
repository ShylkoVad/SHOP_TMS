package by.teachmeskills.shop.repositories;

import by.teachmeskills.shop.domain.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public interface BaseRepository<T extends BaseEntity> {
    Logger log = LoggerFactory.getLogger(BaseRepository.class);
    ConnectionPool connectionPool = ConnectionPool.getInstance();

    T create(T entity);

    List<T> read();

    T update(T entity);

    void delete(int id);
}
