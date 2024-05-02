package com.pnayavu.lab.logging;


import com.pnayavu.lab.service.CounterService;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
  @Pointcut("within(com.pnayavu.lab.controllers.*)"
      + " || within(com.pnayavu.lab.service.*)"
      + " || within(com.pnayavu.lab.cache.*)")
  public void allMethods() {

  }

  @Pointcut("within(com.pnayavu.lab.service..*)")
  public void serviceMethods() {

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

  @Before("serviceMethods()")
  public void logCounterService(final JoinPoint joinPoint) {
    int requestCounter = CounterService.increment();
    log.info("Invocation of service method {}.{}"
            + " Incremented counter to {}", joinPoint
            .getSignature().getDeclaringTypeName(),
        joinPoint.getSignature().getName(), requestCounter);
  }

  @AfterReturning(pointcut = "methodsWithAspectAnnotation()", returning = "retValue")
  public synchronized void logAfterReturning(final JoinPoint joinPoint, Object retValue) {
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
