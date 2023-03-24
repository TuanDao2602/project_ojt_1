package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.Banner;
import ra.model.repository.BannerRepository;
import ra.model.service.BannerService;

import java.util.List;
@Service
public class BannerServiceImp implements BannerService {
    @Autowired
    private BannerRepository bannerRepository;
    @Override
    public List<Banner> finALLBanner() {
        return bannerRepository.findAll();
    }

    @Override
    public Banner finBanerById(int bannerId) {
        return bannerRepository.findById(bannerId).get();
    }

    @Override
    public Banner saveOrUpdate(Banner banner) {
        return bannerRepository.save(banner);
    }

    @Override
    public void deleteBanner(int bannerId) {
        bannerRepository.deleteById(bannerId);
    }

    @Override
    public List<Banner> searchByContentContainingOrBanNerId(String content, int bannerId) {
        return bannerRepository.searchByContentContainingOrBanNerId(content,bannerId);
    }


}
