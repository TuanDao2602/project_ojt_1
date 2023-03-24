package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.OrDerDetail;
import ra.model.entity.Orders;

import java.util.List;

@Repository
public interface OrdersDetailRepository extends JpaRepository<OrDerDetail,Integer> {
    List<OrDerDetail> findByOrdersIn(List<Orders> listOrder);
}
