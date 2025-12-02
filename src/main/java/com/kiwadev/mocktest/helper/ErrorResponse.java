package com.kiwadev.mocktest.helper;

import com.kiwadev.mocktest.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private ErrorData error;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ErrorData {
        private ErrorCode code;
        private String    message;
        private Object details;
    }
}
