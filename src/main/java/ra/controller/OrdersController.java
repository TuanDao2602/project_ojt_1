package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.request.CheckoutRequest;

import ra.model.dto.request.OrderDetailRequest;
import ra.model.dto.request.OrderUpdateRequest;

import ra.model.dto.respon.OrdersReponse;
import ra.model.entity.OrDerDetail;
import ra.model.entity.Orders;
import ra.model.entity.Product;
import ra.model.service.OrderService;
import ra.model.service.OrdersDetaiService;
import ra.model.service.ProductService;
import ra.model.service.UserService;
import ra.sercurity.CustomUserDetails;

import java.util.ArrayList;
import java.util.List;

@RestController


@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("api/v1/auth/order")
public class OrdersController {
    @Autowired
    private OrdersDetaiService ordersDetaiService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;



    @GetMapping
    public List<OrdersReponse> getAllOrder(){
        List<Orders> ordersList =orderService.finAllOrders();
        List<OrdersReponse>ordersReponseList= new ArrayList<>();
        String status ="";
        for (Orders o: ordersList) {
            if (o.getStatus()==0){
                status="sản phẩm bạn mua đang trong giỏ hàng";
            }else if (o.getStatus()==1){
                status="đang chờ duyệt sản phẩm";
            }else if (o.getStatus()==2){
                status="đang chuẩn bị hàng";
            }else if (o.getStatus()==3){
                status="đang giao hàng";
            }else if (o.getStatus()==4){
                status="giao hàng thành công";
            }else {
                status="đã hủy đơn hàng";
            }
            OrdersReponse ordersReponse= new OrdersReponse(status,o.getTotal(),o.getUsers().getUserName());
            ordersReponseList.add(ordersReponse);

        }
        return ordersReponseList;
    }

    @PutMapping("/updateOrder")
    public ResponseEntity<?> updateOrder(@RequestBody OrderUpdateRequest orderUpdateRequest){
        Orders ordersUpdate = orderService.findOrderById(orderUpdateRequest.getOrDerId());
        ordersUpdate.setStatus(orderUpdateRequest.getStatus());
       orderService.saveAndUpdate(ordersUpdate);
        return ResponseEntity.ok("successfully");

    }


    @GetMapping("/getbyId/{orDerId}")
    public OrdersReponse getById(@PathVariable("orDerId")int orDerId){
        Orders orDers=orderService.findOrderById(orDerId);
        String status ="";
        if (orDers.getStatus()==0){
            status="sản phẩm bạn mua đang trong giỏ hàng";
        }else if (orDers.getStatus()==1){
            status="đang chờ duyệt sản phẩm";
        }else if (orDers.getStatus()==2){
            status="đang chuẩn bị hàng";
        }else if (orDers.getStatus()==3){
            status="đang giao hàng";
        }else if (orDers.getStatus()==4){
            status="giao hàng thành công";
        }else {
            status="đã hủy đơn hàng";
        }
        OrdersReponse ordersReponse=new OrdersReponse(
                status,orDers.getTotal(),orDers.getUsers().getUserName());

        return ordersReponse;
    }


    @PostMapping("/addtoCart")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    public ResponseEntity<?> addToCart(@RequestBody OrderDetailRequest orderDetailRequest){
        CustomUserDetails users = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (users!=null){
            Orders orders = orderService.findByStatusAndUsers_UserId(users.getUserId(), 0);
            if (orders == null) {
                    Orders orDers=new Orders();
                    orDers.setUsers(userService.findUserById(users.getUserId()));
                    orDers.setStatus(0);

                    orders = orderService.saveAndUpdate(orDers);
            }
            try {

                OrDerDetail orDerDetail = new OrDerDetail();
                orDerDetail.setOrders(orders);
                Product product = productService.finByProductId(orderDetailRequest.getProductId());
                orDerDetail.setProduct(product);
                orDerDetail.setQuantity(orderDetailRequest.getQuantity());
                orDerDetail.setPrice(product.getPriceProduct() * orderDetailRequest.getQuantity());
//                orders.setTotal(totalAmount(orders.getOrDerDetailList()));
                for (OrDerDetail o : orders.getOrDerDetailList()) {
                    if (o.getProduct().getProductID() == product.getProductID()) {
                        orDerDetail.setQuantity(orderDetailRequest.getQuantity() + o.getQuantity());
                        orDerDetail.setPrice(product.getPriceProduct() * orDerDetail.getQuantity());
                        // dòng 61 để gộp hết orderDetail cùng product vào làm 1
                        orDerDetail.setDetaiId(o.getDetaiId());

                        break;
                    }
                }

                 orderService.saveAndUpdate(orders);
                orDerDetail = ordersDetaiService.saveAndUpdate(orDerDetail);
                return ResponseEntity.ok(orDerDetail);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.ok("CartDetail create fail");
            }
        }else {
            return ResponseEntity.ok("Bạn cần đăng nhập trước khi thực hiện thao tác này");
        }

    }


    public float totalAmount(List<OrDerDetail> orDerDetailList) {
        float totalAmout=0;
        for (OrDerDetail o:orDerDetailList){
            totalAmout+=o.getPrice();
        }
        return totalAmout;
    }

    @PostMapping("/checkout")
    public ResponseEntity<?>checkout(@RequestBody CheckoutRequest checkoutRequest){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Orders orDers = orderService.findByStatusAndUsers_UserId(userDetails.getUserId(),0);
        orDers.setUsers(userService.findUserById(userDetails.getUserId()));
        orDers.setTotal(totalAmount(orDers.getOrDerDetailList()));
        orDers.setCreateDate(checkoutRequest.getCreateDate());
        orDers.setCity(checkoutRequest.getCity());
        orDers.setAdress(checkoutRequest.getAdress());
        orDers.setCountry(checkoutRequest.getCountry());
        orDers.setNote(checkoutRequest.getNote());
        orDers.setFirstName(checkoutRequest.getFirstName());
        orDers.setLastName(checkoutRequest.getLastName());
        orDers.setPostCode(checkoutRequest.getPostCode());
        orDers.setPhone(checkoutRequest.getPhone());
        orDers.setCity(checkoutRequest.getCity());
        orDers.setStatus(1);
        orderService.saveAndUpdate(orDers);
        return ResponseEntity.ok("checkout thành công");
    }

    @DeleteMapping("/delete/{orDerId}")
    public ResponseEntity<?> delete(@PathVariable("orDerId")int orDerId){
        Orders orDersdelete = orderService.findOrderById(orDerId);
        if (orDersdelete.getStatus()==0||orDersdelete.getStatus()==1){
            orderService.delete(orDerId);
        }
        return ResponseEntity.ok("đã xóa thành công");

    }

}
