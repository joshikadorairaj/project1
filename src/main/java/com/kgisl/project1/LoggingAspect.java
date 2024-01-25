package com.kgisl.project1;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.kgisl.project1..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Before method execution: " + joinPoint.getSignature().toShortString()); // getSignature ->method name
    }

    @After("execution(* com.kgisl.project1..*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("After method execution: " + joinPoint.getSignature().toShortString());
    }

    @AfterThrowing(pointcut = "execution(* com.kgisl.project1.service.*.*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        logger.error("Exception thrown in method: " + joinPoint.getSignature().toShortString());
        logger.error("Exception details: " + exception.getMessage(), exception);
    }
}
