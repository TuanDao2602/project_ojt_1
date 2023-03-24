package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.DiscountThisWeek;
import ra.model.repository.DiscountThisWeekRepository;
import ra.model.service.DisCountService;

import java.util.List;
@Service
public class DiscountThisWeekImp implements DisCountService {
    @Autowired
    private DiscountThisWeekRepository discountThisWeekRepository;
    @Override
    public List<DiscountThisWeek> finAllThisWeek() {
        return discountThisWeekRepository.findAll();
    }

    @Override
    public DiscountThisWeek finThisWeekById(int discountThisWeekId) {
        return discountThisWeekRepository.findById(discountThisWeekId).get();
    }

    @Override
    public DiscountThisWeek saveOrUpdate(DiscountThisWeek discountThisWeek) {
        return discountThisWeekRepository.save(discountThisWeek);
    }

    @Override
    public void deleteThisWeek(int discountThisWeekId) {
        discountThisWeekRepository.findById(discountThisWeekId);

    }

    @Override
    public DiscountThisWeek getDiscountThisWeek() {
        return discountThisWeekRepository.getDisscount();
    }
}
