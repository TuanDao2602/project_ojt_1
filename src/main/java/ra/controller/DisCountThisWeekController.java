package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.request.DisCountRequest;
import ra.model.dto.request.DisCountUpdateRequest;
import ra.model.dto.request.ThisWeekCart;
import ra.model.dto.respon.ThisWeekReponse;
import ra.model.entity.DiscountThisWeek;
import ra.model.entity.OrDerDetail;
import ra.model.entity.Orders;
import ra.model.entity.Product;
import ra.model.service.*;
import ra.sercurity.CustomUserDetails;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("api/v1/auth/thisWeek")
public class DisCountThisWeekController {
    @Autowired
    private DisCountService disCountService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrdersDetaiService ordersDetaiService;
    @GetMapping
    public List<ThisWeekReponse> getAll() {
//        List<DiscountThisWeek>discountThisWeekList=disCountService.finAllThisWeek();
        DiscountThisWeek discountThisWeek = disCountService.getDiscountThisWeek();
        if (discountThisWeek != null) {
            List<ThisWeekReponse> thisWeekReponseList = new ArrayList<>();
            Date time = new Date();
            for (Product product : discountThisWeek.getListProduct()) {
                ThisWeekReponse thisWeekReponseNew = new ThisWeekReponse();

                thisWeekReponseNew.setDisCount(discountThisWeek.getDisCount());
                thisWeekReponseNew.setQuantity(product.getQuantity());
                thisWeekReponseNew.setPriceSale(product.getPriceProduct() - (product.getPriceProduct() * discountThisWeek.getDisCount()) / 100);
                thisWeekReponseNew.setProductName(product.getProductName());
                thisWeekReponseNew.setStartTime(discountThisWeek.getStarTime());
                thisWeekReponseNew.setEndTime(discountThisWeek.getEndTime());
                thisWeekReponseNew.setTitle(discountThisWeek.getTitle());
                thisWeekReponseList.add(thisWeekReponseNew);


            }
            return thisWeekReponseList;

        } else {
            return null;
        }
    }



    @GetMapping("/{discountThisWeekId}")
    public DiscountThisWeek getById(@PathVariable("discountThisWeekId")int discountThisWeekId){
        return disCountService.finThisWeekById(discountThisWeekId);
    }
    @PostMapping("/createThisweek")
    public ResponseEntity<?> createTHisWeek(@RequestBody DisCountRequest disCountRequest){
        DiscountThisWeek discountThisWeekNew=new DiscountThisWeek();
        List<Product>productList = productService.findByProductIDIn(disCountRequest.getListProductId());
        discountThisWeekNew.setDisCount(disCountRequest.getDisCount());
        discountThisWeekNew.setTitle(disCountRequest.getTitle());
        discountThisWeekNew.setStarTime(disCountRequest.getStarTime());
        discountThisWeekNew.setEndTime(disCountRequest.getEndTime());
        discountThisWeekNew.setStatus(true);
        discountThisWeekNew.setListProduct(productList);
        disCountService.saveOrUpdate(discountThisWeekNew);
        return  ResponseEntity.ok("Create successfully");
    }
    @PutMapping ("/update/{discountThisWeekId}")
    public ResponseEntity<?>updateThisWeek(@PathVariable("discountThisWeekId")int discountThisWeekId, @RequestBody DisCountUpdateRequest disCountUpdateRequest){
        DiscountThisWeek discountThisWeekUpdate = disCountService.finThisWeekById(discountThisWeekId);
        List<Product>productList=productService.findByProductIDIn(disCountUpdateRequest.getListProductId());
        discountThisWeekUpdate.setDisCount(disCountUpdateRequest.getDisCount());
        discountThisWeekUpdate.setStarTime(disCountUpdateRequest.getStarTime());
        discountThisWeekUpdate.setEndTime(disCountUpdateRequest.getEndTime());
        discountThisWeekUpdate.setTitle(disCountUpdateRequest.getTitle());
        discountThisWeekUpdate.setStatus(disCountUpdateRequest.isStatus());
        discountThisWeekUpdate.setListProduct(productList);
        disCountService.saveOrUpdate(discountThisWeekUpdate);
        return ResponseEntity.ok("update successfully");
    }

    @PostMapping("/addCartThisWeek")
    public ResponseEntity<?> addCartThisWeek(@RequestBody ThisWeekCart thisWeekCart){
        boolean check=false;
        CustomUserDetails users = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DiscountThisWeek discountThisWeek =disCountService.getDiscountThisWeek();
            Orders orders = orderService.findByStatusAndUsers_UserId(users.getUserId(), 0);
            if (orders == null) {
                Orders orDers=new Orders();
                orDers.setUsers(userService.findUserById(users.getUserId()));
                orDers.setStatus(0);
                orders = orderService.saveAndUpdate(orDers);
            } try {

            OrDerDetail orDerDetail = new OrDerDetail();
            orDerDetail.setOrders(orders);
            Product product = productService.finByProductId(thisWeekCart.getProductId());
            for (Product p:discountThisWeek.getListProduct()) {
                //vòng forEach Product p : đối tượng duyệt qua mảng discountThisWeek.getListProduct()
                // mỗi vòng trả về 1 đối tượng Product
                if (p.getProductID()==product.getProductID()){
                    orDerDetail.setProduct(product);
                    orDerDetail.setPrice((product.getPriceProduct()-(product.getPriceProduct()* discountThisWeek.getDisCount()/100))* thisWeekCart.getQuantity());
                    orDerDetail.setQuantity(thisWeekCart.getQuantity());
                    check=true;
                }
            }
            for (OrDerDetail o : orders.getOrDerDetailList()) {
                if (o.getProduct().getProductID() == product.getProductID()) {
                    orDerDetail.setQuantity(thisWeekCart.getQuantity() + o.getQuantity());

                    orDerDetail.setPrice((product.getPriceProduct()-(product.getPriceProduct()* discountThisWeek.getDisCount()/100))* (orDerDetail.getQuantity()));
                    // dòng 61 để gộp hết orderDetail cùng product vào làm 1
                    orDerDetail.setDetaiId(o.getDetaiId());
                    break;
                }
            }
            if (check){
                ordersDetaiService.saveAndUpdate(orDerDetail);
                return ResponseEntity.ok("add to cart successfully");
            }else {
                return ResponseEntity.ok("CartDetail create fail");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("CartDetail create fail");
        }
    }

}
