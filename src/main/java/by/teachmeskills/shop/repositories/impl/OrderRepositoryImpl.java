package by.teachmeskills.shop.repositories.impl;

import by.teachmeskills.shop.domain.Order;
import by.teachmeskills.shop.repositories.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class OrderRepositoryImpl implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order create(Order entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<Order> read() {
        return entityManager.createQuery("select o Order o ", Order.class).getResultList();
    }

    @Override
    public Order update(Order entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(int id) {
        Order order = entityManager.find(Order.class, id);
        entityManager.remove(order);
    }

    @Override
    public Order findById(int id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public List<Order> findByDate(LocalDateTime date) {
        return entityManager.createQuery("select o from Order o where o.created_at=:created_at", Order.class)
                .setParameter("created_at", Timestamp.valueOf(date)).getResultList();
    }

    @Override
    public List<Order> findByUserId(int id) {
        return entityManager.createQuery("select o from Order o where o.user.id=:user_id", Order.class)
                .setParameter("user_id", id).getResultList();
    }
}