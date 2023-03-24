package ra.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "orDerDetail")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrDerDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detaiId")
    private int detaiId;
    @Column(name = "price")
    private float price;
//    @Column(name = "CreDate")
//    private Date creDate;
    @Column(name = "Quantity")

    private int quantity;
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "OrderId")
    private Orders orders;
}