/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.impl;

import data.dao.ProductDao;
import data.driver.MySQLDriver;
import java.sql.Connection;
import java.util.List;
import model.Product;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import data.utils.Vietnamese;
import javax.lang.model.util.Types;
/**
 *
 * @author Admin
 */
public class ProductImpl implements ProductDao {

    Connection con = MySQLDriver.getConnection();
    
    @Override
    public List<Product> latest(int limit) throws Exception {
        if (limit <= 0) limit = 12;
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE status=1 ORDER BY id DESC LIMIT ?";
        try {
            PreparedStatement sttm = con.prepareStatement(sql);
            sttm.setInt(1, limit);
            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs)); // cần constructor Product(ResultSet)
            }
            rs.close();
            sttm.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public List<Product> byCategory(int categoryId, int limit) throws Exception {
        if (limit <= 0) limit = 24;
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE status=1 AND id_category=? ORDER BY id DESC LIMIT ?";
        PreparedStatement sttm;
        try {
            sttm = con.prepareStatement(sql);
            sttm.setInt(1, categoryId);
            sttm.setInt(2, limit);
            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs));
            }
            rs.close();
            sttm.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Product find(int id) throws Exception {
        String sql = "select * from products where id="+id;
        PreparedStatement sttm;
        try {
            sttm = con.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            if(rs.next()) return new Product(rs);
        } catch (SQLException ex) {
            Logger.getLogger(ProductImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Product> search(String keyword, int limit) throws Exception {
        if (keyword == null) {
            keyword = "";
        }
        if (limit <= 0) {
            limit = 24;
        }
        String sql = "SELECT * FROM products WHERE status=1 AND name LIKE ? ORDER BY id DESC LIMIT ?";
        List<Product> raw = new ArrayList<>();
        try {
            PreparedStatement sttm = con.prepareStatement(sql);
            sttm.setString(1, "%" + keyword + "%");
            sttm.setInt(2, Math.max(limit, 60)); // lấy rộng hơn chút để lọc lại
            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {
                raw.add(new Product(rs));
            }
            rs.close();
            sttm.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String k = Vietnamese.unaccent(keyword).toLowerCase().trim();
        List<Product> filtered = new ArrayList<>();
        for (Product p : raw) {
            String n = Vietnamese.unaccent(p.getName()).toLowerCase();
            if (n.contains(k)) {
                filtered.add(p);
                if (filtered.size() >= limit) {
                    break;
                }
            }
        }
        return filtered.isEmpty() ? raw : filtered;
    }

    @Override
    public List<Product> byBrand(int brandId, int limit) throws SQLException{
        if (limit <= 0) {
            limit = 12;
        }
        String sql = "SELECT * FROM products WHERE status=1 AND id_brand=? ORDER BY id DESC LIMIT ?";
        List<Product> list = new ArrayList<>();
        try (PreparedStatement sttm = con.prepareStatement(sql)) {
            sttm.setInt(1, brandId);
            sttm.setInt(2, limit);
            try (ResultSet rs = sttm.executeQuery()) {
                while (rs.next()) {
                    list.add(new Product(rs));
                }
            }
        }
        return list;
    }

    @Override
    public int count(String keyword) throws Exception {
        String sql = "SELECT COUNT(*) FROM products WHERE 1=1"
                   + (keyword != null && !keyword.isBlank() ? " AND name LIKE ?" : "");
        try (PreparedStatement sttm = con.prepareStatement(sql)) {
            if (keyword != null && !keyword.isBlank()) sttm.setString(1, "%"+keyword+"%");
            try (ResultSet rs = sttm.executeQuery()) { rs.next(); return rs.getInt(1); }
        }
    }

    @Override
    public List<Product> searchPaged(String keyword, int offset, int limit) throws Exception {
        if (limit <= 0) limit = 12;
        if (offset < 0) offset = 0;
        List<Product> list = new ArrayList<>();
        String base = "SELECT * FROM products WHERE 1=1";
        String where = (keyword != null && !keyword.isBlank()) ? " AND name LIKE ?" : "";
        String sql = base + where + " ORDER BY id DESC LIMIT ? OFFSET ?";
        try (PreparedStatement sttm = con.prepareStatement(sql)) {
            int i = 1;
            if (!where.isEmpty()) sttm.setString(i++, "%"+keyword+"%");
            sttm.setInt(i++, limit);
            sttm.setInt(i, offset);
            try (ResultSet rs = sttm.executeQuery()) {
                while (rs.next()) list.add(new Product(rs));
            }
        }
        return list;
    }

    @Override
    public void insert(Product p) throws Exception {
        String sql = "INSERT INTO products(name,image,price,quantity,id_category,id_brand,status) " +
                            "VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement sttm = con.prepareStatement(sql)) {
            sttm.setString(1, p.getName());
            sttm.setString(2, p.getImage());
            sttm.setDouble(3, p.getPrice());
            sttm.setInt(4, p.getQuantity());
            sttm.setBoolean(5, p.isStatus());
            sttm.setInt(6, p.getIdCategory());
            if(p.getIdBrand() == 0) sttm.setNull(7, java.sql.Types.INTEGER);
                    else sttm.setInt(7, p.getIdBrand());
            sttm.executeUpdate();
        }
    }

    @Override
    public void update(Product p) throws Exception {
        String sql = "UPDATE products SET name=?, image=?, price=?, quantity=?, id_category=?, id_brand=?, status=? " +
                            "WHERE id=?";
        try (PreparedStatement sttm = con.prepareStatement(sql)) {
            sttm.setString(1, p.getName());
            sttm.setString(2, p.getImage());
            sttm.setDouble(3, p.getPrice());
            sttm.setInt(4, p.getQuantity());
            sttm.setBoolean(5, p.isStatus());
            sttm.setInt(6, p.getIdCategory());
            if (p.getIdBrand() == 0) {
                sttm.setNull(7, java.sql.Types.INTEGER);
            } else {
                sttm.setInt(7, p.getIdBrand());
            }
            sttm.setInt(8, p.getId());             
            sttm.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try (PreparedStatement sttm = con.prepareStatement("DELETE FROM products WHERE id=?")) {
            sttm.setInt(1, id);
            sttm.executeUpdate();
        }
    }
    
    @Override
    public List<Product> findAll() throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = """
        SELECT id, name, image, price, quantity, id_category, id_brand, status
        FROM products
        ORDER BY id DESC
    """;
        try (PreparedStatement st = con.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setImage(rs.getString("image"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setIdCategory(rs.getInt("id_category"));
                p.setIdBrand(rs.getInt("id_brand"));
                p.setStatus(rs.getBoolean("status"));
                list.add(p);
            }
        }
        return list;
    }
}
