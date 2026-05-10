package org.example.expert.domain.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggingAop {

    @Around("execution(* org.example.expert.domain.comment.service*(..))")
    public Object commentAop(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed(); // 실제 메서드 실행 -> Filter에서 doFilter 와 비슷함.

        long end = System.currentTimeMillis();
        log.info("[AOP] {} 실행됨 in {}ms" , joinPoint.getSignature() , end - start);
        return result;
    }
}
