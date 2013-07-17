package com.transform;

import java.io.File;
import java.lang.reflect.Field;

import com.pojo.Member;
import com.pojo.Order;
import com.pojo.annotation.Proto;
import com.pojo.annotation.Fields;


public class JavaTransformProtos {

	public void objectTransform(Class<?> clazz) {
		Proto proto = clazz.getAnnotation(Proto.class);
		String packageName = "option java_package = \"" + proto.packageName() + "\"";
		String className = "option java_outer_classname = \"" + proto.className() + "\"";
		String name = "message " + clazz.getName() + " {";
		for(Field field : clazz.getFields()) {
			Fields fieldAnno = field.getAnnotation(Fields.class);
			fieldAnno.
		}
	}
	
	public static void main(String[] args) throws Exception {
		File sysDir = new File("");
		String path = sysDir.getCanonicalPath();
		File pojoPackage = new File(path + "/src/com/pojo/");
		for(String classFile : pojoPackage.list()) {
			String className = classFile.replace(".java", ".class");
			System.out.println(className);
		}
		new JavaTransformProtos().objectTransform(Member.class);
	}
}
