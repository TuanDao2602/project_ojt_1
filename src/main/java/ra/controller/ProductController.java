package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import ra.model.dto.request.BestSaleProRequest;
import ra.model.dto.request.BestSaleProRequest;
import ra.model.dto.request.ProductUpdateRequest;
import ra.model.dto.respon.ProductDto;
import ra.model.dto.request.Productrequest;
import ra.model.entity.Catalog;
import ra.model.entity.Product;
import ra.model.service.CatalogService;
import ra.model.service.OrderService;
import ra.model.service.OrdersDetaiService;
import ra.model.service.ProductService;

import java.time.LocalDate;
import java.util.*;

@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("api/v1/auth/product")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired

    private CatalogService catalogService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrdersDetaiService ordersDetaiService;
    @GetMapping
    public List<ProductDto> getAllCatalog(){
        List<Product> productList=productService.finAllProduct();
        List<ProductDto> productDtosList =new ArrayList<>();
        for (Product p:productList ) {
            ProductDto productDto=new ProductDto();
            productDto.setProductId(p.getProductID());
            productDto.setProductName(p.getProductName());
            productDto.setCatalogName(p.getCatalog().getCatalogName());
            productDto.setImage(p.getImage());
            productDto.setPrice((p.getPriceProduct()-(p.getPriceProduct()*(p.getDistCount())/100)));
            productDto.setQuantity(p.getQuantity());
            productDto.setTittle(p.getTitle());
            productDto.setStatus(p.isStatus());
            productDto.setDistCount(p.getDistCount());
            productDto.setCreateDate(p.getCreateDate());
            productDto.setStartDistCount(p.getStartDistCount());
            productDto.setEndDistCount(p.getEndDistCount());
//                    p.getProductID(),
//                    p.getProductName(),
//                    p.getCatalog().getCatalogName(),
//                    p.getImage(),
//                    (p.getPriceProduct()-(p.getPriceProduct() * (p.getDistCount()) / 100)),
//                    p.getQuantity(),
//                    p.getTitle(),
//                    p.isStatus(),
//                    p.getDistCount(),
//                    p.getCreateDate(),
//                    p.getStartDistCount(),
//                    p.getEndDistCount()

//



//
            productDtosList.add(productDto);
        }

        return productDtosList;
    }

    @GetMapping("/{productId}")
    public ProductDto getById(@PathVariable("productId")int productId){
        ProductDto productDto =new ProductDto();
        Product product =productService.finByProductId(productId);
        productDto.setProductId(product.getProductID());
        productDto.setProductName(product.getProductName());
        productDto.setCatalogName(product.getCatalog().getCatalogName());
        productDto.setImage(product.getImage());
        productDto.setPrice(product.getPriceProduct()-(product.getPriceProduct() * (product.getDistCount()) / 100));
        productDto.setQuantity(product.getQuantity());
        productDto.setTittle(product.getTitle());
        productDto.setStatus(true);
        productDto.setDistCount(product.getDistCount());
        productDto.setCreateDate(product.getCreateDate());
        productDto.setStartDistCount(product.getStartDistCount());
        productDto.setEndDistCount(product.getEndDistCount());
       return  productDto;
    }

    @PostMapping("/createProduct")
    public ResponseEntity<?> saveAndUpdate(@RequestBody Productrequest product){
        Product productNew =new Product();
        LocalDate time =LocalDate.now();
        productNew.setProductName(product.getProductName());
        productNew.setTitle(product.getTitle());
        productNew.setImage(product.getImage());
        productNew.setQuantity(product.getQuantity());
        productNew.setCreateDate(time);
        productNew.setPriceProduct(product.getPrice());
        productNew.setStatus(true);
        if (product.getDistCount()==0){
            productNew.setDistCount(0);
        }else {
            productNew.setDistCount(product.getDistCount());
        }

        productNew.setStartDistCount(product.getStartDistCount());
        productNew.setEndDistCount(product.getEndDistCount());
        Catalog catalog = (Catalog) catalogService.findCatalogById(product.getCatalogId());
        productNew.setCatalog(catalog);
        productNew = productService.saveOrUpdate(productNew);
//
        ProductDto productDto =new ProductDto();

        productDto.setProductId(productNew.getProductID());
        productDto.setProductName(productNew.getProductName());
        productDto.setCatalogName(productNew.getCatalog().getCatalogName());
        productDto.setImage(productNew.getImage());
        productDto.setPrice(productNew.getPriceProduct()-(productNew.getPriceProduct() * (productNew.getDistCount()) / 100));
        productDto.setQuantity(productNew.getQuantity());
        productDto.setTittle(productNew.getTitle());
        productDto.setStatus(true);
        productDto.setDistCount(productNew.getDistCount());
        productDto.setCreateDate(productNew.getCreateDate());
        productDto.setStartDistCount(productNew.getStartDistCount());
        productDto.setEndDistCount(productNew.getEndDistCount());

        return ResponseEntity.ok(productDto);

    }
    @PutMapping("/update/{productId}")
    public ResponseEntity<?> update(@PathVariable("productId")int productId,@RequestBody ProductUpdateRequest pUpdateRequest){
            Product productUpdate = productService.finByProductId(productId);
            productUpdate.setProductName(pUpdateRequest.getProductName());
            productUpdate.setCreateDate(pUpdateRequest.getCreateDate());
             productUpdate.setStatus(pUpdateRequest.isStatus());
             productUpdate.setPriceProduct(pUpdateRequest.getPrice());
             productUpdate.setTitle(pUpdateRequest.getTitle());
             productUpdate.setImage(pUpdateRequest.getImage());
             productUpdate.setQuantity(pUpdateRequest.getQuantity());
             productUpdate.setDistCount(pUpdateRequest.getDistCount());
             productUpdate.setStartDistCount(pUpdateRequest.getStartDistCount());
             productUpdate.setEndDistCount(pUpdateRequest.getEndDistCount());
            Catalog catalog = (Catalog) catalogService.findCatalogById(pUpdateRequest.getCatalogId());
            productUpdate.setCatalog(catalog);
            productUpdate = productService.saveOrUpdate(productUpdate);
            return ResponseEntity.ok(productUpdate);

    }
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId")int productId){
        Product product = productService.finByProductId(productId);
        product.setStatus(false);
        productService.saveOrUpdate(product);
        return ResponseEntity.ok("đã xóa thành công");
    }


    @GetMapping("/search")
    public ResponseEntity<List<Product>>SearchByNameOrId(@RequestParam("productName")String productName,
                                                         @RequestParam("productId")int productId){
        List<Product>productList=productService.searchByProductNameContainingOrProductID(productName,productId);
        return new ResponseEntity<>(productList, HttpStatus.OK);

    }
    @GetMapping("/sort")
    public ResponseEntity<List<Product>>sortByName(@RequestParam("productName")String productName){
        List<Product> productList =productService.sortByProductName(productName);
        return new ResponseEntity<>(productList,HttpStatus.OK);
    }

    @GetMapping("/pagging")
    public ResponseEntity<Map<String,Object>>getPagging(@RequestParam(defaultValue = "0")int page,
                                                        @RequestParam(defaultValue = "3")int size){
        Pageable pageablePagging= PageRequest.of(page,size);
        Page<Product> productPage = productService.getPaggingProduct(pageablePagging);
        Map<String,Object> data= new HashMap<>();
        data.put("productName",productPage.getContent());
        data.put("size",productPage.getSize());
        data.put("iteml",productPage.getTotalElements());
        data.put("page",productPage.getTotalPages());
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

    @GetMapping("/bestSalePro")
    public ResponseEntity<?> getFeatureSlider(@RequestBody BestSaleProRequest productFeatureRequest) {
        try {
            List<Product> responses = productService.getFeatureProduct(productFeatureRequest.getStartDate(), productFeatureRequest.getEndDate());
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("failse");
        }
    }

}
