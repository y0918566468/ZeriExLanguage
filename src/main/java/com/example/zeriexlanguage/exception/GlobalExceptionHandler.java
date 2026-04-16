package com.example.zeriexlanguage.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

public class GlobalExceptionHandler {
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDuplicateEntery(Exception e) {
        return ResponseEntity.badRequest().body(Map.of(
            "result", "fail",
            "message", "這個單字已經存在於你的單字庫中囉！"
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.badRequest().body(Map.of(
                "result", "fail",
                "message", "這個單字已經存在於你的單字庫中囉！"
        ));
    }

}
