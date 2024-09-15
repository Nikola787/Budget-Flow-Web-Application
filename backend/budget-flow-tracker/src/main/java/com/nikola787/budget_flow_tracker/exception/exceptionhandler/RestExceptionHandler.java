package com.nikola787.budget_flow_tracker.exception.exceptionhandler;

import com.nikola787.budget_flow_tracker.exception.*;
import jakarta.mail.MessagingException;
import org.hibernate.StaleObjectStateException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String RESPONSE_FIELD_ERROR = "error";
    private static final String RESPONSE_FIELD_MESSAGES = "messages";
    private static final String RESPONSE_FIELD_RECORD = "record";


    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleMessagingException(RequestNotAuthorizedException ex, WebRequest request){        var responseBody = new HashMap<String, Object>();
        var errors = new HashMap<String, Object>();
        errors.put(RESPONSE_FIELD_RECORD, ex.getMessage());
        responseBody.put(RESPONSE_FIELD_ERROR, ErrorType.REQUEST_NOT_AUTHORIZED);
        responseBody.put(RESPONSE_FIELD_MESSAGES, errors);
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(RequestNotAuthorizedException.class)
    protected ResponseEntity<Object> handleRequestNotAuthorized(RequestNotAuthorizedException ex, WebRequest request){
        var responseBody = new HashMap<String, Object>();
        var errors = new HashMap<String, Object>();
        errors.put(RESPONSE_FIELD_RECORD, ex.getMessage());
        responseBody.put(RESPONSE_FIELD_ERROR, ErrorType.REQUEST_NOT_AUTHORIZED);
        responseBody.put(RESPONSE_FIELD_MESSAGES, errors);
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(RecordNotCurrentVersionException.class)
    protected ResponseEntity<Object> handleRecordNotCurrentVersion(RecordNotCurrentVersionException ex, WebRequest request){
        var responseBody = new HashMap<String, Object>();
        var errors = new HashMap<String, Object>();
        errors.put(RESPONSE_FIELD_RECORD, ex.getMessage());
        responseBody.put(RESPONSE_FIELD_ERROR, ErrorType.RECORD_NOT_CURRENT_VERSION);
        responseBody.put(RESPONSE_FIELD_MESSAGES, errors);
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(StaleObjectStateException.class)
    protected ResponseEntity<Object> handleRecordNotCurrentVersion(StaleObjectStateException ex, WebRequest request){
        var responseBody = new HashMap<String, Object>();
        var errors = new HashMap<String, Object>();
        errors.put(RESPONSE_FIELD_RECORD, ex.getMessage());
        responseBody.put(RESPONSE_FIELD_ERROR, ErrorType.RECORD_NOT_CURRENT_VERSION);
        responseBody.put(RESPONSE_FIELD_MESSAGES, errors);
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    protected ResponseEntity<Object> handleRecordNotFound(RecordNotFoundException ex, WebRequest request){
        var responseBody = new HashMap<String, Object>();
        var errors = new HashMap<String, Object>();
        errors.put(RESPONSE_FIELD_RECORD, ex.getMessage());
        responseBody.put(RESPONSE_FIELD_ERROR, ErrorType.RECORD_NOT_FOUND);
        responseBody.put(RESPONSE_FIELD_MESSAGES, errors);

        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(RequestNotValidException.class)
    protected ResponseEntity<Object> handleRequestNotValid(RequestNotValidException ex, WebRequest request){
        var responseBody = new HashMap<String, Object>();
        var errors = new HashMap<String, Object>();
        errors.put(RESPONSE_FIELD_RECORD, ex.getMessage());
        responseBody.put(RESPONSE_FIELD_ERROR, ErrorType.REQUEST_NOT_VALID);
        responseBody.put(RESPONSE_FIELD_MESSAGES, errors);
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({RecordNotValidException.class})
    protected ResponseEntity<Object> handleRecordNotValid(RecordNotValidException ex, WebRequest request) {
        var responseBody = new HashMap<String, Object>();
        var errors = new HashMap<String, Object>();
        errors.put(RESPONSE_FIELD_RECORD, ex.getMessage());
        responseBody.put(RESPONSE_FIELD_ERROR, ErrorType.RECORD_NOT_VALID);
        responseBody.put(RESPONSE_FIELD_MESSAGES, errors);
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var responseBody = new HashMap<String, Object>();
        var errors = new HashMap<String, Object>();
        errors.put(RESPONSE_FIELD_RECORD, ex.getMessage());
        responseBody.put(RESPONSE_FIELD_ERROR, ErrorType.MESSAGE_NOT_READABLE);
        responseBody.put(RESPONSE_FIELD_MESSAGES, errors);
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var responseBody = new HashMap<String, Object>();
        var errors = new HashMap<String, Object>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        responseBody.put(RESPONSE_FIELD_MESSAGES, errors);
        responseBody.put(RESPONSE_FIELD_ERROR, ErrorType.METHOD_ARGUMENT_NOT_VALID);
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

}
