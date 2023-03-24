package ra.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@Table(name = "DiscountThisWeek")
public class DiscountThisWeek {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JoinColumn(name = "DiscountThisWeekId")
        private int discountThisWeekId;
        @JoinColumn(name = "DisCountThisWeek")
        private int disCount;
        @JoinColumn(name = "StarTime", nullable = false)
        private Date starTime;
        @JoinColumn(name = "EndTime", nullable = false)
        private Date endTime;
        @JoinColumn(name = "Title")
        private String  title;
        @JoinColumn(name = "Status")
        private boolean status;
//        @OneToMany(mappedBy = "discountThisWeek")
//        private List<Product> listProduct = new ArrayList<>();
         @ManyToMany(fetch = FetchType.EAGER)
         @JoinTable(name = "Discount_product", joinColumns = @JoinColumn(name = "DiscountThisWeekId"), inverseJoinColumns = @JoinColumn(name = "productID"))
         private List<Product> listProduct = new ArrayList<>();


}
