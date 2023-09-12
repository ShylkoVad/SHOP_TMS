package by.teachmeskills.shop.repositories.impl;

import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.repositories.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional

public class ProductRepositoryImpl implements ProductRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Product create(Product entity) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(entity);
        return entity;
    }

    @Override
    public List<Product> read() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("select p from Product p ", Product.class).list();
    }

    @Override
    public Product update(Product entity) {
        Session session = entityManager.unwrap(Session.class);
        return session.merge(entity);
    }

    @Override
    public void delete(int id) {
        Session session = entityManager.unwrap(Session.class);
        Product product = session.get(Product.class, id);
        session.remove(product);
    }

    @Override
    public Product findById(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Product.class, id);
    }

    @Override
    public List<Product> findByCategoryId(int categoryId) {
        Session session = entityManager.unwrap(Session.class);
        Query<Product> query = session.createQuery("select p from Product p where p.category.id=:categoryId", Product.class);
        query.setParameter("categoryId", categoryId);
        return query.list();
    }

    @Override
    public List<Product> findBySearchParameter(String parameter) {
        Session session = entityManager.unwrap(Session.class);
        Query<Product> query = session.createQuery("select p from Product p where lower(p.name) like lower(:parameter) " +
                "or lower(p.description) like lower(:parameter) order by name", Product.class);
        query.setParameter("parameter", "%" + parameter.toLowerCase() + "%");
        return query.list();
    }
}
