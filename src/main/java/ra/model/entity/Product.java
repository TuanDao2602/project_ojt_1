package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "product")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productID")
    private int productID;
    @Column(name = "productName",unique = true,nullable = false)
    private String productName;
    @Column(name = "CreateDate")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate createDate;
    @Column(name = "quantity",nullable = false)
    private int quantity;
    @Column(name = "Image",nullable = false)
    private String image ;
    @Column(name = "title")
    private String title;
    @Column(name = "status")
    private boolean status=true;
    @Column(name = "price")
    private float priceProduct;
    @Column(name = "DistCount")
    private int distCount;
    @Column(name = "StartDistCount")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDistCount;

    @Column(name = "EndDistCount")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDistCount;
    @ManyToOne
    @JoinColumn(name = "catalogID")
    private Catalog catalog;

//    @OneToMany(mappedBy = "product")
//    private List<ItemDiscountThisWeek> itemDiscountThisWeekList=new ArrayList<>();
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<OrDerDetail> orDerDetailList=new ArrayList<>();
}
