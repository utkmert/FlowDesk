package com.flowdesk.admin;
import jakarta.validation.Valid;import jakarta.validation.constraints.*;import org.springframework.jdbc.core.JdbcTemplate;import org.springframework.security.access.prepost.PreAuthorize;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/v1/categories") public class CategoryController {
 private final JdbcTemplate jdbc;public CategoryController(JdbcTemplate jdbc){this.jdbc=jdbc;}
 @GetMapping public List<Category> list(){return jdbc.query("select name,active from categories order by name",(rs,n)->new Category(rs.getString("name"),rs.getBoolean("active")));}
 @PostMapping @PreAuthorize("hasRole('ADMIN')") public Category create(@Valid @RequestBody NewCategory r){if(jdbc.queryForObject("select count(*) from categories where name=?",Long.class,r.name())>0)throw new com.flowdesk.core.ConflictException("Kategori adı zaten mevcut.");jdbc.update("insert into categories(name,active) values (?,true)",r.name());return new Category(r.name(),true);}
 @PatchMapping("/{name}") @PreAuthorize("hasRole('ADMIN')") public Category active(@PathVariable String name,@RequestBody Active r){int changed=jdbc.update("update categories set active=? where name=?",r.active(),name);if(changed==0)throw new com.flowdesk.core.NotFoundException();return new Category(name,r.active());}
 public record NewCategory(@NotBlank @Size(max=80)String name){}public record Active(boolean active){}public record Category(String name,boolean active){}
}
