package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.Banner;


import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner,Integer> {
    List<Banner> searchByContentContainingOrBanNerId (String content, int bannerId);
}
