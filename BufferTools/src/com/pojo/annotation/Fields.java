package com.pojo.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) 
public @interface Fields {
	String fieldType() default "optional";
	String fieldName() default "";
	String paramType() default "";
	int fieldIndex() default 0;
}
