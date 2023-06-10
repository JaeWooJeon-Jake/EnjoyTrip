package com.ssafy.luckytrip.user.model;

import java.sql.Timestamp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "UserDto (게시글 정보)", description = "사용자의 아이디, 비밀번호, 이름, 이메일, 주소, 휴대폰 번호, 등록일 저장")
public class UserDto {

	@ApiModelProperty(value = "아이디")
	private String id;
	@ApiModelProperty(value = "비밀번호")
	private String password;
	@ApiModelProperty(value = "이름")
	private String name;
	@ApiModelProperty(value = "이메일")
	private String email;
	@ApiModelProperty(value = "주소")
	private String address;
	@ApiModelProperty(value = "휴대폰 번호")
	private String phone;
	@ApiModelProperty(value = "가입일")
	private Timestamp registDate;
	@ApiModelProperty(value = "이모지번호")
	private int emoji;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Timestamp getRegistDate() {
		return registDate;
	}
	public void setRegistDate(Timestamp registDate) {
		this.registDate = registDate;
	}
	

	
	public int getEmoji() {
		return emoji;
	}
	public void setEmoji(int emoji) {
		this.emoji = emoji;
	}
	@Override
	public String toString() {
		return "UserDto [id=" + id + ", password=" + password + ", name=" + name + ", email=" + email + ", address="
				+ address + ", phone=" + phone + "]" + ", emoji=" + emoji + "]";
	}
}
