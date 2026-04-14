package data.dao;

import java.util.List;
import model.Order;
import model.OrderItem;

public interface OrderDao {
  List<Order> findPaidSummary() throws Exception;
    int insert(Order o) throws Exception;                
    void insertItem(OrderItem it) throws Exception;
    void updateStatus(int orderId, String status) throws Exception;
    Order find(int id) throws Exception;
    List<Order> listAll() throws Exception;
    List<Order> listByUser(int userId) throws Exception;
}
