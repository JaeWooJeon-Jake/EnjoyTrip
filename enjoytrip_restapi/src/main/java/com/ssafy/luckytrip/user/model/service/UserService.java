package com.ssafy.luckytrip.user.model.service;

import java.util.Map;

import com.ssafy.luckytrip.user.model.UserDto;

public interface UserService {
	
	public boolean idCheck(String id) throws Exception;

	public UserDto login(UserDto user) throws Exception;
	
	public boolean join(UserDto userDto) throws Exception;
	
	public boolean modify(UserDto userDto) throws Exception;
	
	public boolean delete(String id) throws Exception;
	
	public String searchId(Map<String, String> params) throws Exception;
	
	public String searchPwd(Map<String, String> params) throws Exception;
	
	//JWT위함
	public UserDto userInfo(String userid) throws Exception;
	public void saveRefreshToken(String userid, String refreshToken) throws Exception;
	public Object getRefreshToken(String userid) throws Exception;
	public void deleRefreshToken(String userid) throws Exception;
	
//	개인기능
	public void setEmoji(UserDto userDto)throws Exception;

}
