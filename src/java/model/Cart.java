/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.io.Serializable;
import java.util.*;
/**
 *
 * @author Admin
 */
public class Cart {
    private final Map<Integer, CartItem> items = new LinkedHashMap<>();

    public void add(Product p, int qty) {
        if (p == null || qty <= 0) return;
        CartItem it = items.get(p.getId());
        if (it == null) items.put(p.getId(), new CartItem(p, qty));
        else it.setQuantity(it.getQuantity() + qty);
    }

    public void remove(int productId) { items.remove(productId); }

    public int getTotalItems() {
        int sum = 0;
        for (CartItem it: items.values()) sum += it.getQuantity();
        return sum;
    }
    
    public double getTotalAmount() {
        return items.values().stream().mapToDouble(CartItem::getAmount).sum();
    }

    public Collection<CartItem> getItems() { return items.values(); }
    
    public boolean isEmpty() { return items.isEmpty(); }
}
