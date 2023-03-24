package ra.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ThisWeekCart {
    private int productId;
    private int quantity;

}
