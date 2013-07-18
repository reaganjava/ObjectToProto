package com.buffer.util;

import java.util.HashMap;
import java.util.Map;


public class TypeHandler {

	private static Map<String, String> typeMap = new HashMap<String, String>();
	
	static {
		typeMap.put("byte", "int32");
		typeMap.put("java.lang.Byte", "int32");
		typeMap.put("short", "int32");
		typeMap.put("java.lang.Short", "int32");
		typeMap.put("int", "int32");
		typeMap.put("java.lang.Integer", "int32");
		typeMap.put("long", "int64");
		typeMap.put("java.lang.Long", "int64");
		typeMap.put("float", "float");
		typeMap.put("java.lang.Float", "float");
		typeMap.put("double", "double");
		typeMap.put("java.lang.Double", "double");
		typeMap.put("java.lang.String", "string");
		typeMap.put("boolean", "bool");
		typeMap.put("java.lang.Boolean", "bool");
		typeMap.put("com.google.protobuf.ByteString", "bytes");
	}
	
	public static String getProtoType(String javaType) {
		System.out.println(javaType);
		return typeMap.get(javaType);
	}
}
