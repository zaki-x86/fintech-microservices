package com.example.model.enumeration;

public enum BankStatus {
    WAITING, TIMEOUT, OK, ERROR;


    public static boolean success(BankStatus status){
        return status != null && status.equals(OK);
    }

}
