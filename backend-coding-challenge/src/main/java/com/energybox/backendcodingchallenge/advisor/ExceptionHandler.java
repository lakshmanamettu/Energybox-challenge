package com.energybox.backendcodingchallenge.advisor;

import com.energybox.backendcodingchallenge.exception.BusinessException;
import com.energybox.backendcodingchallenge.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid
            (MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>( ex.getMessage(),headers,HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = BusinessException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public BusinessException handleBusinessException(BusinessException be){
        return be;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public NotFoundException handleNotFoundException(NotFoundException ne){
        return ne;
    }
}
