package com.fpt.capstone.tourism.exception.common;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessException extends RuntimeException {
    protected int httpCode;
    protected String responseMessage;
    protected Object responseData;

    public static BusinessException of(String responseMessage, Exception e) {
        return BusinessException.builder()
                .httpCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .responseMessage(responseMessage)
                .responseData(e.getLocalizedMessage())
                .build();
    }

    public static BusinessException of(String responseMessage) {
        return BusinessException.builder()
                .httpCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .responseMessage(responseMessage)
                .responseData(null)
                .build();
    }

}