package com.exam.base;

import java.lang.annotation.*;

/**
 * 身份校验注解
 *
 * @author sunc
 * @date 2020/3/7 14:14
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoleCheck {

    String[] value() default {};

}