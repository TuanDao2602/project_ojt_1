package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Orders")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId")
    private int orDerId;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String  lastName;
    @Column(name = "Total")
    private float total;
    @Column(name = "Status")
    private int status;
    @Column(name = "note")
    private String note;
    @Column(name = "Country")
    private String country;
    @Column(name = "Adress")
    private String adress;
    @Column(name = "City")
    private String city;
    @Column(name = "PostCode")
    private int postCode;
    @Column(name = "CreateDate")
    private LocalDateTime createDate;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "orders",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrDerDetail> orDerDetailList=new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UserId")
    @JsonIgnore
    private Users users;







}
