package com.buffer.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) 
public @interface Fields {
	String fieldType() default "";
	String fieldName() default "";
	String paramType() default "";
	String mapping() default "";
	int fieldIndex() default -1;
}
