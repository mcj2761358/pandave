package com.minutch.pandave.dao.base;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by Minutch on 15/6/12.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface MyBatisRepository {
    String value() default "";
}
