package com.pojo;

import java.util.ArrayList;
import java.util.List;

import com.buffer.annotation.Fields;
import com.buffer.annotation.Proto;

@Proto(protoPackage="com.test", packageName="com.pojo", className="MemberOrder")
public class Member {

	@Fields(fieldType="required", fieldName="uid", paramType="int64", fieldIndex=1)
	private long uid;
	
	@Fields(fieldType="optional", fieldName="username", paramType="string", fieldIndex=2)
	private String username;
	
	@Fields(fieldType="optional", fieldName="password", paramType="string", fieldIndex=3)
	private String password;
	
	@Fields(mapping="com.pojo.Order")
	private List<Order> orders = new ArrayList<Order>();

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
}
