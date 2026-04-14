
package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {
    private int id;
    private String name;
    private String image;      
    private double price;
    private int quantity;
    private boolean status;        
    private int idCategory;
    private int idBrand;

    public Product(int id, String name, String image, double price, int quantity, boolean status, int idCategory, int idBrand) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.idCategory = idCategory;
        this.idBrand = idBrand;
    }

    public Product(ResultSet rs) throws SQLException {
        this.id         = rs.getInt("id");
        this.name       = rs.getString("name");
        this.price      = rs.getDouble("price");
        this.quantity = rs.getInt("quantity");
        this.idCategory = rs.getInt("id_category"); 
        this.idBrand = rs.getInt("id_brand"); 
        this.image      = rs.getString("image");
        this.status = rs.getBoolean("status");    
    }

    public Product() {
    }
    
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isStatus() {
        return status;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public int getIdBrand() {
        return idBrand;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public void setIdBrand(int idBrand) {
        this.idBrand = idBrand;
    }
    
    
}
