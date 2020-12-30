package cleanergy.webill;

import java.util.Date;

/**
 *
 * @author Muhammad WANNOUS
 */
public class User {

    private int userId;
    private String gName;
    private String sName;
    private String role;
    private String email;
    private Boolean status;
    private Date dateOfChange;

    public User(int userId, String gName, String sName, String role, String email, Boolean status, Date dateOfChange) {
        this.userId = userId;
        this.gName = gName;
        this.sName = sName;
        this.role = role;
        this.email = email;
        this.status = status;
        this.dateOfChange = dateOfChange;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getDateOfChange() {
        return dateOfChange;
    }

    public void setDateOfChange(Date dateOfChange) {
        this.dateOfChange = dateOfChange;
    }

}
