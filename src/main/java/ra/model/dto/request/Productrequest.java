package ra.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Productrequest {

    private String productName;
    private String image;
    private float price;
    private int quantity;
    private String title;
    private int distCount;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDistCount;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDistCount;
    private int catalogId;
}
