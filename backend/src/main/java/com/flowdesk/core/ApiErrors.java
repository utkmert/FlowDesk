package com.flowdesk.core;
import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import java.util.*;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException; import org.springframework.http.converter.HttpMessageNotReadableException;
@RestControllerAdvice public class ApiErrors {
 @ExceptionHandler(TicketServiceException.class) ResponseEntity<Map<String,Object>> domain(TicketServiceException e){return ResponseEntity.status(e.status).body(Map.of("message",e.getMessage(),"status",e.status.value()));}
 @ExceptionHandler(ObjectOptimisticLockingFailureException.class) ResponseEntity<Map<String,Object>> conflict(Exception e){return ResponseEntity.status(409).body(Map.of("message","Kayıt başka bir kullanıcı tarafından değiştirildi. Yenileyip tekrar deneyin.","status",409));}
 @ExceptionHandler(AccessDeniedException.class) ResponseEntity<Map<String,Object>> forbidden(Exception e){return ResponseEntity.status(403).body(Map.of("message","Bu işlem için yetkiniz yok.","status",403));}
 @ExceptionHandler(HttpMessageNotReadableException.class) ResponseEntity<Map<String,Object>> unreadable(Exception e){return ResponseEntity.badRequest().body(Map.of("message","İstek gövdesi geçersiz.","status",400));}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<Map<String,Object>> invalid(MethodArgumentNotValidException e){return ResponseEntity.badRequest().body(Map.of("message","İstek doğrulanamadı","errors",e.getBindingResult().getFieldErrors().stream().map(x->x.getField()+": "+x.getDefaultMessage()).toList()));}
 @ExceptionHandler(Exception.class) ResponseEntity<Map<String,Object>> other(Exception e){return ResponseEntity.internalServerError().body(Map.of("message","Beklenmeyen sunucu hatası"));}
}
