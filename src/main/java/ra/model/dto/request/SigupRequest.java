package ra.model.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;
@Data
@Setter
@Getter
public class SigupRequest {
    private String userName;
    private String firstName;
    private String lastName;
    private String avartar;
    private String adress;
    private String password;
    private String email;
    private String phone;
    private Date created;
    private boolean userStatus;
    private Set<String> listRoles;

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAvartar() {
        return avartar;
    }

    public String getAdress() {
        return adress;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Date getCreated() {
        return created;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public Set<String> getListRoles() {
        return listRoles;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAvartar(String avartar) {
        this.avartar = avartar;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    public void setListRoles(Set<String> listRoles) {
        this.listRoles = listRoles;
    }
}
