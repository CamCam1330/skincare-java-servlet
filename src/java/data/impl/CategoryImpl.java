/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.impl;

import java.sql.Statement;
import data.dao.CategoryDao;
import data.dao.Database;
import data.driver.MySQLDriver;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;

/**
 *
 * @author Admin
 */
public class CategoryImpl implements CategoryDao {
    Connection con = MySQLDriver.getConnection();
    @Override
    public List<Category> findAll(){
        List<Category> listCategory = new ArrayList<>();
        String sql = "select * from categories order by id asc";
        try {
            PreparedStatement sttm = con.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                listCategory.add(new Category(id, name));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return listCategory;
    }

    
    @Override
    public Category findById(int id) throws SQLException{
        String sql = "select * from categories WHERE id=?";
        try (PreparedStatement sttm = con.prepareStatement(sql)) {
            sttm.setInt(1, id);
            try (ResultSet rs = sttm.executeQuery()) {
                if (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt("id"));
                    c.setName(rs.getString("name"));
                    return c;
                }
            }
        return null;
        }
    }

    @Override
    public void insert(Category c) throws Exception {
        String sql = "INSERT INTO categories(name) VALUES(?)";
        try (PreparedStatement sttm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            sttm.setString(1, c.getName());
            sttm.executeUpdate();

            // nếu muốn lấy id mới tạo:
            try (ResultSet rs = sttm.getGeneratedKeys()) {
                if (rs.next()) {
                    c.setId(rs.getInt(1));
                }
            }
        }
}


    @Override
    public void update(Category c) throws Exception {
        final String sql = "UPDATE categories SET name = ? WHERE id = ?";
        try (PreparedStatement sttm = con.prepareStatement(sql)) {
            sttm.setString(1, c.getName() == null ? "" : c.getName().trim());
            sttm.setInt(2, c.getId());
            sttm.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        final String sql = "DELETE FROM categories WHERE id = ?";
        try ( PreparedStatement sttm = con.prepareStatement(sql)){
            sttm.setInt(1, id);
            sttm.executeUpdate();
        }
    }
}
