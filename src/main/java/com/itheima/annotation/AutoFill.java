package com.itheima.annotation;

import com.itheima.enumeration.OperationType;

import java.lang.annotation.*;

@Target(ElementType.METHOD) //这个注解只能贴在方法上。但不能乱贴到类、字段上。
@Retention(RetentionPolicy.RUNTIME) //这个注解在运行时还保留着。如果不保留到运行时，程序执行时就读不到这个注解了。
@Documented //生成文档时，把这个注解也算进去。
public @interface AutoFill { //定义一个自定义标签 @AutoFill，以后可以贴在方法上，告诉程序：这个方法需要做公共字段自动填充。
    OperationType value(); //这个注解里必须带一个参数，类型是：INSERT\UPDATE
}
