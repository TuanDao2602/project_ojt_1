package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.model.entity.Orders;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {

    @Query(value = "from Orders o where o.users.userId=:userId and  o.status=:status")
    Orders findByStatusAndUsers_UserId(@Param("userId") int userId, @Param("status") int status);
    Orders findByUsers_UserId(int id);
    List<Orders> findByCreateDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
