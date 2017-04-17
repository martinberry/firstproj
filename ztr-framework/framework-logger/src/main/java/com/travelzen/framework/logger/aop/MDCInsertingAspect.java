package com.travelzen.framework.logger.aop;


import com.travelzen.framework.core.util.TZUtil;
import com.travelzen.framework.logger.core.ri.CallInfo;
import com.travelzen.framework.logger.ri.RequestIdentityHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;

/**

 *
 */


/**
 * 在MDC中增加参数
 *
 * @author renshui
 *         use class in framework-logger-core
 */
@Deprecated
@Aspect
public abstract class MDCInsertingAspect {

    @Pointcut
    public abstract void mdcInsertingOperation();

    @Around("mdcInsertingOperation()")
    public Object insert(final ProceedingJoinPoint pjp) throws Throwable {

        try {
            CallInfo callInfo = com.travelzen.framework.logger.core.ri.RequestIdentityHolder.get();
            if (TZUtil.isEmpty(callInfo))
                RequestIdentityHolder.init();
            return pjp.proceed();
        } finally {
            MDC.clear();
        }
    }
}
