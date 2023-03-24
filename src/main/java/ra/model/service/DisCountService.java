package ra.model.service;

import ra.model.entity.DiscountThisWeek;

import java.util.List;

public interface DisCountService {
    List<DiscountThisWeek> finAllThisWeek();
    DiscountThisWeek finThisWeekById(int discountThisWeekId);
    DiscountThisWeek saveOrUpdate(DiscountThisWeek discountThisWeek);
    void  deleteThisWeek(int discountThisWeekId);
//    List<Banner> searchByContentContainingOrBanNerId (String content, int bannerId);
    DiscountThisWeek getDiscountThisWeek();
}
