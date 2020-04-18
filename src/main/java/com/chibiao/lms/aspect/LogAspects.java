package com.chibiao.lms.aspect;

import com.chibiao.lms.annotation.Log;
import com.chibiao.lms.error.DefaultErrorCode;
import com.chibiao.lms.exception.BusinessException;
import com.chibiao.lms.util.HttpResultUtil;
import com.chibiao.lms.util.JsonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * 打印日志切面
 *
 * @author : 迟彪
 * @date : 2020/4/15
 */
@Aspect
public class LogAspects {
    private static final Logger logger = LoggerFactory.getLogger(LogAspects.class);
    private String appName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Pointcut("@annotation(com.chibiao.lms.annotation.Log)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object log(ProceedingJoinPoint point) {
        // 参数
        Object[] args = point.getArgs();
        // 方法签名
        MethodSignature ms = (MethodSignature) point.getSignature();
        // 参数名
        String[] parameterNames = ms.getParameterNames();
        // 方法信息
        Method method = ms.getMethod();
        // 得到注解
        Log annotation = method.getAnnotation(Log.class);
        // 注解key
        String umpKey = annotation.jKey();
        // 日志开关
        boolean logSwitch = annotation.logSwitch();
        try {
            long start = System.currentTimeMillis();
            if (logSwitch) {
                logger.info(umpKey + ",start");
                if (null != parameterNames) {
                    for (int i = 0; i < parameterNames.length; i++) {
                        if (args[i] instanceof Collection) {
                            printLog(umpKey, ((Collection) args[i]).size(), parameterNames[i], args[i]);
                        } else if (args[i] instanceof Map) {
                            printLog(umpKey, ((Map) args[i]).size(), parameterNames[i], args[i]);
                        } else {
                            logger.info(umpKey + ",param," + parameterNames[i] + ":{}", JsonUtil.toJSONStringWithDateFormat(args[i]));
                        }
                    }
                }
            }
            Object proceed = point.proceed();
            long end = System.currentTimeMillis();
            if (logSwitch) {
                logger.info(umpKey + ",result:{}", JsonUtil.toJSONStringWithDateFormat(proceed));
            }
            logger.info(umpKey + ",用时{}毫秒", (end - start));
            return proceed;
        } catch (BusinessException b) {
            // 报警
            logger.error(umpKey + ",error", b);
            if (annotation.errorReturnHttpResult()) {
                return HttpResultUtil.buildHttpResult(b.getErrorCode());
            } else {
                throw b;
            }
        } catch (Exception e) {
            // 报警
            logger.error(umpKey + ",error", e);
            if (annotation.errorReturnHttpResult()) {
                return HttpResultUtil.buildHttpResult(DefaultErrorCode.UNKNOWN_ERROR);
            } else {
                throw BusinessException.asBusinessException(DefaultErrorCode.UNKNOWN_ERROR, e);
            }
        } catch (Throwable throwable) {
            logger.error(umpKey + ",error", throwable);
            if (annotation.errorReturnHttpResult()) {
                return HttpResultUtil.buildHttpResult(DefaultErrorCode.UNKNOWN_ERROR);
            } else {
                throw BusinessException.asBusinessException(DefaultErrorCode.UNKNOWN_ERROR, throwable);
            }
        } finally {

        }
    }


    /**
     * 集合日志打印，size > 50只打印size，否则打印内容
     *
     * @param umpKey        umpKey
     * @param size          size
     * @param parameterName 入参名
     * @param arg           入参值
     */
    private void printLog(String umpKey, int size, String parameterName, Object arg) {
        int maxSize = 50;
        if (size > maxSize) {
            logger.info(umpKey + ",param," + parameterName + ".size:{}", size);
        } else {
            logger.info(umpKey + ",param," + parameterName + ":{}", JsonUtil.toJSONStringWithDateFormat(arg));
        }
    }
}
