package com.ysyesilyurt.Rest;

import com.ysyesilyurt.Exception.PotentialPlaylistRuntimeException;
import com.ysyesilyurt.Exception.ResourceNotFoundException;
import com.ysyesilyurt.Rest.Response.RestApiResponseBodyBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class RestApiControllerAdvice {

    @Autowired
    private RestApiResponseBodyBuilder responseBodyBuilder;

    @ExceptionHandler(PotentialPlaylistRuntimeException.class)
    public ResponseEntity<?> runtimeException(PotentialPlaylistRuntimeException exception) {
        log.error(exception.getMessage(), exception);
        RestApiResponseBody<Object> responseBody =
                this.responseBodyBuilder.errorBody(exception.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> notFound(ResourceNotFoundException exception) {
        log.error(exception.getMessage());
        RestApiResponseBody<Object> responseBody = this.responseBodyBuilder.errorBody(exception.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> argumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        log.error(exception.getMessage(), exception);
        RestApiResponseBody<Object> responseBody = this.responseBodyBuilder.errorBody(exception);
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> missingParameter(MissingServletRequestParameterException exception) {
        log.error(exception.getMessage(), exception);
        RestApiResponseBody<Object> responseBody = this.responseBodyBuilder.errorBody(exception);
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknownException(Exception exception) {
        log.error(exception.getMessage(), exception);
        RestApiResponseBody<Object> responseBody = this.responseBodyBuilder.errorBody(exception.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
