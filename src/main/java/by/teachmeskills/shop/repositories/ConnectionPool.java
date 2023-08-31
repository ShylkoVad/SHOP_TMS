package by.teachmeskills.shop.repositories;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Slf4j
public class ConnectionPool {

    private static volatile ConnectionPool instance;
    private static final String DB_PROPERTY_FILE = "application";
    private static final String DB_URL = "db.url";
    private static final String DB_LOGIN = "db.login";
    private static final String DB_PASS = "db.pass";
    private static final int MAX_CONNECTION_COUNT = 10;
    private static final int MIN_CONNECTION_COUNT = 5;
    private static final String URL;
    private static final String LOGIN;
    private static final String PASS;

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DB_PROPERTY_FILE);
        URL = resourceBundle.getString(DB_URL);
        LOGIN = resourceBundle.getString(DB_LOGIN);
        PASS = resourceBundle.getString(DB_PASS);
    }

    private volatile int currentConnectionNumber = MIN_CONNECTION_COUNT;
    private final BlockingQueue<Connection> pool = new ArrayBlockingQueue<>(MAX_CONNECTION_COUNT, true);

    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    private ConnectionPool() {
        for (int i = 0; i < MIN_CONNECTION_COUNT; i++) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                pool.add(DriverManager.getConnection(URL, LOGIN, PASS));
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void openAdditionalConnection() throws Exception {
        try {
            pool.add(DriverManager.getConnection(URL, LOGIN, PASS));
            currentConnectionNumber++;
        } catch (SQLException e) {
            throw new Exception("Новое соединение не было добавлено в пул соединений", e);
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            if (pool.isEmpty() && currentConnectionNumber < MAX_CONNECTION_COUNT) {
                openAdditionalConnection();
            }
            connection = pool.take();
        } catch (Exception ex) {
            Thread.currentThread().interrupt();
            log.warn("Достигнуто максимальное количество подключений!");
        }
        return connection;
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            if (currentConnectionNumber > MIN_CONNECTION_COUNT) {
                currentConnectionNumber--;
            }
            try {
                pool.put(connection);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("Соединение не было возвращено в пул должным образом");
            }
        }
    }

    public void disconnect() {
        pool.forEach(s -> {
            try {
                s.close();
            } catch (SQLException e) {
                log.error("Не удается отключить соединение с пулом");
            }
        });
    }
}
