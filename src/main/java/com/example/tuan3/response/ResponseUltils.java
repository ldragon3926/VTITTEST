package com.example.tuan3.response;
import com.sun.jna.WString;
import org.springframework.http.ResponseEntity;

public class ResponseUltils{
   public static  <T> ResponseEntity<ApiResponse<T>> success(T data1, String message){
       String pass = "Pass";
       return ResponseEntity.ok(new ApiResponse<>(pass, message, data1));
   }

    public static <T> ResponseEntity<ApiResponse<T>> error( String code, String message){
        return ResponseEntity.badRequest().body(new ApiResponse<>(code, message, null));
    }



}
