package com.pojo;

import java.util.ArrayList;
import java.util.List;

import com.pojo.annotation.Field;
import com.pojo.annotation.Proto;

@Proto(packageName="com.pojo", className="MemberOrder")
public class Member {

	@Field(fieldType="required", fieldName="uid", paramType="long", fieldIndex=1)
	private long uid;
	
	@Field(fieldType="optional", fieldName="username", paramType="string", fieldIndex=2)
	private String username;
	
	@Field(fieldType="optional", fieldName="password", paramType="string", fieldIndex=3)
	private String password;
	
	@Field(fieldType="optional", fieldName="password", paramType="com.pojo.Order", fieldIndex=4)
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
