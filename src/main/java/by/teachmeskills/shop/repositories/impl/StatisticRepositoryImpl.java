package by.teachmeskills.shop.repositories.impl;

import by.teachmeskills.shop.domain.StatisticEntity;
import by.teachmeskills.shop.repositories.StatisticRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Transactional
@Repository
public class StatisticRepositoryImpl implements StatisticRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public StatisticEntity create(StatisticEntity entity) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(entity);
        return entity;
    }

    @Override
    public List<StatisticEntity> read() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("select s from S s ", StatisticEntity.class).list();
    }

    @Override
    public StatisticEntity update(StatisticEntity entity) {
        Session session = entityManager.unwrap(Session.class);
        return session.merge(entity);
    }

    @Override
    public void delete(int id) {
        Session session = entityManager.unwrap(Session.class);
        StatisticEntity statistic = session.get(StatisticEntity.class, id);
        session.remove(statistic);
    }
}
