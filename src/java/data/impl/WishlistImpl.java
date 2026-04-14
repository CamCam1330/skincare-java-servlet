/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.impl;

import data.dao.WishlistDao;
import data.driver.MySQLDriver;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Product;
/**
 *
 * @author Admin
 */
public class WishlistImpl implements WishlistDao {
    Connection con = MySQLDriver.getConnection();

    @Override
    public void add(int userId, int productId) throws Exception {
        String sql = "INSERT IGNORE INTO wishlist(user_id, product_id) VALUES (?,?)";
        try (PreparedStatement sttm = con.prepareStatement(sql)) {
            sttm.setInt(1, userId);
            sttm.setInt(2, productId);
            sttm.executeUpdate();
        }
    }

    @Override
    public void remove(int userId, int productId) throws Exception {
        String sql = "DELETE FROM wishlist WHERE user_id=? AND product_id=?";
        try (PreparedStatement sttm = con.prepareStatement(sql)) {
            sttm.setInt(1, userId);
            sttm.setInt(2, productId);
            sttm.executeUpdate();
        }
    }

    @Override
    public boolean exists(int userId, int productId) throws Exception {
        String sql = "SELECT 1 FROM wishlist WHERE user_id=? AND product_id=? LIMIT 1";
        try (PreparedStatement sttm = con.prepareStatement(sql)) {
            sttm.setInt(1, userId);
            sttm.setInt(2, productId);
            try (ResultSet rs = sttm.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public List<Product> listByUser(int userId) throws Exception {
        String sql = """
            SELECT p.*
            FROM wishlist w
            JOIN products p ON p.id = w.product_id
            WHERE w.user_id = ?
            ORDER BY w.created_at DESC
        """;
        List<Product> list = new ArrayList<>();
        try (PreparedStatement sttm = con.prepareStatement(sql)) {
            sttm.setInt(1, userId);
            try (ResultSet rs = sttm.executeQuery()) {
                while (rs.next()) {
                    list.add(new Product(rs));
                }
            }
        }
        return list;
    }
}
