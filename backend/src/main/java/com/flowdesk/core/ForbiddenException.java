package com.flowdesk.core; import org.springframework.http.HttpStatus;
public class ForbiddenException extends TicketServiceException {public ForbiddenException(){super("Bu kaydı görüntüleme yetkiniz yok.",HttpStatus.FORBIDDEN);}}
