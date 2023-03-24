package ra.model.dto.respon;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDetailReponse {
    private int orderDetailId;
    private float price;
    private String userName;
    private String phone;
    private String firtName;
    private String lastName;
    private String adress;
}
