package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.model.entity.DiscountThisWeek;

import java.util.Date;

@Repository
public interface DiscountThisWeekRepository extends JpaRepository<DiscountThisWeek,Integer> {
    @Query(value =
            "select discountThisWeekId, disCount, endTime, starTime, status, title\n" +
                    "from discountthisweek  where now() between starTime and endTime",nativeQuery = true)
    DiscountThisWeek getDisscount();



}
