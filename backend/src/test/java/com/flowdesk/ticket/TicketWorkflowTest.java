package com.flowdesk.ticket;
import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.*;
class TicketWorkflowTest {
 @Test void employeeStartsOwnAssignedWork(){assertEquals("IN_PROGRESS",TicketWorkflow.next("START","ASSIGNED","employee1","employee1","employee1",false,null));}
 @Test void employeeCannotStartSomeoneElsesWork(){assertThrows(RuntimeException.class,()->TicketWorkflow.next("START","ASSIGNED","employee1","employee2","employee1",false,null));}
 @Test void employeeCannotStartOpenWork(){assertThrows(RuntimeException.class,()->TicketWorkflow.next("START","OPEN","employee1","employee1","employee1",false,null));}
 @Test void employeeSubmitsOwnWork(){assertEquals("PENDING_APPROVAL",TicketWorkflow.next("SUBMIT","IN_PROGRESS","employee1","employee1","employee1",false,null));}
 @Test void employeeCannotSubmitOthersWork(){assertThrows(RuntimeException.class,()->TicketWorkflow.next("SUBMIT","IN_PROGRESS","employee1","employee2","employee1",false,null));}
 @Test void managerApproves(){assertEquals("COMPLETED",TicketWorkflow.next("APPROVE","PENDING_APPROVAL","employee1","employee2","manager1",true,null));}
 @Test void managerRejectsBackToProgress(){assertEquals("IN_PROGRESS",TicketWorkflow.next("REJECT","PENDING_APPROVAL","employee1","employee2","manager1",true,"Eksik"));}
 @Test void employeeCannotApprove(){assertThrows(RuntimeException.class,()->TicketWorkflow.next("APPROVE","PENDING_APPROVAL","employee1","employee2","employee3",false,null));}
 @Test void managerCannotApproveOwnAssignment(){assertThrows(RuntimeException.class,()->TicketWorkflow.next("APPROVE","PENDING_APPROVAL","manager1","manager1","manager1",true,null));}
 @Test void creatorCancelsOwnOpenTicket(){assertEquals("CANCELLED",TicketWorkflow.next("CANCEL","OPEN","employee1",null,"employee1",false,null));}
 @Test void managerNeedsReasonToCancel(){assertThrows(RuntimeException.class,()->TicketWorkflow.next("CANCEL","IN_PROGRESS","employee1","employee2","manager1",true," "));}
 @Test void terminalStateCannotBeCancelledAgain(){assertThrows(RuntimeException.class,()->TicketWorkflow.next("CANCEL","COMPLETED","employee1","employee2","manager1",true,"Gerekçe"));}
 @Test void terminalTransitionRejected(){assertThrows(RuntimeException.class,()->TicketWorkflow.next("START","COMPLETED","employee1","employee1","employee1",false,null));}
}
