package ra.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class DisCountUpdateRequest {
    private int disCount;
    private Date starTime;
    private Date endTime;
    private String title;
    private boolean status;
    private List<Integer> listProductId;
}
