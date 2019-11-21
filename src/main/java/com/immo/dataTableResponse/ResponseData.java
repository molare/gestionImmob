package com.immo.dataTableResponse;


import java.util.List;

/**
 * Created by MORY on 20/03/2019.
 */
public class ResponseData {
    private boolean status;
    private String message;
    private Object data;
    private Object firstData;
    private Object secondData;
    private Object thirtyData;

    public ResponseData(boolean status, Object data) {
        this.status = status;
        this.data = data;
    }

    public ResponseData(boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }



    /*public ResponseData(boolean status, Object data, Object secondData, Object thirtyData) {
        this.status = status;
        this.data = data;
        this.secondData = secondData;
        this.thirtyData = thirtyData;
    }*/

    public ResponseData(boolean status, Object firstData, Object secondData, Object thirtyData) {
        this.status = status;
        this.firstData = firstData;
        this.secondData = secondData;
        this.thirtyData = thirtyData;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public Object getSecondData() {
        return secondData;
    }

    public void setSecondData(Object secondData) {
        this.secondData = secondData;
    }

    public Object getThirtyData() {
        return thirtyData;
    }

    public void setThirtyData(Object thirtyData) {
        this.thirtyData = thirtyData;
    }

    public Object getFirstData() {
        return firstData;
    }

    public void setFirstData(Object firstData) {
        this.firstData = firstData;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
