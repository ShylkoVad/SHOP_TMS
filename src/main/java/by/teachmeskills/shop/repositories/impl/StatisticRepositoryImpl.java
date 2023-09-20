package by.teachmeskills.shop.repositories.impl;

import by.teachmeskills.shop.domain.StatisticEntity;
import by.teachmeskills.shop.repositories.StatisticRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<StatisticEntity> read() {
        return entityManager.createQuery("select s from S s ", StatisticEntity.class).getResultList();
    }

    @Override
    public StatisticEntity update(StatisticEntity entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(int id) {
        StatisticEntity statistic = entityManager.find(StatisticEntity.class, id);
        entityManager.remove(statistic);
    }
}
