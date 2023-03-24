package ra.model.service;

import ra.model.entity.Banner;

import java.util.List;

public interface BannerService {
    List<Banner>finALLBanner();
    Banner finBanerById(int bannerId);
    Banner saveOrUpdate(Banner banner);
    void  deleteBanner(int bannerId);
    List<Banner> searchByContentContainingOrBanNerId (String content, int bannerId);
}
