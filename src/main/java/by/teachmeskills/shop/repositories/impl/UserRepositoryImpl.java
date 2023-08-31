package by.teachmeskills.shop.repositories.impl;

import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.repositories.UserRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static String updateQuery;
    private static final String ADD_USER_QUERY = "INSERT INTO users (name, surname, birthday, balance, email, password) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_USERS_QUERY = "SELECT * FROM users";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String GET_USER_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String GET_USER_BY_EMAIL_AND_PASS_QUERY = "SELECT * FROM users WHERE email = ? AND password = ?";
    private static final String GET_USER_BY_EMAIL_QUERY = "SELECT * FROM users WHERE email = ?";

    @Override
    public User create(User entity) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_QUERY);

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.setDate(3, Date.valueOf(entity.getBirthday()));
            preparedStatement.setBigDecimal(4, BigDecimal.valueOf(entity.getBalance()));
            preparedStatement.setString(5, entity.getEmail());
            preparedStatement.setString(6, entity.getPassword());
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
    public List<User> read() {
        List<User> users = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement psGet = connection.prepareStatement(GET_ALL_USERS_QUERY);

            ResultSet resultSet = psGet.executeQuery();
            while (resultSet.next()) {
                users.add(User.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .surname(resultSet.getString(3))
                        .birthday(resultSet.getTimestamp(4).toLocalDateTime().toLocalDate())
                        .balance(resultSet.getBigDecimal(5).doubleValue())
                        .email(resultSet.getString(5))
                        .password(resultSet.getString(6))
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
        return users;
    }

    @Override
    public User update(User entity) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

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
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY);

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
    public User findById(int id) {
        User user = null;
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement psGet = connection.prepareStatement(GET_USER_BY_ID_QUERY);

            psGet.setInt(1, id);
            ResultSet resultSet = psGet.executeQuery();
            while (resultSet.next()) {
                user = User.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .surname(resultSet.getString(3))
                        .birthday(resultSet.getTimestamp(4).toLocalDateTime().toLocalDate())
                        .balance(resultSet.getBigDecimal(5).doubleValue())
                        .email(resultSet.getString(5))
                        .password(resultSet.getString(6))
                        .build();
            }
            resultSet.close();

            psGet.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            connectionPool.closeConnection(connection);
        }
        return user;
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {

        User user = null;
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL_AND_PASS_QUERY);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = User.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .surname(resultSet.getString(3))
                        .birthday(resultSet.getTimestamp(4).toLocalDateTime().toLocalDate())
                        .balance(resultSet.getBigDecimal(5).doubleValue())
                        .email(resultSet.getString(6))
                        .password(resultSet.getString(7))
                        .build();
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            connectionPool.closeConnection(connection);
        }
        return user;
    }


    @Override
    public User findByEmail(String email) {
        User user = null;
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL_QUERY);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = User.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .surname(resultSet.getString(3))
                        .birthday(resultSet.getTimestamp(4).toLocalDateTime().toLocalDate())
                        .balance(resultSet.getBigDecimal(5).doubleValue())
                        .email(resultSet.getString(6))
                        .password(resultSet.getString(7))
                        .build();
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            connectionPool.closeConnection(connection);
        }
        return user;
    }
}
