package com.flowdesk.ticket;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="ticket_audit_logs") public class TicketAudit {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(nullable=false,length=24) private String ticketNumber; @Column(nullable=false,length=40) private String action; @Column(nullable=false,length=80) private String actor; @Column(nullable=false) private Instant occurredAt; @Column(columnDefinition="text") private String previousValue; @Column(columnDefinition="text") private String newValue; @Column(columnDefinition="text") private String reason;
 protected TicketAudit(){} public TicketAudit(String no,String action,String actor,Instant at,String prev,String next,String reason){ticketNumber=no;this.action=action;this.actor=actor;occurredAt=at;previousValue=prev;newValue=next;this.reason=reason;}
 public Long getId(){return id;} public String getTicketNumber(){return ticketNumber;} public String getAction(){return action;} public String getActor(){return actor;} public Instant getOccurredAt(){return occurredAt;} public String getPreviousValue(){return previousValue;} public String getNewValue(){return newValue;} public String getReason(){return reason;}
}
