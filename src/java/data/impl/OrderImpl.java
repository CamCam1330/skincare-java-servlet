package data.impl;

import data.dao.Database;
import data.dao.OrderDao;
import data.driver.MySQLDriver;
import java.sql.*;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import model.Order;
import model.OrderItem;

public class OrderImpl implements OrderDao {

    Connection con = MySQLDriver.getConnection();

    @Override
    public List<Order> findPaidSummary() throws Exception {
        String sql = """
      SELECT o.id,
             o.user_id,
             COALESCE(u.name, u.email) AS user_name,
             o.created_at,
             o.status,
             COALESCE(SUM(oi.quantity * oi.price), 0) AS total,
             COALESCE(SUM(oi.quantity), 0) AS item_count
      FROM   orders o
      JOIN   users u ON u.id = o.user_id
      LEFT JOIN order_items oi ON oi.order_id = o.id
      WHERE  o.status = 'PAID'
      GROUP  BY o.id
      ORDER  BY o.created_at DESC
    """;

        List<Order> list = new ArrayList<>();
        try (
                PreparedStatement sttm = con.prepareStatement(sql); ResultSet rs = sttm.executeQuery()) {

            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setUserId(rs.getInt("user_id"));
                o.setUserName(rs.getString("user_name"));
                Timestamp ts = rs.getTimestamp("created_at");
                o.setCreatedAt(ts != null ? ts.toInstant() : null);
                o.setStatus(rs.getString("status"));
                o.setTotal(rs.getDouble("total"));
                o.setItemCount(rs.getInt("item_count"));
                list.add(o);
            }
        }
        return list;
    }

    @Override
    public int insert(Order o) throws Exception {
        String sql = "INSERT INTO orders(user_id, status) VALUES(?,?)";
        try (PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, o.getUserId());
            st.setString(2, o.getStatus() == null ? "PENDING" : o.getStatus());
            st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    @Override
    public void insertItem(OrderItem it) throws Exception {
        String sql = "INSERT INTO order_items(order_id, product_id, quantity, price) VALUES(?,?,?,?)";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, it.getOrderId());
            st.setInt(2, it.getProductId());
            st.setInt(3, it.getQuantity());
            st.setDouble(4, it.getPrice());
            st.executeUpdate();
        }
    }

    @Override
    public void updateStatus(int orderId, String status) throws Exception {
        String sql = "UPDATE orders SET status=? WHERE id=?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, status);
            st.setInt(2, orderId);
            st.executeUpdate();
        }
    }

    @Override
    public Order find(int id) throws Exception {
        String sql = """
      SELECT o.id, o.user_id, u.name as user_name, o.status, o.created_at,
             COALESCE(SUM(oi.quantity*oi.price),0) as total,
             COUNT(oi.id) as item_count
      FROM orders o
      LEFT JOIN users u ON u.id=o.user_id
      LEFT JOIN order_items oi ON oi.order_id=o.id
      WHERE o.id=?
      GROUP BY o.id
    """;
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    while (rs.next()) {
                        Order o = new Order();
                        o.setId(rs.getInt("id"));
                        o.setUserId(rs.getInt("user_id"));
                        o.setUserName(rs.getString("user_name"));
                        Timestamp ts = rs.getTimestamp("created_at");
                        o.setCreatedAt(ts != null ? ts.toInstant() : null);
                        o.setStatus(rs.getString("status"));
                        o.setTotal(rs.getDouble("total"));
                        o.setItemCount(rs.getInt("item_count"));
                        return o;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<Order> listAll() throws Exception {
        String sql = """
      SELECT o.id, o.user_id, u.name as user_name, o.status, o.created_at,
             COALESCE(SUM(oi.quantity*oi.price),0) as total,
             COUNT(oi.id) as item_count
      FROM orders o
      LEFT JOIN users u ON u.id=o.user_id
      LEFT JOIN order_items oi ON oi.order_id=o.id
      GROUP BY o.id
      ORDER BY o.created_at DESC
    """;
        try (PreparedStatement st = con.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            List<Order> list = new ArrayList<>();
            while (rs.next()) {
                while (rs.next()) {
                    Order o = new Order();
                    o.setId(rs.getInt("id"));
                    o.setUserId(rs.getInt("user_id"));
                    o.setUserName(rs.getString("user_name"));
                    Timestamp ts = rs.getTimestamp("created_at");
                    o.setCreatedAt(ts != null ? ts.toInstant() : null);
                    o.setStatus(rs.getString("status"));
                    o.setTotal(rs.getDouble("total"));
                    o.setItemCount(rs.getInt("item_count"));
                    list.add(o);
                }
            }
            return list;
        }
    }

    @Override
    public List<Order> listByUser(int userId) throws Exception {
        String sql = """
      SELECT o.id, o.user_id, u.name as user_name, o.status, o.created_at,
             COALESCE(SUM(oi.quantity*oi.price),0) as total,
             COUNT(oi.id) as item_count
      FROM orders o
      LEFT JOIN users u ON u.id=o.user_id
      LEFT JOIN order_items oi ON oi.order_id=o.id
      WHERE o.user_id=?
      GROUP BY o.id
      ORDER BY o.created_at DESC
    """;
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, userId);
            try (ResultSet rs = st.executeQuery()) {
                List<Order> list = new ArrayList<>();
                while (rs.next()) {
                    while (rs.next()) {
                        Order o = new Order();
                        o.setId(rs.getInt("id"));
                        o.setUserId(rs.getInt("user_id"));
                        o.setUserName(rs.getString("user_name"));
                        Timestamp ts = rs.getTimestamp("created_at");
                        o.setCreatedAt(ts != null ? ts.toInstant() : null);
                        o.setStatus(rs.getString("status"));
                        o.setTotal(rs.getDouble("total"));
                        o.setItemCount(rs.getInt("item_count"));
                        list.add(o);
                    }
                }
                return list;
            }
        }
    }

}
