package com.ssafy.luckytrip.user.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.luckytrip.user.model.UserDto;
import com.ssafy.luckytrip.user.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	
	private UserMapper userMapper;

	public UserServiceImpl(UserMapper userMapper) {
		super();
		this.userMapper = userMapper;
	}
	
	@Override
	public boolean idCheck(String id) throws Exception {
		return userMapper.idCheck(id)==1;
	}

	@Override
	public UserDto login(UserDto user) throws Exception {
		return userMapper.login(user);
	}

	@Override
	public boolean join(UserDto userDto) throws Exception {
		return userMapper.join(userDto) == 1;
	}

	@Override
	public boolean modify(UserDto userDto) throws Exception {
		return userMapper.modify(userDto) == 1;
	}

	@Override
	public boolean delete(String id) throws Exception {
		return userMapper.delete(id) == 1;
	}
	
	@Override
	public String searchId(Map<String, String> params) throws Exception {
		return userMapper.searchId(params);
	}

	@Override
	public String searchPwd(Map<String, String> params) throws Exception {
		return userMapper.searchPwd(params);
	}
	
// JWT관련
	@Override
	public UserDto userInfo(String userid) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.userInfo(userid);
	}

	@Override
	public void saveRefreshToken(String userid, String refreshToken) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", refreshToken);
		userMapper.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userid) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.getRefreshToken(userid);
	}

	@Override
	public void deleRefreshToken(String userid) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", null);
		userMapper.deleteRefreshToken(map);
	}

	@Override
	public void setEmoji(UserDto userDto)throws Exception {
		userMapper.setEmoji(userDto);
	}

}
