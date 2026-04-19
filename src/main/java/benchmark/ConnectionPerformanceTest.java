package benchmark;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPerformanceTest {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/sgbd1";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) {
        try {
            System.out.println("=== CONNECTION CREATION OVERHEAD TEST ===");

            long noPoolNanos = testWithoutPooling(100);
            long poolNanos = testWithPooling(100);

            printResults("Without pooling", noPoolNanos, 100);
            printResults("With pooling", poolNanos, 100);

            double speedup = (double) noPoolNanos / poolNanos;
            System.out.printf("Pooling speedup: %.2fx%n", speedup);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long testWithoutPooling(int count) throws SQLException {
        long start = System.nanoTime();

        for (int i = 0; i < count; i++) {
            try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
                // connection opened and closed immediately
            }
        }

        return System.nanoTime() - start;
    }

    private static long testWithPooling(int count) throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(JDBC_URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setDriverClassName("org.postgresql.Driver");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setConnectionTimeout(30000);

        try (HikariDataSource dataSource = new HikariDataSource(config)) {
            long start = System.nanoTime();

            for (int i = 0; i < count; i++) {
                try (Connection conn = dataSource.getConnection()) {
                    // borrowed from pool and returned immediately
                }
            }

            return System.nanoTime() - start;
        }
    }

    private static void printResults(String label, long totalNanos, int count) {
        double totalMs = totalNanos / 1_000_000.0;
        double avgMs = totalMs / count;

        System.out.printf("%s -> total: %.2f ms, average per connection: %.4f ms%n",
                label, totalMs, avgMs);
    }
}