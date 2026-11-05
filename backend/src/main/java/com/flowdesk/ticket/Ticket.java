package com.flowdesk.ticket;
import com.flowdesk.security.UserAccount; import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="tickets") public class Ticket {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=24) private String ticketNumber; @Column(nullable=false,length=180) private String title; @Column(nullable=false,columnDefinition="text") private String description;
 @Column(nullable=false,length=80) private String category; @Column(nullable=false,length=2) private String priority; @Column(nullable=false,length=32) private String status;
 @ManyToOne(optional=false) @JoinColumn(name="created_by") private UserAccount createdBy; @ManyToOne @JoinColumn(name="assignee_id") private UserAccount assignee;
 @Column(nullable=false) private Instant createdAt; @Column(nullable=false) private Instant updatedAt; @Column(nullable=false) private Instant slaDueAt; private Instant completedAt; @Version private long version;
 protected Ticket(){} public Ticket(String no,String title,String desc,String cat,String pri,UserAccount creator,Instant now){ticketNumber=no;this.title=title;description=desc;category=cat;priority=pri;createdBy=creator;createdAt=now;updatedAt=now;slaDueAt=now.plusSeconds(hours(pri)*3600L);status="OPEN";}
 static int hours(String p){return switch(p){case "P1"->4;case "P3"->72;default->24;};}
 public Long getId(){return id;} public String getTicketNumber(){return ticketNumber;} public String getTitle(){return title;} public String getDescription(){return description;} public String getCategory(){return category;} public String getPriority(){return priority;} public String getStatus(){return status;} public UserAccount getCreatedBy(){return createdBy;} public UserAccount getAssignee(){return assignee;} public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;} public Instant getSlaDueAt(){return slaDueAt;} public Instant getCompletedAt(){return completedAt;} public long getVersion(){return version;} public boolean isSlaBreached(Instant now){return !status.equals("COMPLETED")&&!status.equals("CANCELLED")&&slaDueAt.isBefore(now);}
 void changeStatus(String s,Instant now){status=s;updatedAt=now;if(s.equals("COMPLETED"))completedAt=now;} void assign(UserAccount u,Instant now){assignee=u;status="ASSIGNED";updatedAt=now;} void priority(String p,Instant now){priority=p;slaDueAt=createdAt.plusSeconds(hours(p)*3600L);updatedAt=now;}
}
