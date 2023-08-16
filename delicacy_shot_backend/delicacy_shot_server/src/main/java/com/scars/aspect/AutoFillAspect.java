package com.scars.aspect;

import com.scars.annotation.AutoFill;
import com.scars.constant.AutoFillConstant;
import com.scars.context.BaseContext;
import com.scars.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面类，实现公共字段填充处理逻辑
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    @Pointcut("execution(* com.scars.mapper.*.*(..)) && @annotation(com.scars.annotation.AutoFill)")
    public void autoFillPointCut() {}

    /**
     * 前置通知
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始进行公共字段的自动填充");

        // 获取当前数据库操作类型
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature(); // 方法签名对象
        AutoFill autoFill = methodSignature.getMethod().getAnnotation(AutoFill.class);  // 方法的注解对象
        OperationType operationType = autoFill.value();   // 获取数据库操作类型

        // 获取拦截到的方法参数（实体对象）
        Object args[] = joinPoint.getArgs();
        if (args == null || args.length == 0)
        {
            return;
        }
        Object entity = args[0];

        // 准备用于赋值的数据
        LocalDateTime currentTime = LocalDateTime.now();
        Long currentID = BaseContext.getCurrentId();

        // 对不同的操作类型来为对应的属性通过反射来赋值
        if (operationType == OperationType.INSERT)
        {
            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                // 通过反射为对象属性赋值
                setCreateTime.invoke(entity, currentTime);
                setCreateUser.invoke(entity, currentID);
                setUpdateTime.invoke(entity, currentTime);
                setUpdateUser.invoke(entity, currentID);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if (operationType == OperationType.UPDATE)
        {
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                // 通过反射为对象属性赋值
                setUpdateTime.invoke(entity, currentTime);
                setUpdateUser.invoke(entity, currentID);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
