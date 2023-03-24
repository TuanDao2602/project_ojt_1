package ra.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.request.DetailUpdateQuantityReponse;
import ra.model.dto.respon.OrderDetailReponse;
import ra.model.entity.OrDerDetail;
import ra.model.service.OrdersDetaiService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/v1/auth/detail")
public class OrderDetailController {
        @Autowired
        private OrdersDetaiService ordersDetaiService;
        @GetMapping
    public List<OrderDetailReponse> getAllOrderDetail(){
        List<OrDerDetail>orDerDetailList=ordersDetaiService.finAllOrderDetail();
        List<OrderDetailReponse>orderDetailReponses= new ArrayList<>();

        for (OrDerDetail o:orDerDetailList) {
            OrderDetailReponse orderDetailReponse=new OrderDetailReponse(
                    o.getDetaiId(),
                    o.getPrice(),
                    o.getOrders().getUsers().getUserName(),
                    o.getOrders().getPhone(),
                    o.getOrders().getFirstName(),
                    o.getOrders().getLastName(),
                    o.getOrders().getAdress()
            );
            orderDetailReponses.add(orderDetailReponse);
        } return orderDetailReponses;
        }

        @GetMapping("/{detaiId}")
        public OrderDetailReponse getById(@PathVariable("detaiId")int detaiId){
            OrDerDetail orDerDetail=ordersDetaiService.findOrderDetailById(detaiId);
            OrderDetailReponse orderDetailReponse =new OrderDetailReponse(orDerDetail.getDetaiId(),
                    orDerDetail.getPrice(),orDerDetail.getOrders().getUsers().getUserName(), orDerDetail.getOrders().getPhone(),
                    orDerDetail.getOrders().getFirstName(),orDerDetail.getOrders().getLastName(), orDerDetail.getOrders().getAdress()
            );
            return orderDetailReponse;

        }
        @PutMapping("/updateDetaiQuantity")
    public ResponseEntity<?> updateQuantity(@RequestBody DetailUpdateQuantityReponse detailUpdateQuantityReponse){
            OrDerDetail orDerDetail=ordersDetaiService.findOrderDetailById(detailUpdateQuantityReponse.getDetaiId());
            if(orDerDetail.getOrders().getStatus()==0){
                orDerDetail.setQuantity(detailUpdateQuantityReponse.getQuantity());
                orDerDetail.setPrice(orDerDetail.getProduct().getPriceProduct()*detailUpdateQuantityReponse.getQuantity());
                ordersDetaiService.saveAndUpdate(orDerDetail);
                return ResponseEntity.ok("Cập nhật thành công");
            }else {
                return ResponseEntity.ok("cập nhật thất bại");
            }
        }

        @DeleteMapping("/delete/{detaiId}")
    public ResponseEntity<?>delete(@PathVariable("detaiId")int detaiId){
            OrDerDetail orDerDetail = ordersDetaiService.findOrderDetailById(detaiId);
            if (orDerDetail.getOrders().getStatus()==0||orDerDetail.getOrders().getStatus()==1){
                ordersDetaiService.delete(orDerDetail.getDetaiId());
                return ResponseEntity.ok("đã xóa thành công");
            }else {
                return ResponseEntity.ok("xóa thất bại");
//                return ResponseEntity.ok("xóa thất bại");

            }
        }

//    public float maxQuantity(List<OrDerDetail> orDerDetailList) {
//        float maxQuantity=0;
//        for (OrDerDetail o:orDerDetailList){
//            if (maxQuantity<o.getProduct().getQuantity()){
//                maxQuantity=o.getDetaiId();
//            }
//        }
//        System.out.println("----------"+maxQuantity);
//        return maxQuantity;
//
//    }



    }
