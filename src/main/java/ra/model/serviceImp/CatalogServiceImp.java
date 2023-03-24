package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.model.entity.Catalog;
import ra.model.repository.CatalogRepository;
import ra.model.service.CatalogService;

import java.util.List;
@Service
public class CatalogServiceImp implements CatalogService {
    @Autowired
    private CatalogRepository catalogRepository;
    @Override
    public List<Catalog> findAllCatalog() {
        return catalogRepository.findAllCatalog();
    }

    @Override
    public Catalog findCatalogById(int catalogId) {
        return catalogRepository.findById(catalogId).get();
    }

    @Override
    public Catalog saveAndUpdate(Catalog catalog) {
        return catalogRepository.save(catalog);
    }

    @Override
    public void delete(int catalogId) {
    catalogRepository.deleteById(catalogId);

    }

    @Override
    public List<Catalog> searchByNameOrId(String catalogName, int catalogId) {
        return catalogRepository.searchByCatalogNameContainingOrCatalogID(catalogName,catalogId);
    }

    @Override
    public List<Catalog> sortByCatalogName(String catalogName) {
        if (catalogName.equals("asc")){
            return catalogRepository.findAll(Sort.by("catalogName").ascending());
        }else {
            return catalogRepository.findAll(Sort.by("catalogName").descending());
        }

    }

    @Override
    public List<Catalog> sortByNameAndId(String sendirectName, String sendirectId) {
        if (sendirectName.equals("asc")){
            if (sendirectId.equals("asc")){
                return catalogRepository.findAll(Sort.by("catalogName").and(Sort.by("catalogId")));
            }else {
                return catalogRepository.findAll(Sort.by("catalogName").and(Sort.by("catalogId").descending()));
            }
        }else {
            if (sendirectId.equals("asc")){
                return catalogRepository.findAll(Sort.by("catalogName").descending().and(Sort.by("catalogId")));
            }else {
                return catalogRepository.findAll(Sort.by("catalogName").descending().and(Sort.by("catalogId").descending()));
            }
        }

    }

    @Override
    public Page<Catalog> getPaggingCatalog(Pageable pageable) {
        return catalogRepository.findAll(pageable);
    }

    @Override
    public Page<Catalog> searchByCatalogNameContaining(String name, Pageable pageable) {
        return catalogRepository.searchByCatalogNameContaining(name,pageable);
    }


}
