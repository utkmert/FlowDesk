package com.flowdesk.core; import org.springframework.http.HttpStatus;
public class NotFoundException extends TicketServiceException {public NotFoundException(){super("Kayıt bulunamadı.",HttpStatus.NOT_FOUND);}}
