package by.teachmeskills.shop.repositories.impl;

import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.repositories.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
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
    private final JdbcTemplate jdbcTemplate;
    private static String updateQuery;
    private static final String ADD_USER_QUERY = "INSERT INTO users (name, surname, birthday, balance, email, password) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_USERS_QUERY = "SELECT * FROM users";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String GET_USER_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String GET_USER_BY_EMAIL_AND_PASS_QUERY = "SELECT * FROM users WHERE email = ? AND password = ?";
    private static final String GET_USER_BY_EMAIL_QUERY = "SELECT * FROM users WHERE email = ?";

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User entity) {
        return jdbcTemplate.execute(ADD_USER_QUERY, (PreparedStatementCallback<User>) ps -> {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getSurname());
            ps.setDate(3, Date.valueOf(entity.getBirthday()));
            ps.setBigDecimal(4, BigDecimal.valueOf(entity.getBalance()));
            ps.setString(5, entity.getEmail());
            ps.setString(6, entity.getPassword());
            ps.execute();

            return entity;
        });
    }

    @Override
    public List<User> read() {
        return jdbcTemplate.query(GET_ALL_USERS_QUERY, (rs, rowNum) -> User.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .birthday(rs.getTimestamp("birthday").toLocalDateTime().toLocalDate())
                .balance(rs.getBigDecimal("balance").doubleValue())
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .build());
    }

    @Override
    public User update(User entity) {
        jdbcTemplate.update(updateQuery);
        return entity;
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_USER_QUERY, id);
    }

    @Override
    public User findById(int id) {
        return jdbcTemplate.queryForObject(GET_USER_BY_ID_QUERY, (RowMapper<User>) (rs, rowNum) -> User.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .birthday(rs.getTimestamp("birthday").toLocalDateTime().toLocalDate())
                .balance(rs.getBigDecimal("balance").doubleValue())
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .build(), id);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {

        return jdbcTemplate.queryForObject(GET_USER_BY_EMAIL_AND_PASS_QUERY, (RowMapper<User>) (rs, rowNum) -> User.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .birthday(rs.getTimestamp("birthday").toLocalDateTime().toLocalDate())
                .balance(rs.getInt("balance"))
                .build(), email, password);
    }


    @Override
    public User findByEmail(String email) {
        return jdbcTemplate.queryForObject(GET_USER_BY_EMAIL_QUERY, (RowMapper<User>) (rs, rowNum) -> User.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .birthday(rs.getTimestamp("birthday").toLocalDateTime().toLocalDate())
                .balance(rs.getInt("balance"))
                .build(), email);
    }
}
