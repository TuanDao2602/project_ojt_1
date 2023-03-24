package ra.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.model.entity.Catalog;

import java.util.List;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog,Integer> {
    @Query (value = "from Catalog c where c.status = true ")
    List<Catalog>findAllCatalog();
    List<Catalog>searchByCatalogNameContainingOrCatalogID(String catalogName,int catalogId);
    Page<Catalog>searchByCatalogNameContaining(String name, Pageable pageable);

}
