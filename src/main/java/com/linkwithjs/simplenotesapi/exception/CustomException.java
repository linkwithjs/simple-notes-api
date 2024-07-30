package com.linkwithjs.simplenotesapi.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class CustomException extends RuntimeException{
    private String requestId;
    private String message;
    private int errorCode;


    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    public CustomException(String message, int errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    public CustomException(String requestId, String message, int errorCode) {
        super(message);
        this.requestId = requestId;
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getRequestId() {
        return this.requestId;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

}
