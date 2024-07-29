package com.linkwithjs.simplenotesapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqRes {
    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String email;
    private String role;
    private String password;
    private Object data;

    public static ResponseEntity<ExceptionDTO> successResponse(String message, Object data) {
        return new ResponseEntity<>(new ExceptionDTO(HttpStatus.OK.value(), message, data), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    public static ResponseEntity<ExceptionDTO> Response(String message, String data){
        return new ResponseEntity<>(new ExceptionDTO(HttpStatus.OK.value(), message,data), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }
}
