package cleanergy.webill;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kanfitine
 */
public class Meter {
    private int meterId;
    private String qrCode;
    private String longitude;
    private String latitude;
    private String reading;
    private String address;
    private boolean status;

    public int getMeterId() {
        return meterId;
    }

    public void setMeterId(int meterId) {
        this.meterId = meterId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Meter(int meterId, String qrCode, String longitude, String latitude, String reading, String address, boolean status) {
        this.meterId = meterId;
        this.qrCode = qrCode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.reading = reading;
        this.address = address;
        this.status = status;
    }
    
    
}
