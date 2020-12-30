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
public class Bill {
    private int billId;
    private int assignementId;
    private String imageFilePath;
    private String billFilePath;
    private String previousReading;
    private String currentReading;
    private boolean verifiedByConsumer;
    private boolean verifiedByAdmin;
    private String paymentAmount;
    private boolean paid;
    private Date dateOfPayment;
    private int handlingAdminId;
    private String userMail;
    private String givenName;

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getAssignementId() {
        return assignementId;
    }

    public void setAssignementId(int assignementId) {
        this.assignementId = assignementId;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public String getBillFilePath() {
        return billFilePath;
    }

    public void setBillFilePath(String billFilePath) {
        this.billFilePath = billFilePath;
    }

    public String getPreviousReading() {
        return previousReading;
    }

    public void setPreviousReading(String previousReading) {
        this.previousReading = previousReading;
    }

    public String getCurrentReading() {
        return currentReading;
    }

    public void setCurrentReading(String currentReading) {
        this.currentReading = currentReading;
    }

    public boolean isVerifiedByConsumer() {
        return verifiedByConsumer;
    }

    public void setVerifiedByConsumer(boolean verifiedByConsumer) {
        this.verifiedByConsumer = verifiedByConsumer;
    }

    public boolean isVerifiedByAdmin() {
        return verifiedByAdmin;
    }

    public void setVerifiedByAdmin(boolean verifiedByAdmin) {
        this.verifiedByAdmin = verifiedByAdmin;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public int getHandlingAdminId() {
        return handlingAdminId;
    }

    public void setHandlingAdminId(int handlingAdminId) {
        this.handlingAdminId = handlingAdminId;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public Bill(int billId, int assignementId, String imageFilePath, String billFilePath, String previousReading, String currentReading, boolean verifiedByConsumer, boolean verifiedByAdmin, String paymentAmount, boolean paid, Date dateOfPayment, int handlingAdminId) {
        this.billId = billId;
        this.assignementId = assignementId;
        this.imageFilePath = imageFilePath;
        this.billFilePath = billFilePath;
        this.previousReading = previousReading;
        this.currentReading = currentReading;
        this.verifiedByConsumer = verifiedByConsumer;
        this.verifiedByAdmin = verifiedByAdmin;
        this.paymentAmount = paymentAmount;
        this.paid = paid;
        this.dateOfPayment = dateOfPayment;
        this.handlingAdminId = handlingAdminId;
    }
    
    public Bill(int billId, int assignementId, String imageFilePath, String billFilePath, String previousReading, String currentReading, boolean verifiedByConsumer, boolean verifiedByAdmin, String paymentAmount, boolean paid, Date dateOfPayment, int handlingAdminId, String userMail, String givenName) {
        this.billId = billId;
        this.assignementId = assignementId;
        this.imageFilePath = imageFilePath;
        this.billFilePath = billFilePath;
        this.previousReading = previousReading;
        this.currentReading = currentReading;
        this.verifiedByConsumer = verifiedByConsumer;
        this.verifiedByAdmin = verifiedByAdmin;
        this.paymentAmount = paymentAmount;
        this.paid = paid;
        this.dateOfPayment = dateOfPayment;
        this.handlingAdminId = handlingAdminId;
        this.userMail = userMail;
        this.givenName = givenName;
    }
    
}
