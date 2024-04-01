package com.pnayavu.lab.logging;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("within(com.pnayavu.lab.controllers.*)"
            + " || within(com.pnayavu.lab.service.*)"
            + " || within(com.pnayavu.lab.cache.*)")
    public void allMethods() {

    }

    @Pointcut("@annotation(Logged)")
    public void methodsWithAspectAnnotation() {

    }

    @Before("methodsWithAspectAnnotation()")
    public void logMethodsInvocation(final JoinPoint joinPoint) {
        log.info("Method invocation {}.{}() with arguments: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs())
                );
    }
    @AfterReturning(pointcut = "methodsWithAspectAnnotation()", returning = "retValue")
    public void logAfterReturning(final JoinPoint joinPoint, Object retValue)  {
        log.info("Method {}.{}() returned {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                retValue
        );
    }

    @AfterThrowing(pointcut = "allMethods()", throwing = "exception")
    public void exceptionLogs(final JoinPoint joinPoint,
                                              final Throwable exception) {
        log.error("Thrown exception from: {}.{} thrown: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), exception.toString());
    }
}
