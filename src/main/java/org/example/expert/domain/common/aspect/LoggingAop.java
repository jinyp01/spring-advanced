package org.example.expert.domain.common.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class LoggingAop {

    @Around("execution(* org.example.expert.domain.admin.controller*(..))")
    public Object commentAop(ProceedingJoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes attr =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest(); //
        String requestBody = Arrays.toString(joinPoint.getArgs());

        long start = System.currentTimeMillis(); // API 요청 시각

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();

        Object result = joinPoint.proceed(); // 응답 본문

        log.info(" 유저 아이디 : {}," +
                " API 요청 시각 : {}" +
                " API 요청 URL : {}" +
                " 요청 본문 : {}" +
                " 응답 본문 : {}",
                userId,
                start,
                request.getRequestURI(),
                requestBody,
                result);

        return result;
    }
}
