package com.transform;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class BufferTransform {

	public static void main(String[] args) {
		Runtime run = Runtime.getRuntime();
		BufferedReader reader = null;
		try {
			File sysDir = new File("");
			String path = sysDir.getCanonicalPath();
			String protoSrcPath = path + "/proto";
			String outDscPath = path + "/src";
			System.out.println(protoSrcPath + "     " + outDscPath);
			File file = new File(protoSrcPath);
			for(int i = 0; i < file.list().length; i++) {
				System.out.println(file.list()[i]);
			}
			String commend = path + "/protoc.exe -I=" + protoSrcPath + " --java_out=" + outDscPath + " " +  protoSrcPath + "/addressbook.proto";
			System.out.println(commend);
			Process process = run.exec(commend);
			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
