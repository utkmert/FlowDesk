package com.flowdesk.ticket;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="notifications") public class Notification {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(nullable=false,length=80) private String username; @Column(nullable=false,length=40) private String type; @Column(nullable=false,length=500) private String message; @Column(nullable=false,length=24) private String ticketNumber; @Column(nullable=false) private Instant createdAt; @Column(name="is_read",nullable=false) private boolean read=false;
 protected Notification(){} public Notification(String user,String type,String msg,String no,Instant at){username=user;this.type=type;message=msg;ticketNumber=no;createdAt=at;} public Long getId(){return id;} public String getUsername(){return username;} public String getType(){return type;} public String getMessage(){return message;} public String getTicketNumber(){return ticketNumber;} public Instant getCreatedAt(){return createdAt;} public boolean isRead(){return read;} public void markRead(){read=true;}
}
