package com.dukankhata.common.audit;

import com.dukankhata.auth.User;
import com.dukankhata.common.security.CurrentUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.UUID;

@Service
public class AuditService {
    private final AuditEventRepository repository;
    private final CurrentUserService current;
    public AuditService(AuditEventRepository repository, CurrentUserService current){this.repository=repository;this.current=current;}

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void record(String resource, String action, String outcome, String entityType, String entityId, String details){
        AuditEvent e=new AuditEvent();
        try { User u=current.requireUser(); e.setShopId(u.getShop().getId()); e.setActorUserId(u.getId()); e.setActorName(u.getName()); e.setActorMobile(u.getMobile()); } catch(Exception ignored) {}
        e.setResource(trim(resource,60)); e.setAction(trim(action,100)); e.setOutcome(trim(outcome,20)); e.setEntityType(trim(entityType,80)); e.setEntityId(trim(entityId,80));
        HttpServletRequest req=request(); if(req!=null){e.setHttpMethod(trim(req.getMethod(),80));e.setRequestPath(trim(req.getRequestURI(),300));e.setIpAddress(trim(clientIp(req),80));e.setUserAgent(trim(req.getHeader("User-Agent"),300));}
        e.setDetails(trim(details,1000)); repository.save(e);
    }
    public java.util.List<AuditEvent> latest(int limit){return repository.findByShopIdOrderByCreatedAtDesc(current.shopId(),org.springframework.data.domain.PageRequest.of(0,Math.min(Math.max(limit,1),200)));}
    private HttpServletRequest request(){var a=RequestContextHolder.getRequestAttributes();return a instanceof ServletRequestAttributes s?s.getRequest():null;}
    private String clientIp(HttpServletRequest r){String f=r.getHeader("X-Forwarded-For");return f!=null&&!f.isBlank()?f.split(",")[0].trim():r.getRemoteAddr();}
    private String trim(String s,int max){if(s==null)return null;s=s.trim();return s.length()<=max?s:s.substring(0,max);}
}
