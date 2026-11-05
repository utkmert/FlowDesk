package com.flowdesk.security;
import jakarta.persistence.*; import java.util.*;
@Entity @Table(name="users") public class UserAccount {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=80) private String username;
 @Column(nullable=false,length=160) private String fullName;
 @Column(nullable=false,length=100) private String passwordHash;
 @Column(nullable=false) private boolean active=true;
 @ElementCollection(fetch=FetchType.EAGER) @CollectionTable(name="user_roles",joinColumns=@JoinColumn(name="user_id")) @Column(name="role_name",length=30) private Set<String> roles=new HashSet<>();
 protected UserAccount(){} public UserAccount(String u,String n,String p,Set<String> r){username=u;fullName=n;passwordHash=p;roles=r;}
 public Long getId(){return id;} public String getUsername(){return username;} public String getFullName(){return fullName;} public String getPasswordHash(){return passwordHash;} public boolean isActive(){return active;} public Set<String> getRoles(){return roles;} public void setActive(boolean value){active=value;} public void setRoles(Set<String> value){roles=value;}
}
