package com.dukankhata.common.audit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Aspect @Component
public class AuditAspect {
    private final AuditService audit;
    public AuditAspect(AuditService audit){this.audit=audit;}
    @Around("@within(resource)")
    public Object auditController(ProceedingJoinPoint pjp, AuditResource resource) throws Throwable {
        String action=((MethodSignature)pjp.getSignature()).getMethod().getName();
        String entityId=findId(pjp.getArgs());
        try { Object result=pjp.proceed(); audit.record(resource.value(),action,"SUCCESS",resource.value(),entityId,null); return result; }
        catch(Throwable ex){ audit.record(resource.value(),action,"FAILURE",resource.value(),entityId,ex.getClass().getSimpleName()+": "+safe(ex.getMessage())); throw ex; }
    }
    private String findId(Object[] args){for(Object a:args)if(a instanceof UUID)return a.toString();return null;}
    private String safe(String s){if(s==null)return null;return s.length()>800?s.substring(0,800):s;}
}
