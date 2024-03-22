package com.al_makkah_traders_app.utility;

public class BillCreationResult {
    private boolean success;
    private String billId;

    public BillCreationResult(boolean success, String billId) {
        this.success = success;
        this.billId = billId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }
}
