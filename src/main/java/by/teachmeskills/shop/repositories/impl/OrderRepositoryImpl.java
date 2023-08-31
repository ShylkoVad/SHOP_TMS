package by.teachmeskills.shop.repositories.impl;

import by.teachmeskills.shop.domain.Order;
import by.teachmeskills.shop.repositories.OrderRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private static final String ADD_ORDER_QUERY = "INSERT INTO orders (userId, createdAt, price) VALUES (?, ?, ?)";
    private static final String UPDATE_ORDER_QUERY = "UPDATE orders WHERE id = ?";
    private static final String GET_ALL_ORDERS_QUERY = "SELECT * FROM orders";
    private static final String GET_ALL_ORDERS_BY_USER_ID_QUERY = "SELECT * FROM orders WHERE userId = ?";
    private static final String GET_ORDER_BY_ID_QUERY = "SELECT * FROM orders WHERE id = ?";
    private static final String GET_ORDERS_BY_DATE_QUERY = "SELECT * FROM orders WHERE createAt = ?";
    private static final String DELETE_ORDER_QUERY = "UPDATE orders SET id = ?";

    @Override
    public Order create(Order entity) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_ORDER_QUERY);

            preparedStatement.setInt(1, entity.getUserId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(entity.getCreatedAt()));
            preparedStatement.setDouble(3, entity.getPrice());
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
    public List<Order> read() {
        List<Order> orders = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDERS_QUERY);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(Order.builder()
                        .id(resultSet.getInt(1))
                        .userId(resultSet.getInt(2))
                        .createdAt(resultSet.getTimestamp(3).toLocalDateTime())
                        .price(resultSet.getDouble(4))
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
        return orders;
    }

    @Override
    public Order update(Order entity) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_QUERY);

            preparedStatement.setInt(1, entity.getId());
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
    public void delete(int id) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER_QUERY);

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
    public Order findById(int id) {
        Order order = null;
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_BY_ID_QUERY);

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                order = Order.builder()
                        .id(resultSet.getInt(1))
                        .userId(resultSet.getInt(2))
                        .createdAt(resultSet.getTimestamp(3).toLocalDateTime())
                        .price(resultSet.getDouble(4))
                        .build();
            }
            resultSet.close();

            preparedStatement.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            connectionPool.closeConnection(connection);
        }
        return order;
    }

    @Override
    public List<Order> findByDate(LocalDateTime date) {
        List<Order> orders = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement psGet = connection.prepareStatement(GET_ORDERS_BY_DATE_QUERY);
            psGet.setTimestamp(1, Timestamp.valueOf(date));

            ResultSet resultSet = psGet.executeQuery();
            while (resultSet.next()) {
                orders.add(Order.builder()
                        .id(resultSet.getInt(1))
                        .userId(resultSet.getInt(2))
                        .createdAt(resultSet.getTimestamp(3).toLocalDateTime())
                        .price(resultSet.getDouble(4))
                        .build()
                );
            }
            resultSet.close();

            psGet.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            connectionPool.closeConnection(connection);
        }
        return orders;
    }

    @Override
    public List<Order> findByUserId(int id) {
        List<Order> orders = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDERS_BY_USER_ID_QUERY);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(Order.builder()
                        .id(resultSet.getInt(1))
                        .userId(resultSet.getInt(2))
                        .createdAt(resultSet.getTimestamp(3).toLocalDateTime())
                        .price(resultSet.getDouble(4))
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
        return orders;
    }
}