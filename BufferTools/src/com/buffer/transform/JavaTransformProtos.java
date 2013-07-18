package com.buffer.transform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

import com.buffer.annotation.Fields;
import com.buffer.annotation.Proto;
import com.buffer.util.TypeHandler;


public class JavaTransformProtos {
	
	private String srcClassPath = "";
	
	private String protoFilePath = "";
	
	private String protoFileName = "";
	
	private String projectDir = "";
	
	private String classPackage = "";
	
	public JavaTransformProtos(String classPackage, String protoFileDir) throws Exception {
		File currentFile = new File("");
		projectDir = currentFile.getCanonicalPath();
		this.classPackage = classPackage;
		System.out.println(classPackage);
		this.srcClassPath = projectDir + "/src/" + classPackage.replace(".", "/");;
		System.out.println(srcClassPath);
		this.protoFilePath = projectDir + "/" + protoFileDir;
		File protoDir = new File(this.protoFilePath);
		if(!protoDir.isDirectory()) {
			protoDir.mkdirs();
		}
	}
	
	public void startTransform() throws Exception {
		File pojoPackage = new File(srcClassPath);
		for(String classFile : pojoPackage.list()) {
			String className = classFile.substring(0, classFile.indexOf("."));
			System.out.println(className);
			Class<?> clazz = Class.forName(classPackage + "." + className);
			System.out.println(clazz.getName());
			Proto proto = clazz.getAnnotation(Proto.class);
			if(proto != null && !proto.subClass()) {
				String protoFile = transform(clazz);
				if(protoFile != null) {
					System.out.println(protoFile);
					protoFileName = clazz.getSimpleName() + "Proto.proto";
					System.out.println(protoFilePath + "/" + protoFileName);
					File writeProtoFile = new File(protoFilePath + "/" + protoFileName);
					FileOutputStream out = new FileOutputStream(writeProtoFile);
					out.write(protoFile.getBytes());
					out.flush();
					out.close();
				}
			}
		}
		buildJava();
	}
	
	public void buildJava() throws Exception {
		Runtime run = Runtime.getRuntime();
		BufferedReader reader = null;
		File file = new File(protoFilePath);
		for(String protoName : file.list()) {
			System.out.println(protoName);
			String commend = projectDir + "/protoc.exe -I=" + protoFilePath + " --java_out=" + projectDir + "/src/" + " " +  protoFilePath + "/" + protoName;
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
		if(proto != null) {
			String protoFile = "";
			String protoPackage = "";
			String packageName = "";
			String className = "";
			if(!proto.subClass()) {
				if(!proto.protoPackage().equals("")) {
					protoPackage = "package " + proto.protoPackage() + ";\n";
				}
				if(!proto.packageName().equals("")) {
					packageName = "option java_package = \"" + proto.packageName() + "\";\n";
				} else {
					System.out.println("default:" + clazz.getPackage());
					packageName = "option java_package = \"" + clazz.getPackage() + "\";\n";
				} 
				if(!proto.className().equals("")) {
					className = "option java_outer_classname = \"" + proto.className() + "\";\n";
				} else {
					System.out.println("default:" + clazz.getSimpleName());
					className = "option java_outer_classname = \"" + clazz.getSimpleName() + "\";\n";
				}
			}
			String messageName = "message " + clazz.getSimpleName() + " {\n";
			String protoField = "";
			String subProto = "";
			int defaultIndex = 1;
			for(Field field : clazz.getDeclaredFields()) {
				Fields fieldAnno = field.getAnnotation(Fields.class);
				if(fieldAnno != null) {
					
					String fieldType = fieldAnno.fieldType();
					String fieldName = fieldAnno.fieldName();
					String protoType = fieldAnno.protoType();
					int index = fieldAnno.fieldIndex();
					String mapping = fieldAnno.mapping();
					String defValue = fieldAnno.defValue();
					if(mapping.equals("")) {
						if(!fieldType.equals("")) {
							protoField += fieldType;
						}
						
						if(!protoType.equals("")) {
							protoField += " " + protoType;
						} else {
							protoType = TypeHandler.getProtoType(field.getType().getName());
							if(protoType == null) {
								throw new Exception("@Fields mapping error Class can not find");
							}
							protoField += " " + protoType;
						}
						
						if(!fieldName.equals("")) {
							protoField += " " + fieldName;
						} else {
							protoField += " " + field.getName(); 
						}
						
						if(index != -1) {
							protoField += " = " + index;
						} else {
							protoField += " = " + defaultIndex; 
						}
						
						if(!defValue.equals("")) {
							switch(protoType) {
								case "string" : {
									defValue = "\"" + defValue + "\"";
									break;
								}
								case "float" : {
									defValue = "" + Float.parseFloat(defValue);
									break;
								}
								case "double" : {
									defValue = "" + Double.parseDouble(defValue);
									break;
								}
								case "int32":
								case "int64": {
									defValue = "" + Integer.parseInt(defValue);
									break;
								}
							}
							protoField += " [default = " + defValue + "]";
						}
						
						protoField +=";\n";
					} else {
						if(!mapping.equals("")) {
							Class<?> mappingClazz = Class.forName(mapping);
							subProto = transform(mappingClazz);
						}	
					}
					defaultIndex++;
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
