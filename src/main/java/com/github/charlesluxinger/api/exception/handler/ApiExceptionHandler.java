package com.github.charlesluxinger.api.exception.handler;

import com.github.charlesluxinger.api.exception.ApiExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null) {
            var detail = "An unexpected internal server error has occurred. Try again and if the problem persists, contact your system administrator.";
            body = exceptionResponseBuilder(detail, INTERNAL_SERVER_ERROR, request);
            status = INTERNAL_SERVER_ERROR;
        }

        logger.error(Arrays.toString(ex.getStackTrace()));

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var response = exceptionResponseBuilder("One or more fields are invalid. Fill in correctly and try again.", status, request);
        return this.handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        var status = INTERNAL_SERVER_ERROR;
        var detail = "An unexpected internal system error has occurred. Try again and if the problem persists, contact your system administrator.";
        var body = exceptionResponseBuilder(detail, status, request);

        logger.error(Arrays.toString(ex.getStackTrace()));

        return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
    }

    private ApiExceptionResponse exceptionResponseBuilder(String detail, HttpStatus status, WebRequest request) {
        return ApiExceptionResponse.builder()
                                   .detail(detail)
                                   .status(status.value())
                                   .title(status.getReasonPhrase())
                                   .path(((ServletWebRequest) request).getRequest().getRequestURI())
                                   .build();
    }

}