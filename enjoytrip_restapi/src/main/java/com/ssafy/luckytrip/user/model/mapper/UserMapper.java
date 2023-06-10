package com.ssafy.luckytrip.user.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.luckytrip.user.model.UserDto;

@Mapper
public interface UserMapper {
	
	public int idCheck(String id) throws SQLException;

	public UserDto login(UserDto user) throws SQLException;
	
	public int join(UserDto userDto) throws SQLException;
	
	public int modify(UserDto userDto) throws SQLException;
	
	public int delete(String id) throws SQLException;
	
	public String searchId(Map<String, String> params) throws SQLException;
	
	public String searchPwd(Map<String, String> params) throws SQLException;
	
//	JWT관련
// userInfo 완료
	public UserDto userInfo(String userid) throws SQLException;
	
//	save완료
	public void saveRefreshToken(Map<String, String> map) throws SQLException;
	
	//마지막 빠샤
	public Object getRefreshToken(String userid) throws SQLException;
	
	//완료
	public void deleteRefreshToken(Map<String, String> map) throws SQLException;

	public void setEmoji(UserDto userDto) throws SQLException;
	
	
}
