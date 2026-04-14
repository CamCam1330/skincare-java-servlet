package data.dao;
import model.Product;
import java.util.List;

public interface ProductDao {
    List<Product> latest(int limit) throws Exception;
    List<Product> byCategory(int categoryId, int limit) throws Exception;
    Product find(int id) throws Exception;
    List<Product> search(String keyword, int limit) throws Exception;
    List<Product> byBrand(int brandId, int limit) throws Exception;
    List<Product> findAll() throws Exception;
    //------------------//
    
    int count(String keyword) throws Exception;
    List<Product> searchPaged(String keyword, int offset, int limit) throws Exception;

    void insert(Product p) throws Exception;
    void update(Product p) throws Exception;
    void delete(int id) throws Exception;
}
