package com.flowdesk.ticket; import org.springframework.scheduling.annotation.Scheduled; import org.springframework.stereotype.Component;
@Component public class SlaScheduler {private final TicketService service;public SlaScheduler(TicketService s){service=s;} @Scheduled(fixedDelayString="${app.sla-check-ms:60000}") public void check(){service.checkSla();}}
