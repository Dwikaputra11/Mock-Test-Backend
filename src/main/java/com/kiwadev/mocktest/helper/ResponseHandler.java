package com.kiwadev.mocktest.helper;

import com.kiwadev.mocktest.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public record ResponseHandler() {


    public static final String STATUS = "status";
    public static final String MESSAGE = "message";
    public static final String DATA = "data";
    public static final String TOTAL_RECORDS = "total";
    public static final String PAGE = "page";
    public static final String PAGE_SIZE = "page_size";


    public static ResponseEntity<Object> generateExceptionResponseOld(String message, HttpStatus status, String uri) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put(MESSAGE, message);
        map.put(STATUS, status.value());
        map.put(DATA, uri);
        map.put(TOTAL_RECORDS, 0);

        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put(MESSAGE, message);
        map.put(STATUS, status.value());

        if(responseObj == null){
            map.put(DATA, null);
            map.put(PAGE, 0);
            map.put(PAGE_SIZE, 0);
            map.put(TOTAL_RECORDS, 0);
        } else if(responseObj instanceof List<?> list){
            map.put(DATA, responseObj);
            map.put(PAGE, 0);
            map.put(PAGE_SIZE, 0);
            map.put(TOTAL_RECORDS, list.size());
        }else {
            var list = new ArrayList<>();
            list.add(responseObj);
            map.put(DATA, list);
            map.put(PAGE, 0);
            map.put(PAGE_SIZE, 0);
            map.put(TOTAL_RECORDS, list.size());
        }

        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generatePagingResponse(String message, HttpStatus status, Page<?> responseObj) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put(STATUS, status.value());
        map.put(MESSAGE, message);

        if(responseObj == null){
            map.put(DATA, null);
            map.put(PAGE, 0);
            map.put(PAGE_SIZE, 0);
            map.put(TOTAL_RECORDS, 0);
        } else {
            map.put(DATA, responseObj.getContent());
            map.put(PAGE, responseObj.getNumber() + 1);
            map.put(PAGE_SIZE, responseObj.getSize());
            map.put(TOTAL_RECORDS, responseObj.getTotalElements());
        }


        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateExceptionResponse(ErrorCode code, String message, Object details, HttpStatus status) {
        ErrorResponse response = ErrorResponse.builder()
                .error(ErrorResponse.ErrorData.builder()
                        .code(code)
                        .message(message)
                        .details(details)
                        .build())
                .build();

        return ResponseEntity.status(status).body(response);
    }
}
