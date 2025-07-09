package org.example.commonservice.exception;

import org.springframework.http.HttpStatus;

public class UserException extends HttpException{

    public UserException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
    public static UserException notFoundUsername(){
        return new UserException(HttpStatus.NOT_FOUND, "Username not found");
    }
}
