package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.Orders;
import ra.model.repository.OrderRepository;
import ra.model.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;
@Service

public class OrdersServiceImp implements OrderService {
    @Autowired
    private OrderRepository orderRepository;


    @Override
    public Orders findByStatusAndUsers_UserId(int userId, int status) {
        return  orderRepository.findByStatusAndUsers_UserId(userId,status);
    }

    @Override
    public Orders findByUsers_UserId(int id) {
        return orderRepository.findByUsers_UserId(id);
    }

    @Override
    public List<Orders> finAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Orders findOrderById(int orDersId) {
        return orderRepository.findById(orDersId).get();
    }

    @Override
    public Orders saveAndUpdate(Orders orDers) {
        return orderRepository.save(orDers);
    }

    @Override
    public void delete(int orDersId) {
        orderRepository.deleteById(orDersId);

    }

    @Override
    public List<Orders> findByCreatDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByCreateDateBetween(startDate, endDate);
    }


}
