package ra.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class OrderUpdateRequest {
    private int orDerId;
    private int status;
}
