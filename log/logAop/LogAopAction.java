package com.guozhong.his.modules.market.log.logAop;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.guozhong.his.modules.market.log.annotation.SystemLog;
/**
 * 日志切面
 * @author GAOLEI
 *
 */
@Aspect
@Component
public  class LogAopAction {
	
     private  static  final Logger logger = LoggerFactory.getLogger(LogAopAction. class);
     
     //切入点
     @Pointcut("@annotation(com.guozhong.his.modules.market.log.annotation.SystemLog)")
     public  void controllerAspect() {
    }
    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     * @throws Throwable 
     */
    @After("controllerAspect()")
     public void  doController(JoinPoint  point) throws Throwable {
    	logger.debug("--------------方法之前--------------------");
    	
    	//Object proceed = point.proceed();
    	//getControllerMethodDescription(point);
    	logger.debug("--------------方法之后--------------------");
        Object result = null;
        // 执行方法名
        Object[] args = point.getArgs();
        for (Object object : args) {
			
		}
        getControllerMethodDescription(point);
        String methodName = point.getSignature().getName();
        logger.debug("--------------methodName-------------------" + methodName);
        String className = point.getTarget().getClass().getSimpleName();
        logger.debug("--------------className-------------------" + className);
        // 当前用户
       //  return proceed;
    }
    
    /**
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
     @SuppressWarnings("rawtypes")
    public Map<String, Object> getControllerMethodDescription(JoinPoint joinPoint)  throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
         for (Method method : methods) {
             if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                for (Class claz : clazzs) {
					Field[] declaredFields = claz.getDeclaredFields();
					for (Field field : declaredFields) {
						logger.info(field.getName()+"--------------------field.getName()----------------------------------------");
						String type = field.getGenericType().toString(); // 获取属性的类型
						logger.info(type+"--------------------type----------------------------------------");
					}
				}
                 if (clazzs.length == arguments.length) {
                	 logger.debug("------------.module()-----------------" + method.getAnnotation(SystemLog.class).module());
                     map.put("module", method.getAnnotation(SystemLog.class).module());
                     map.put("methods", method.getAnnotation(SystemLog.class).methods());
                     String de = method.getAnnotation(SystemLog.class).description();
                   //  if(Common.isEmpty(de))de="执行成功!";
                     map.put("description", de);
                     break;
                }
            }
        }
         return map;
    }
}