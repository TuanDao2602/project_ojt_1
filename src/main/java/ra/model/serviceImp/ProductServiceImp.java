package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.model.entity.OrDerDetail;
import ra.model.entity.Orders;
import ra.model.entity.Product;
import ra.model.repository.OrderRepository;
import ra.model.repository.OrdersDetailRepository;
import ra.model.repository.ProductRepository;
import ra.model.service.ProductService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrdersDetailRepository ordersDetailRepository;
    @Override
    public List<Product> finAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product saveOrUpdate(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product finByProductId(int productId) {
        return productRepository.findById(productId).get();
    }

    @Override
    public void deleteProduct(int productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public List<Product> searchByProductNameContainingOrProductID(String productName, int productId) {
        return productRepository.searchByProductNameContainingOrProductID(productName,productId);
    }

    @Override
    public List<Product> sortByProductName(String productName) {
        if (productName.equals("asc")){
            return productRepository.findAll(Sort.by("productName").ascending());
        }else {
            return productRepository.findAll(Sort.by("productName").descending());
        }
    }

    @Override
    public List<Product> sortByNameAndId(String sendirectName, String sendirectId) {
        return null;
    }

    @Override
    public Page<Product> getPaggingProduct(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> findByProductIDIn(List<Integer> listProductId) {
        return productRepository.findByProductIDIn(listProductId);
    }

    @Override
    public List<Product> getFeatureProduct(LocalDateTime startDate, LocalDateTime endDate) {
            List<Orders> listCart = orderRepository.findByCreateDateBetween(startDate, endDate);
            List<OrDerDetail> listOrderDetail = ordersDetailRepository.findByOrdersIn(listCart);
            Map<Integer, Integer> maxMap = new HashMap<>();
            for (int i = 0; i < listOrderDetail.size(); i++) {
                int quantity = listOrderDetail.get(i).getQuantity();
                int key = listOrderDetail.get(i).getProduct().getProductID();
                if (maxMap.containsKey(key)) {
                    int value = maxMap.get(key);
                    maxMap.put(key, value + quantity);
                } else {
                    maxMap.put(key, quantity);
                }
            }
            Map<Integer, Integer> result = maxMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .skip(maxMap.size() - 3)
                    .limit(3)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue
                    ));

            List<Product> listProduct = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
                listProduct.add(finByProductId(entry.getKey()));
            }
            return listProduct;
        }


    }



