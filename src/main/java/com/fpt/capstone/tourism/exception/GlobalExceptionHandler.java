package com.fpt.capstone.tourism.exception;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<GeneralResponse<Object>> handleOverallException(Exception ex) {
        log.error("Error happened", ex);
        return ResponseEntity.internalServerError().body(GeneralResponse.of(ex));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<GeneralResponse<Object>> handleBusinessException(BusinessException be) {
        log.error("Business error happened", be);
        return ResponseEntity.status(be.getHttpCode()).body(GeneralResponse.of(be));
    }
}
