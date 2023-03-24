package ra.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BannerId")
    private int banNerId;
    @Column(name = "image")
    private String  image;
    @Column(name = "content")
    private String  content;
    @Column(name = "priority")
    private int  priority;
    @Column(name = "status")
    private boolean  status;

}
