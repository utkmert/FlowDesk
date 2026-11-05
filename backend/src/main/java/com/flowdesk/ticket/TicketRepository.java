package com.flowdesk.ticket;
import org.springframework.data.domain.*; import org.springframework.data.jpa.repository.*; import org.springframework.data.jpa.repository.JpaSpecificationExecutor; import java.time.Instant; import java.util.*;
public interface TicketRepository extends JpaRepository<Ticket,Long>,JpaSpecificationExecutor<Ticket>{
 Page<Ticket> findByCreatedByUsernameOrAssigneeUsername(String creator,String assignee,Pageable page);
 List<Ticket> findByStatusNotInAndSlaDueAtBefore(List<String> terminal,Instant now);
}
