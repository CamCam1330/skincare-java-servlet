/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.dao;

import java.util.List;
import model.Category;

/**
 *
 * @author Admin
 */
public interface CategoryDao {
    List<Category> findAll() throws Exception;
    Category findById(int id) throws Exception;
    
    void insert(Category c) throws Exception;
    void update(Category c) throws Exception;
    void delete(int id) throws Exception;
}
