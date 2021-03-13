package com.palilu.mineSweeper.model;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author pmendoza
 * @since 2021-03-13
 */
@Target( { ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MineSweeperGameValidator.class)
@Documented
public @interface MineSweeperGame {

    String message() default "Input contains more mines than cells";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}