package by.teachmeskills.shop.repositories.impl;

import by.teachmeskills.shop.domain.Product;
import by.teachmeskills.shop.repositories.ProductRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private static String GET_ALL_PRODUCTS_FOR_ORDER = "SELECT * FROM product p " +
            "JOIN order_lists ol ON p.id = ol.productId " +
            "JOIN orders o ON o.id = ol.orderId WHERE o.id = ?";
    private static String GET_PRODUCT_QUANTITY = "SELECT quantity FROM order_lists ol " +
            "JOIN orders o ON ol.orderId = o.id WHERE orderId = ?";


    private static final String ADD_PRODUCT_QUERY = "INSERT INTO products (name, description, price, categoryId) VALUES (?, ?, ?, ?)";
    private static final String GET_ALL_PRODUCTS_QUERY = "SELECT * FROM products";
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM products WHERE id = ?";
    private static final String GET_PRODUCT_BY_ID_QUERY = "SELECT * FROM products WHERE id = ?";
    private static final String GET_PRODUCT_BY_CATEGORY_ID_QUERY = "SELECT * FROM products WHERE categoryId = ?";
    private static final String UPDATE_PRODUCT_QUERY = "UPDATE products SET name = ?, description = ?, price = ?" +
            " categoryid = ?,  WHERE id = ?";
    private static final String GET_PRODUCTS_SEARCHED = "SELECT * FROM products WHERE name LIKE ? OR description LIKE ?";


    @Override
    public Product create(Product entity) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT_QUERY);

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setDouble(3, entity.getPrice());
            preparedStatement.setInt(4, entity.getCategoryId());
            preparedStatement.execute();

            preparedStatement.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            connectionPool.closeConnection(connection);
        }
        return entity;
    }

    @Override
    public List<Product> read() {
        List<Product> products = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCTS_QUERY);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                products.add(Product.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .description(resultSet.getString(3))
                        .price(resultSet.getDouble(4))
                        .categoryId(resultSet.getInt(5))
                        .build()
                );
            }
            resultSet.close();

            preparedStatement.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            connectionPool.closeConnection(connection);
        }
        return products;
    }

    @Override
    public Product update(Product entity) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_QUERY);

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setDouble(3, entity.getPrice());
            preparedStatement.setInt(4, entity.getCategoryId());
            preparedStatement.executeUpdate();
            entity = findById(entity.getId());
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            connectionPool.closeConnection(connection);
        }
        return entity;
    }

    @Override
    public void delete(int id) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_QUERY);
            preparedStatement.setInt(1, id);

            preparedStatement.execute();

            preparedStatement.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public Product findById(int id) {
        Product product = null;
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_ID_QUERY);

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product = Product.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .description(resultSet.getString(3))
                        .price(resultSet.getDouble(4))
                        .categoryId(resultSet.getInt(5))
                        .build();
            }
            resultSet.close();

            preparedStatement.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            connectionPool.closeConnection(connection);
        }
        return product;
    }

    @Override
    public List<Product> findByCategoryId(int categoryId) {
        List<Product> products = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_CATEGORY_ID_QUERY);

            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                products.add(Product.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .description(resultSet.getString(3))
                        .price(resultSet.getDouble(4))
                        .categoryId(resultSet.getInt(5))
                        .build());
            }
            resultSet.close();

            preparedStatement.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            connectionPool.closeConnection(connection);
        }
        return products;
    }

    @Override
    public List<Product> findBySearchParameter(String searchString) {
        List<Product> products = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCTS_SEARCHED);
            preparedStatement.setString(1, "%" + searchString + "%");
            preparedStatement.setString(2, "%" + searchString + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                products.add(Product.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .description(resultSet.getString(3))
                        .price(resultSet.getDouble(4))
                        .categoryId(resultSet.getInt(5))
                        .build());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            connectionPool.closeConnection(connection);
        }
        return products;
    }
}
