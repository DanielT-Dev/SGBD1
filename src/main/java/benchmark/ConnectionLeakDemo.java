package benchmark;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionLeakDemo {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/sgbd1";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) {
        System.out.println("=== CONNECTION LEAK DEMO ===");
        demonstrateLeak();
        System.out.println();
        fixedVersion();
    }

    private static void demonstrateLeak() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(JDBC_URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setDriverClassName("org.postgresql.Driver");
        config.setMaximumPoolSize(3);
        config.setMinimumIdle(0);
        config.setConnectionTimeout(2000);

        List<Connection> leakedConnections = new ArrayList<>();

        try (HikariDataSource dataSource = new HikariDataSource(config)) {
            for (int i = 1; i <= 10; i++) {
                System.out.println("Opening connection " + i + " without closing it...");
                Connection conn = dataSource.getConnection();
                leakedConnections.add(conn);
            }
        } catch (SQLException e) {
            System.out.println("Pool exhausted / timeout reached:");
            e.printStackTrace();
        } finally {
            for (Connection c : leakedConnections) {
                try {
                    c.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    private static void fixedVersion() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(JDBC_URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setDriverClassName("org.postgresql.Driver");
        config.setMaximumPoolSize(3);
        config.setMinimumIdle(0);
        config.setConnectionTimeout(2000);

        try (HikariDataSource dataSource = new HikariDataSource(config)) {
            for (int i = 1; i <= 10; i++) {
                try (Connection conn = dataSource.getConnection()) {
                    System.out.println("Opened and closed connection " + i + " correctly.");
                }
            }
            System.out.println("Fixed version completed without exhausting the pool.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}