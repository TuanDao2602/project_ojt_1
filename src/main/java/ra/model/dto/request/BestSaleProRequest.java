package ra.model.dto.request;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class BestSaleProRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
