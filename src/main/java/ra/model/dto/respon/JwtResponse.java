package ra.model.dto.respon;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private int userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String avartar;

    private String adress;
    private String email;
    private String phone;
    private  boolean userStatus;
    private List<String> listRoles;

    public JwtResponse(String token, int userId, String userName, String firstName, String lastName, String adress, String email, String phone, boolean userStatus, List<String> listRoles) {
        this.token = token;
        this.userId = userId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;

        this.adress = adress;
        this.email = email;
        this.phone = phone;
        this.userStatus = userStatus;
        this.listRoles = listRoles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getListRoles() {
        return listRoles;
    }

    public void setListRoles(List<String> listRoles) {
        this.listRoles = listRoles;
    }
}
