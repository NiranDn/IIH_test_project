package com.example.iihtestproject.object;
import java.net.ConnectException;
import java.net.SocketException;

public class AppResponse {

    private int code;
    private boolean status;
    private String message;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Boolean isSuccess() {
        return this.code == 1;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AppResponse{" +
                "code=" + code +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }


    public void setError (int result, Throwable throwable){
        code = result;
        status = false;

        if (throwable == null){
            this.message = "Server error please try later";
        }else if(throwable instanceof SocketException) {
            this.message = "Server error please try later";
        }else if (throwable instanceof ConnectException){
            this.message = "Please check your internet connection and try again!";
        }else{
            this.message = "Server error please try later";
        }
    }
}
