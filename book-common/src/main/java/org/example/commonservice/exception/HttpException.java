package org.example.commonservice.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.commonservice.enums.ErrorConstant;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = false)
public class HttpException extends RuntimeException {

    private HttpStatus httpStatus;
    private ErrorConstant error;

    public HttpException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.error = ErrorConstant.UNKNOWN_ERROR;
    }

}
