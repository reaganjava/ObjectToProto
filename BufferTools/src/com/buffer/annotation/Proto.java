package com.buffer.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) 
public @interface Proto {
	String protoPackage() default "";
	String packageName() default "com.pojo";
	String className() default "";
	boolean subClass() default false;
}
