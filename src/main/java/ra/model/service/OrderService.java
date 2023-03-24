package ra.model.service;

import ra.model.entity.Orders;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    Orders findByStatusAndUsers_UserId(int userId, int status);
    Orders findByUsers_UserId(int id);
    List<Orders> finAllOrders();
    Orders findOrderById(int ordersId);
    Orders saveAndUpdate(Orders orDers);
    void delete(int ordersId);
    List<Orders> findByCreatDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
