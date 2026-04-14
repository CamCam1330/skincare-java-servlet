/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.impl;

import data.dao.BranDao;
import data.driver.MySQLDriver;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import model.Brand;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class BrandImpl implements BranDao {
    
    Connection con = MySQLDriver.getConnection();

    
    @Override
    public List<Brand> findAll() throws Exception {
        List<Brand> listBrand = new ArrayList<>();
        String sql = "select * from brands";
        try {
            PreparedStatement sttm = con.prepareStatement(sql);
            ResultSet rs = sttm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");               
                listBrand.add(new Brand(id, name));
            }
        } catch (SQLException ex) {
        }
        return listBrand;
    }

    @Override
    public Brand findById(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
