package com.pojo;

import com.buffer.annotation.Fields;
import com.buffer.annotation.Proto;

@Proto(subClass=true)
public class Order {

	@Fields(fieldType="required", fieldName="orderId", protoType="int32", fieldIndex=1)
	private int orderId;
	
	@Fields(fieldType="optional", fieldName="productName", protoType="string", fieldIndex=2)
	private String productName;
	
	private Member member;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
}
