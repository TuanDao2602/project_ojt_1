package ra.model.dto.respon;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrdersReponse {
    private String status;
    private float price;
    private String userName;


}
