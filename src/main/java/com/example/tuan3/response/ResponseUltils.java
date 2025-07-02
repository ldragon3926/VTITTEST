package com.example.tuan3.response;
import org.springframework.http.ResponseEntity;

public class ResponseUltils{
   public static  <T> ResponseEntity<ApiResponse<T>> success(T data1, String code, String message){
       return ResponseEntity.ok(new ApiResponse<>(code, message, data1));
   }

    public static <T> ResponseEntity<ApiResponse<T>> error( String code, String message){
        return ResponseEntity.badRequest().body(new ApiResponse<>(code, message, null));
    }



}
