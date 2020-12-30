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
public class Notification {
    private int noteId;
    private int billId;
    private String message;
    private boolean status;
    private Date timeStamp;

    public Notification(int noteId, int billId, String message, boolean status, Date timeStamp) {
        this.noteId = noteId;
        this.billId = billId;
        this.message = message;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
    
}
