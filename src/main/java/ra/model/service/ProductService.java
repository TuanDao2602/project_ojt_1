package ra.model.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.model.entity.Catalog;
import ra.model.entity.Product;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductService {
    List<Product>finAllProduct();
    Product saveOrUpdate(Product product);
    Product finByProductId(int productId);
    void deleteProduct(int productId);
    List<Product>searchByProductNameContainingOrProductID(String productName,int productId);
    List<Product>sortByProductName(String sendirect);
    List<Product>sortByNameAndId(String sendirectName,String sendirectId);
    Page<Product>getPaggingProduct(Pageable pageable);
    List<Product> findByProductIDIn(List<Integer> listProductId);
    List<Product> getFeatureProduct(LocalDateTime startDate, LocalDateTime endDate);


}
