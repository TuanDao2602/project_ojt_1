package ra.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.model.entity.Catalog;

import java.util.List;

public interface CatalogService {
    List<Catalog>findAllCatalog();
    Catalog findCatalogById(int catalogId);
    Catalog saveAndUpdate(Catalog catalog);
    void delete(int catalogId);
    List<Catalog>searchByNameOrId(String catalogName,int catalogId);
    List<Catalog>sortByCatalogName(String sendirect);
    List<Catalog>sortByNameAndId(String sendirectName,String sendirectId);
    Page<Catalog>getPaggingCatalog(Pageable pageable);
    Page<Catalog>searchByCatalogNameContaining(String name, Pageable pageable);

}
