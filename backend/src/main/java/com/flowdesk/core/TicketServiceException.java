package com.flowdesk.core; import org.springframework.http.HttpStatus;
public class TicketServiceException extends RuntimeException {public final HttpStatus status;public TicketServiceException(String m,HttpStatus s){super(m);status=s;}}
