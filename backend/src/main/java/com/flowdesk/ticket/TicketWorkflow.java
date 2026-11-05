package com.flowdesk.ticket;
import com.flowdesk.core.ConflictException;
public final class TicketWorkflow {
 private TicketWorkflow(){}
 public static String next(String action,String status,String creator,String assignee,String actor,boolean manager,String reason){
  return switch(action){case "START"->{if(!"ASSIGNED".equals(status)||!actor.equals(assignee))throw new ConflictException("Yalnızca size atanmış işi başlatabilirsiniz.");yield "IN_PROGRESS";}
   case "SUBMIT"->{if(!"IN_PROGRESS".equals(status)||!actor.equals(assignee))throw new ConflictException("Yalnızca üzerinizdeki devam eden işi onaya gönderebilirsiniz.");yield "PENDING_APPROVAL";}
   case "APPROVE","REJECT"->{if(!manager||!"PENDING_APPROVAL".equals(status))throw new ConflictException("Bu iş şu anda onaylanamaz.");if(actor.equals(assignee))throw new ConflictException("Kendi üzerinizdeki işi onaylayamazsınız.");yield action.equals("APPROVE")?"COMPLETED":"IN_PROGRESS";}
   case "CANCEL"->{if("OPEN".equals(status)&&creator.equals(actor))yield "CANCELLED";if(manager&&!status.equals("COMPLETED")&&!status.equals("CANCELLED")&&reason!=null&&!reason.isBlank())yield "CANCELLED";throw new ConflictException("İptal koşulları sağlanmıyor; yönetici iptalinde gerekçe zorunludur.");}
   default->throw new ConflictException("Geçersiz işlem.");};
 }
}
