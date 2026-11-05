package com.flowdesk.ticket;
import com.flowdesk.security.UserAccount;import org.junit.jupiter.api.Test;import java.time.Instant;import java.util.Set;import static org.junit.jupiter.api.Assertions.*;
class TicketSlaTest {
 private final Instant start=Instant.parse("2026-01-01T00:00:00Z");private final UserAccount employee=new UserAccount("employee1","Demo Employee","hash",Set.of("EMPLOYEE"));
 @Test void p1DeadlineFourCalendarHours(){assertEquals(start.plusSeconds(4*3600L),new Ticket("FD-1","x","x","Ops","P1",employee,start).getSlaDueAt());}
 @Test void p2DeadlineOneCalendarDay(){assertEquals(start.plusSeconds(24*3600L),new Ticket("FD-2","x","x","Ops","P2",employee,start).getSlaDueAt());}
 @Test void p3DeadlineThreeCalendarDays(){assertEquals(start.plusSeconds(72*3600L),new Ticket("FD-3","x","x","Ops","P3",employee,start).getSlaDueAt());}
 @Test void expiredOpenTicketIsBreached(){var t=new Ticket("FD-4","x","x","Ops","P1",employee,start);assertTrue(t.isSlaBreached(start.plusSeconds(4*3600L+1)));}
 @Test void completedTicketLeavesActiveBreachCheck(){var t=new Ticket("FD-5","x","x","Ops","P1",employee,start);t.changeStatus("COMPLETED",start.plusSeconds(5*3600L));assertFalse(t.isSlaBreached(start.plusSeconds(8*3600L)));}
 @Test void cancelledTicketLeavesActiveBreachCheck(){var t=new Ticket("FD-6","x","x","Ops","P1",employee,start);t.changeStatus("CANCELLED",start.plusSeconds(5*3600L));assertFalse(t.isSlaBreached(start.plusSeconds(8*3600L)));}
}
