package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Banner;
import ra.model.entity.Product;
import ra.model.service.BannerService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("api/v1/auth/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;
    @GetMapping
    public List<Banner>getALLBanner(){
        return bannerService.finALLBanner();
    }
    @GetMapping("/{bannerId}")
    public Banner getById(@PathVariable("bannerId")int bannerId){
        return bannerService.finBanerById(bannerId);
    }
    @PostMapping("/create")
    public Banner create(@RequestBody Banner banner){
        Banner bannerNew =new Banner();
        bannerNew.setContent(banner.getContent());
        bannerNew.setImage(banner.getImage());
        bannerNew.setPriority(banner.getPriority());
        bannerNew.setStatus(true);
        return bannerService.saveOrUpdate(bannerNew);
    }
    @PutMapping("/update/{bannerId}")
    public Banner update(@PathVariable("bannerId")int bannerId,@RequestBody Banner banner){
        Banner bannerupdate = bannerService.finBanerById(bannerId);
        bannerupdate.setStatus(banner.isStatus());
        bannerupdate.setPriority(banner.getPriority());
        bannerupdate.setImage(banner.getImage());
        bannerupdate.setContent(banner.getContent());
        return bannerService.saveOrUpdate(bannerupdate);
    }
    @DeleteMapping("/delete/{bannerId}")
    public ResponseEntity<?> delete(@PathVariable("bannerId")int bannerId){
        bannerService.deleteBanner(bannerId);
        return ResponseEntity.ok("đã xóa thành công");
    }
    @GetMapping("/search")
    public ResponseEntity<List<Banner>>SearchByNameOrId(@RequestParam("content")String content,
                                                         @RequestParam("bannerId")int bannerId){
        List<Banner>bannerList=bannerService.searchByContentContainingOrBanNerId(content,bannerId);
        return new ResponseEntity<>(bannerList, HttpStatus.OK);

    }
}
