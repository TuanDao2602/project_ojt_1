package ra.model.dto.respon;

import lombok.Data;

import java.util.Date;
@Data
public class ThisWeekReponse {

    private float priceSale;
    private String productName;
    private int quantity;
    private Date startTime;
    private Date endTime;
    private String title;
    private int disCount;

}
