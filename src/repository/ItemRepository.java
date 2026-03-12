package repository;

import config.DatabaseConnection;
import model.Item;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemRepository {

    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                items.add(new Item(
                        rs.getInt("id"),
                        rs.getInt("seller_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getBigDecimal("price")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    // ---------------- Insert ----------------
    public void insertItem(int sellerId, String title, String description, BigDecimal price) {
        String sql = "INSERT INTO items (seller_id, title, description, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sellerId);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setBigDecimal(4, price);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(int itemId) {
        String sql = "DELETE FROM items WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, itemId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateItem(int id, String title, String description, BigDecimal price) {

        String sql = "UPDATE items SET title = ?, description = ?, price = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setBigDecimal(3, price);
            ps.setInt(4, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}