/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanergy.webill;

import java.sql.Date;

/**
 *
 * @author Kanfitine
 */
public class Assignment {
    private Date startDate;
    private Date endDate;
    private int userId;
    private int meterId;
    private int assignmentId;
    private String userGivenName;
    private String userEmail;
    private String meterAdress;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMeterId() {
        return meterId;
    }

    public void setMeterId(int meterId) {
        this.meterId = meterId;
    }

    public String getUserGivenName() {
        return userGivenName;
    }

    public void setUserGivenName(String userGivenName) {
        this.userGivenName = userGivenName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getMeterAdress() {
        return meterAdress;
    }

    public void setMeterAdress(String meterAdress) {
        this.meterAdress = meterAdress;
    }

    public Assignment(Date startDate, Date endDate, int userId, int meterId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.meterId = meterId;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Assignment(int userId, int meterId, int assignmentId, String userGivenName, String userEmail, String meterAdress) {
        this.userId = userId;
        this.meterId = meterId;
        this.assignmentId = assignmentId;
        this.userGivenName = userGivenName;
        this.userEmail = userEmail;
        this.meterAdress = meterAdress;
    }

    
    
}
