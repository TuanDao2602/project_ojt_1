package ra.model.dto.respon;

import lombok.Data;

@Data
public class ChangePassword {
    private String oldPassword;
    private String newPassword;
    private String comfimlNewPass;
}
