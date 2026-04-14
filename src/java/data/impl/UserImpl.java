/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.impl;

import data.dao.UserDao;
import data.driver.MySQLDriver;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author Admin
 */
public class UserImpl implements UserDao {
    Connection con = MySQLDriver.getConnection();
    public User find(String emailphone, String password){
        String sql;
        if(emailphone.contains("@"))
            sql = "select * from users where email='"+emailphone+"' and password='"+password+"'";
        else
            sql = "select * from users where phone='"+emailphone+"' and password='"+password+"'";
        try {
            PreparedStatement sttm = con.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String pass = rs.getString("password");
                String role = rs.getString("role");
                return  new User(id, name, email, phone, pass, role);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User findUser(String emailphone) {
        String sql;
        if(emailphone.contains("@"))
            sql = "select * from users where email='"+emailphone+"'";
        else
            sql = "select * from users where phone='"+emailphone+"'";
        try {
            PreparedStatement sttm = con.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String pass = rs.getString("password");
                String role = rs.getString("role");
                return  new User(id, name, email, phone, pass, role);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void insertUser( String name, String email, String phone, String password) {
       
    String sql="insert into users( name, email, phone, password, role) value('"+name+"', '"+email;
    sql=sql +"', '"+phone+"', '"+password+"', '')";
        try {
            PreparedStatement sttm = con.prepareStatement(sql);
            sttm.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       
    }

    @Override
    public void updateBasic(User u) throws Exception {
        String sql = """
        UPDATE users 
           SET name=?, email=?, phone=?, role=? 
         WHERE id=?""";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, u.getName());
        ps.setString(2, u.getEmail());
        ps.setString(3, u.getPhone());
        ps.setString(4, u.getRole());
        ps.setInt(5, u.getId());
        ps.executeUpdate();
    }
    }

    @Override
    public void updateRole(int id, String role) throws Exception {
         String sql = "UPDATE users SET role=? WHERE id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, role);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM users WHERE id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public User findById(int id){
        String sql = "SELECT id, name, email, phone, role FROM users WHERE id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setName(rs.getString("name"));
                    u.setEmail(rs.getString("email"));
                    u.setPhone(rs.getString("phone"));
                    u.setRole(rs.getString("role"));
                    return u;
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<User> findAll() throws Exception {
        String sql = "SELECT id, name, email, phone, role FROM users ORDER BY id";
        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<User> list = new ArrayList<>();
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setRole(rs.getString("role"));
                list.add(u);
            }
            return list;
        }
    }

    @Override
    public List<User> findCustomers() throws Exception {
        String sql = """
            SELECT id, name, email, phone, role
            FROM users
            WHERE COALESCE(role,'') <> 'admin'
            ORDER BY id
        """;
        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<User> list = new ArrayList<>();
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setRole(rs.getString("role"));
                list.add(u);
            }
            return list;
        }
    }

   @Override
public boolean emailExists(String email, int excludeUserId) {
    String sql = "SELECT 1 FROM users WHERE email=? AND id<>?";
    try (PreparedStatement st = con.prepareStatement(sql)) {
        st.setString(1, email);
        st.setInt(2, excludeUserId);
        try (ResultSet rs = st.executeQuery()) { return rs.next(); }
    } catch (SQLException e) { e.printStackTrace(); }
    return false;
}

    @Override
    public boolean phoneExists(String phone, int excludeUserId) {
        String sql = "SELECT 1 FROM users WHERE phone=? AND id<>?";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, phone);
            st.setInt(2, excludeUserId);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void updateProfile(User u) throws Exception {
        String sql = "UPDATE users SET name=?, email=?, phone=? WHERE id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPhone());
            ps.setInt(4, u.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void updatePassword(int id, String md5) throws Exception {
        String sql = "UPDATE users SET password=? WHERE id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, md5);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }
}
