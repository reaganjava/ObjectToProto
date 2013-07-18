package com.buffer.transform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

import com.buffer.annotation.Fields;
import com.buffer.annotation.Proto;


public class JavaTransformProtos {
	
	private String srcClassPath = "";
	
	private String protoFilePath = "";
	
	private String protoFileName = "";
	
	private String projectDir = "";
	
	public JavaTransformProtos(String classPackage, String protoFileDir) throws Exception {
		File currentFile = new File("");
		projectDir = currentFile.getCanonicalPath();
		classPackage = classPackage.replace(".", "/");
		System.out.println(classPackage);
		this.srcClassPath = projectDir + "/src/" + classPackage;
		System.out.println(srcClassPath);
		this.protoFilePath = projectDir + protoFileDir;
		File protoDir = new File(this.protoFilePath);
		if(!protoDir.isDirectory()) {
			protoDir.mkdirs();
		}
	}
	
	public void startTransform() throws Exception {
		File pojoPackage = new File(srcClassPath);
		for(String classFile : pojoPackage.list()) {
			String className = classFile.replace(".java", ".class");
			System.out.println(className);
			Class<?> clazz = Class.forName(className);
			String protoFile = transform(clazz);
			protoFileName = clazz.getSimpleName() + "Proto.proto";
			File writeProtoFile = new File(protoFilePath + protoFileName);
			FileOutputStream out = new FileOutputStream(writeProtoFile);
			out.write(protoFile.getBytes());
			out.flush();
			out.close();
		}
		buildJava();
	}
	
	public void buildJava() throws Exception {
		Runtime run = Runtime.getRuntime();
		BufferedReader reader = null;
		File file = new File(protoFilePath);
		for(int i = 0; i < file.list().length; i++) {
			System.out.println(file.list()[i]);
			String commend = projectDir + "/protoc.exe -I=" + protoFilePath + " --java_out=" + projectDir + "/src/" + " " +  protoFilePath + file.getName();
			System.out.println(commend);
			Process process = run.exec(commend);
			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		}
		
	
	}

	private String transform(Class<?> clazz) throws Exception {
		Proto proto = clazz.getAnnotation(Proto.class);
		if(proto != null && !proto.subClass()) {
			String protoFile = "";
			String protoPackage = "";
			String packageName = "";
			String className = "";
			protoPackage = proto.protoPackage() + "\n";
			if(!proto.subClass()) {
				packageName = "option java_package = \"" + proto.packageName() + "\";\n";
				className = "option java_outer_classname = \"" + proto.className() + "\";\n";
			}
			String messageName = "message " + clazz.getSimpleName() + " {\n";
			String protoField = "";
			String subProto = "";
			
			for(Field field : clazz.getDeclaredFields()) {
				Fields fieldAnno = field.getAnnotation(Fields.class);
				if(fieldAnno != null) {
					
					String fieldType = fieldAnno.fieldType();
					String fieldName = fieldAnno.fieldName();
					String paramType = fieldAnno.paramType();
					String mapping = fieldAnno.mapping();
					int index = fieldAnno.fieldIndex();
					
					if(!fieldType.equals("")) {
						protoField += fieldType;
					}
					
					if(!paramType.equals("")) {
						protoField += " " + paramType;
					}
					
					if(!fieldName.equals("")) {
						protoField += " " + fieldName;
					}
					
					if(index != -1) {
						protoField += " = " + index + ";\n";
					}
					
					if(!mapping.equals("")) {
						Class<?> mappingClazz = Class.forName(mapping);
						subProto = transform(mappingClazz);
					}				
					
				}
			}
			protoFile = protoPackage + packageName + className + messageName + protoField;
			if(subProto != null) {
				protoFile += subProto;
			}
			protoFile += "}\n";
			return protoFile;
		} else {
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception {
		JavaTransformProtos jtp = new JavaTransformProtos("com.pojo", "proto");
		jtp.startTransform();
	}
}
