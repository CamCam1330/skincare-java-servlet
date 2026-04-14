/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.dao;

import data.impl.BrandImpl;
import data.impl.CategoryImpl;
import data.impl.OrderImpl;
import data.impl.ProductImpl;
import data.impl.UserImpl;
import data.impl.WishlistImpl;

/**
 *
 * @author Admin
 */
public class Database {
   public  static  CategoryDao getCategoryDao(){
        return new CategoryImpl();
    }
    
    public  static  ProductDao getProductDao(){
        return new ProductImpl();
    }
    
    public  static  BranDao getBranDao(){
        return new BrandImpl();
    }
    
    public  static  UserDao getUserDao(){
        return new UserImpl();
    }
    
    public static WishlistDao getWishlistDao(){
        return new WishlistImpl();
    }
    
    public  static OrderDao getOrderDao(){
        return new OrderImpl();
    }
}
