/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package data.dao;

import java.util.List;
import model.Brand;

/**
 *
 * @author Admin
 */
public interface BranDao {
    List<Brand> findAll() throws Exception;
    Brand findById(int id) throws Exception; 
}
