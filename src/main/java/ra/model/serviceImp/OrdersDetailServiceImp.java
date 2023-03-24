package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.OrDerDetail;
import ra.model.entity.Orders;
import ra.model.repository.OrdersDetailRepository;
import ra.model.service.OrdersDetaiService;

import java.util.List;
@Service
public class OrdersDetailServiceImp implements OrdersDetaiService {
    @Autowired
    private OrdersDetailRepository ordersDetailRepository;

    @Override
    public List<OrDerDetail> finAllOrderDetail() {
        return ordersDetailRepository.findAll();
    }

    @Override
    public OrDerDetail findOrderDetailById(int detaiId) {
        return ordersDetailRepository.findById(detaiId).get();
    }

    @Override
    public OrDerDetail saveAndUpdate(OrDerDetail orDerDetail) {
        return ordersDetailRepository.save(orDerDetail);
    }

    @Override
    public void delete(int detaiId) {
        ordersDetailRepository.deleteById(detaiId);

    }

    @Override
    public List<OrDerDetail> findByOrDersIn(List<Orders> listOrder) {
        return ordersDetailRepository.findByOrdersIn(listOrder);
    }


}
