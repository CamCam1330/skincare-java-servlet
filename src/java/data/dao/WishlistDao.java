/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.dao;
import java.util.List;
import model.Product;
/**
 *
 * @author Admin
 */
public interface WishlistDao {
    void add(int userId, int productId) throws Exception;
    void remove(int userId, int productId) throws Exception;
    boolean exists(int userId, int productId) throws Exception;
    List<Product> listByUser(int userId) throws Exception;
}
