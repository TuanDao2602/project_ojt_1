package ra.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

 import ra.model.entity.Catalog;
import ra.model.entity.Product;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product>searchByProductNameContainingOrProductID(String productName,int productId);
//    @Query(value = "from Product p where p.status = true ")
//    List<Product> findAllProduct();
    List<Product> findByProductIDIn(List<Integer> listProductId);
}
