package com.site.AOP_Demo.aspect;

import com.site.AOP_Demo.annotation.CheckSecurity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class BankAuditAspect {

    @Pointcut("execution(* com.example.aopdemo.service.BankService.*(..))")
    public void bankServiceMethods() {}

    @Around("bankServiceMethods()")
    public Object measureExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("1. [@Around - Start] Entering: " + proceedingJoinPoint.getSignature().getName());


        Object result = proceedingJoinPoint.proceed();

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("6. [@Around - End] Completed in " + elapsed + " ms");
        return result;
    }


    @Before("bankServiceMethods()")
    public void logMethodEntry(JoinPoint joinPoint) {
        System.out.println("2. [@Before] Method: " + joinPoint.getSignature().getName()
                + " | Args: " + Arrays.toString(joinPoint.getArgs()));
    }

    @Before("@annotation(checkSecurity)")
    public void enforceSecurity(JoinPoint joinPoint, CheckSecurity checkSecurity) {
        System.out.println("3. [@Before - Security] Verifying '" + checkSecurity.role()
                + "' permission for: " + joinPoint.getSignature().getName());
    }


    @AfterReturning(pointcut = "bankServiceMethods()", returning = "result")
    public void logSuccess(JoinPoint joinPoint, Object result) {
        System.out.println("4. [@AfterReturning] Returned: " + result);
    }

    @AfterThrowing(pointcut = "bankServiceMethods()", throwing = "exception")
    public void logFailure(JoinPoint joinPoint, Throwable exception) {
        System.out.println("4. [@AfterThrowing] Exception caught: " + exception.getMessage());
    }

    @After("bankServiceMethods()")
    public void logFinally(JoinPoint joinPoint) {
        System.out.println("5. [@After] Cleanup finished for: " + joinPoint.getSignature().getName());
    }
}