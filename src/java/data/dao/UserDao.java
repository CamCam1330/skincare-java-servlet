/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package data.dao;

import java.util.List;
import model.User;

/**
 *
 * @author Admin
 */
public interface UserDao {
    public User find(String emailphone, String password);
    public User findUser(String emailphone);
    public void insertUser(String name, String email, String phone, String password);
    public User findById(int id);
    public List<User> findAll() throws Exception;
    public List<User> findCustomers() throws Exception; 
    void updateBasic(User u) throws Exception;

    void updateRole(int id, String role) throws Exception;

    void delete(int id) throws Exception;
    
    boolean emailExists(String email, int excludeUserId);
    boolean phoneExists(String phone, int excludeUserId);

    void updateProfile(User u) throws Exception;
    void updatePassword(int id, String md5) throws Exception;
}
