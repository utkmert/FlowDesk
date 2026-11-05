package com.flowdesk.core;
import com.flowdesk.security.*; import org.springframework.context.annotation.*; import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.core.userdetails.*; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.security.web.SecurityFilterChain; import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
@Configuration @EnableMethodSecurity public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean UserDetailsService userDetailsService(UserRepository repo){return username->{var u=repo.findByUsername(username).filter(UserAccount::isActive).orElseThrow(()->new UsernameNotFoundException("Kullanıcı bulunamadı"));return User.withUsername(u.getUsername()).password(u.getPasswordHash()).authorities(u.getRoles().stream().map(r->"ROLE_"+r).toArray(String[]::new)).build();};}
 @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  http.csrf(c->c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
   .authorizeHttpRequests(a->a.requestMatchers("/api/v1/auth/csrf","/api/v1/auth/me","/actuator/health","/swagger-ui/**","/v3/api-docs/**").permitAll().anyRequest().authenticated())
   .formLogin(f->f.loginProcessingUrl("/api/v1/auth/login").successHandler((req,res,auth)->res.setStatus(204)).failureHandler((req,res,e)->{res.setStatus(401);res.setContentType("application/json");res.getWriter().write("{\"message\":\"Kullanıcı adı veya parola hatalı\"}");}))
   .logout(l->l.logoutUrl("/api/v1/auth/logout").logoutSuccessHandler((req,res,auth)->res.setStatus(204)))
   .exceptionHandling(e->e.authenticationEntryPoint((req,res,ex)->res.sendError(401)));
  return http.build();
 }
}
