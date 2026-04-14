/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.io.Serializable;
/**
 *
 * @author Admin
 */
public class CartItem implements Serializable{
    private Product product;
    private int quantity;

    public CartItem(Product p, int q){ 
        this.product = p; 
        this.quantity = q; 
    }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int q) { this.quantity = q; }
    public double getAmount() {
        return product.getPrice() * quantity;
    }
}
