package com.hlkj.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ServiceLogAspect {

    /**
     * AOP通知：
     *  1、前置通知：在方法调用之前执行
     *  2、后置通知：在方法正常调用之后执行
     *  3、环绕通知：在方法正常调用之后执行
     *  4、异常通知：在方法调用过程中发生异常执行
     *  5、最终通知：在方法调用之后执行
     */

    public static final Logger log = LoggerFactory.getLogger(ServiceLogAspect.class);

    /**
     * 切面表达式
     *  execution：代表所要执行的表达式主体
     *  第一处： * 代表方法返回类型，这里*代表所有类型
     *  第二处：包名 代表aop监控的类所在的包名
     *  第三处：..  代表改包及其子包下所有的方法
     *  第四处：* 代表类名
     *  第五处：*(..)  *代表方法,（..）代表方法中的任何参数
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.hlkj.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("==========开始执行 {}.{}========", joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName());

        long begin = System.currentTimeMillis();
        //执行目标service
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        //执行时间
        long take = end - begin;
        if (take > 3000){
            log.error("==========执行结束，耗时 {} 毫秒=========", take);
        } else if (take > 2000){
            log.debug("==========执行结束，耗时 {} 毫秒=========", take);
        } else {
            log.info("==========执行结束，耗时 {} 毫秒=========", take);
        }
        return result;
    }

}
