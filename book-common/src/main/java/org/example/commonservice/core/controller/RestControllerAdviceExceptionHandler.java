package org.example.commonservice.core.controller;

import lombok.RequiredArgsConstructor;
import org.example.commonservice.enums.ErrorConstant;
import org.example.commonservice.exception.HttpException;
import org.example.commonservice.filter.HttpBodyErrorResponse;
import org.example.commonservice.filter.HttpBodyResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.naming.AuthenticationException;
import java.util.*;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestControllerAdviceExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exception, HttpException httpException) {

        String messageError =
            Optional.ofNullable(exception.getMessage())
                .orElse(ErrorConstant.ERROR.getMessage());

        HttpBodyErrorResponse httpBodyErrorResponse =
            createHttpBodyErrorResponse(exception);

        HttpBodyResponse<Object> response =
            HttpBodyResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(messageError)
                .error(httpBodyErrorResponse)
                .build();

        if (exception instanceof MethodArgumentTypeMismatchException) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("NOT_FOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<HttpBodyResponse<Object>> handleAccessDenied(AccessDeniedException ex) {
        HttpBodyErrorResponse errorResponse = HttpBodyErrorResponse.builder().type(ex.getClass().getSimpleName()).code("ACCESS_DENIED").message("Access Denied").error(ex.getMessage()).build();
        HttpBodyResponse<Object> response = HttpBodyResponse.builder().status(HttpStatus.FORBIDDEN.value()).message("Access Denied").error(errorResponse).build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    public ResponseEntity<HttpBodyResponse<Object>> handleMissingServletRequestParameterException(
//        MissingServletRequestParameterException ex) {
//
//        Map<String, String> validationErrors = new HashMap<>();
//        validationErrors.put(ex.getParameterName(), ex.getMessage());
//
//        HttpBodyErrorResponse httpBodyErrorResponse = createHttpBodyErrorResponse(
//            ex,
//            ErrorConstant.VALIDATION_ERROR,
//            List.of(ErrorConstant.VALIDATION_ERROR.getMessage()),
//            validationErrors
//        );
//
//        HttpBodyResponse<Object> response = HttpBodyResponse.builder()
//            .status(HttpStatus.BAD_REQUEST.value())
//            .message("Missing request parameter")
//            .error(httpBodyErrorResponse)
//            .build();
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<HttpBodyResponse<Object>> handleAuthenticationException(AuthenticationException ex) {

        HttpBodyErrorResponse errorResponse = HttpBodyErrorResponse.builder()
            .type(ex.getClass().getSimpleName())
            .code("AUTH_ERROR")
            .message("Authentication failed")
            .error(ex.getMessage())
            .build();

        HttpBodyResponse<Object> response = HttpBodyResponse.builder().status(HttpStatus.UNAUTHORIZED.value())
            .message(ErrorConstant.UNAUTHORIZED.getMessage()).error(errorResponse).build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidBearerTokenException.class)
    public ResponseEntity<HttpBodyResponse<Object>> handleInvalidBearerTokenException(InvalidBearerTokenException ex) {

        HttpBodyErrorResponse errorResponse = HttpBodyErrorResponse.builder()
            .type(ex.getClass().getSimpleName())
            .code("TOKEN_EXPIRED")
            .message("Your token has expired. Please log in again.")
            .error(ex.getMessage())
            .build();

        HttpBodyResponse<Object> response = HttpBodyResponse.builder()
            .status(HttpStatus.UNAUTHORIZED.value())
            .message("Token expired")
            .error(errorResponse)
            .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleValidationExceptions(
        MethodArgumentNotValidException exception) {

        Map<String, String> validationErrors =
            getValidationError(exception.getBindingResult().getAllErrors());

        HttpBodyErrorResponse httpBodyErrorResponse =
            createHttpBodyErrorResponse(
                exception,
                ErrorConstant.VALIDATION_ERROR,
                List.of(ErrorConstant.VALIDATION_ERROR.getMessage()),
                validationErrors);

        HttpBodyResponse<Object> response =
            HttpBodyResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ErrorConstant.VALIDATION_ERROR.getMessage())
                .error(httpBodyErrorResponse)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    private HttpBodyErrorResponse createHttpBodyErrorResponse(
        Exception exception) {
        return createHttpBodyErrorResponse(
            exception, ErrorConstant.UNKNOWN_ERROR, Collections.emptyList(), null);
    }

//    @ExceptionHandler(BadCredentialsException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<HttpBodyResponse<Object>> handleBadCredentials(BadCredentialsException ex) {
//        HttpBodyErrorResponse errorResponse = HttpBodyErrorResponse.builder()
//            .type(ex.getClass().getSimpleName())
//            .code("INVALID_CREDENTIALS")
//            .message("Username or password is incorrect")
//            .error(ex.getMessage())
//            .build();
//
//        HttpBodyResponse<Object> response = HttpBodyResponse.builder()
//            .status(HttpStatus.BAD_REQUEST.getValue())
//            .message("Username or password is incorrect")
//            .error(errorResponse)
//            .build();
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//    }

    private HttpBodyErrorResponse createHttpBodyErrorResponse(
        Exception exception,
        ErrorConstant errorConstantException,
        List<String> errors,
        Map<String, String> bodyRequest) {
        StackTraceElement[] stackTraceElements =
            Optional.ofNullable(exception.getCause())
                .map(Throwable::getStackTrace)
                .orElse(exception.getStackTrace());

        String message = errors.stream().findFirst().orElse(errorConstantException.getMessage());

        return HttpBodyErrorResponse.builder()
            .type(exception.getClass().getSimpleName())
            .code(errorConstantException.name())
            .message(message)
            .error(String.valueOf(Arrays.stream(stackTraceElements).findFirst().orElse(null)))
            .bodyRequestError(bodyRequest)
            .build();
    }

    private Map<String, String> getValidationError(List<ObjectError> objectErrors) {
        Map<String, String> bodyRequestError = new HashMap<>();
        for (ObjectError objectError : objectErrors) {
            if (objectError instanceof FieldError fieldError) {
                bodyRequestError.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }
        return bodyRequestError;
    }

}
