package com.flowdesk.core; import org.springframework.http.HttpStatus;
public class ConflictException extends TicketServiceException {public ConflictException(String m){super(m,HttpStatus.CONFLICT);}}
