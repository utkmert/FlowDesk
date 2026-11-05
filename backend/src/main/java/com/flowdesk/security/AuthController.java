package com.flowdesk.security;
import jakarta.servlet.http.HttpServletRequest; import org.springframework.security.core.Authentication; import org.springframework.security.web.csrf.CsrfToken; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/v1/auth") public class AuthController {
 private final UserRepository users; public AuthController(UserRepository users){this.users=users;}
 @GetMapping("/csrf") public Map<String,String> csrf(CsrfToken token){return Map.of("token",token.getToken(),"headerName",token.getHeaderName());}
 @GetMapping("/me") public Map<String,Object> me(Authentication auth){if(auth==null)return Map.of("authenticated",false);var u=users.findByUsername(auth.getName()).orElseThrow();return Map.of("authenticated",true,"username",u.getUsername(),"fullName",u.getFullName(),"roles",u.getRoles());}
}
