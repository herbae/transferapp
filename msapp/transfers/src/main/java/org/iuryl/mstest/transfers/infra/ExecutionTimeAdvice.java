package org.iuryl.mstest.transfers.infra;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@ConditionalOnExpression("${aspect.enabled:true}")
public class ExecutionTimeAdvice {

    @Around("@annotation(org.iuryl.mstest.transfers.infra.TrackExecutionTime)")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        if (log.isTraceEnabled()) {
            long startTime = System.currentTimeMillis();
            Object object = point.proceed();
            long endtime = System.currentTimeMillis();
            log.trace("Class Name: "+ point.getSignature().getDeclaringTypeName() +". Method Name: "+ point.getSignature().getName() + ". Time taken for Execution is : " + (endtime-startTime) +"ms");
            return object;
        } else {
            return point.proceed();
        }
    }
}
