package ra.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DetailUpdateQuantityReponse {
    private int detaiId;
    private int quantity;

}
