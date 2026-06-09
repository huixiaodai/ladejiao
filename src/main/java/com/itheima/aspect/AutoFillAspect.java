package com.itheima.aspect;


import com.itheima.annotation.AutoFill;
import com.itheima.enumeration.OperationType;
import com.itheima.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 调用 mapper 方法
 * -> 切面先拦一下
 * -> 自动补公共字段
 * -> 再执行 SQL
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    //凡是方法上带有 @AutoFill 注解的，在它执行之前，先执行这个 autoFill() 方法。
    @Before("@annotation(com.itheima.annotation.AutoFill)")

    //JoinPoint 当前被拦住的那个方法的信息包 eg.方法是 addDish 参数是 dish
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始进行公共字段自动填充...");

        //拿到“当前方法的签名信息”。 拿到当前被拦住的方法对象。
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //从这个方法上，把 @AutoFill 注解取下来。
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        //再从注解里拿到值。 知道是insert还是update
        OperationType operationType = autoFill.value();

        //取出当前方法的参数。
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }

        //获取到dish
        Object entity = args[0];

        LocalDateTime now = LocalDateTime.now();
        Integer currentId = ThreadLocalUtil.getCurrentId();

        try {
            if (operationType == OperationType.INSERT) {
                //我现在不知道你传进来的具体是 Dish、Employee、Category 还是别的实体，但只要它有这些 setter，我就能调用。
                Method setCreateTime = entity.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod("setCreateUser", Integer.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Integer.class);

                //真正赋值
                setCreateTime.invoke(entity, now);
                setUpdateTime.invoke(entity, now);
                setCreateUser.invoke(entity, currentId);
                setUpdateUser.invoke(entity, currentId);
            } else if (operationType == OperationType.UPDATE) {
                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Integer.class);

                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
            }
        } catch (Exception e) {
            throw new RuntimeException("公共字段自动填充失败", e);
        }
    }
}
