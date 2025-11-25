package com.kiwadev.mocktest.helper;

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
    public static final String TOTAL_RECORDS = "totalRecords";


    public static ResponseEntity<Object> generateExceptionResponse(String message, HttpStatus status, String uri) {
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
            map.put(TOTAL_RECORDS, 0);
        } else if(responseObj instanceof List<?> list){
            map.put(TOTAL_RECORDS, list.size());
            map.put(DATA, responseObj);
        }else {
            var list = new ArrayList<>();
            list.add(responseObj);
            map.put(DATA, list);
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
            map.put(TOTAL_RECORDS, 0);
            map.put("pageNumber", 0);
            map.put("pageSize", 0);
        } else {
            map.put(TOTAL_RECORDS, responseObj.getTotalElements());
            map.put("pageNumber", responseObj.getNumber());
            map.put("pageSize", responseObj.getSize());
            map.put(DATA, responseObj.getContent());
        }


        return new ResponseEntity<>(map, status);
    }
}
