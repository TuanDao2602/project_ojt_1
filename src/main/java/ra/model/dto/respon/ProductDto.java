package ra.model.dto.respon;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private int productId;
    private String productName;
    private String catalogName;
    private String image;
    private float price;
    private int quantity;
    private String tittle;
    private boolean status=true;
    private int distCount;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate createDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDistCount;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDistCount;

}
