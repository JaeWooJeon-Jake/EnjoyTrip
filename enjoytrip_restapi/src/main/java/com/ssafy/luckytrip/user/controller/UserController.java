package com.ssafy.luckytrip.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.luckytrip.user.model.UserDto;
import com.ssafy.luckytrip.user.model.service.JwtServiceImpl;
import com.ssafy.luckytrip.user.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/user")
@Api(tags = {"사용자"})
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private JwtServiceImpl jwtService;
	private UserService userService;
	
	public UserController(JwtServiceImpl jwtService, UserService userService) {
		super();
		this.jwtService = jwtService;
		this.userService = userService;
	}

	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.")
	@GetMapping("/info/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(@PathVariable("userid") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userid,
			HttpServletRequest request) {
//		logger.debug("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		if (jwtService.checkToken(request.getHeader("Authorization").substring(7))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				UserDto userDto = userService.userInfo(userid);
				logger.debug("사용 가능한 토큰!!!{}",userDto);
				
				resultMap.put("userInfo", userDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "아이디 중복 체크", notes = "회원 가입 시 아이디를 중복 체크합니다.")
	@GetMapping("/idCheck")
	public ResponseEntity<Boolean> idCheck(@RequestParam String id) throws Exception {
		logger.debug("idCheck id {}", id);
		if(userService.idCheck(id)) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.")
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호)") UserDto userDto) {
//		이거 잠시뒤에 하고 프런트 로직으로 테스트 할 수 있게만들기
		logger.debug("login userDto : {}", userDto);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			UserDto loginUser = userService.login(userDto);
			if(loginUser != null) {
				String accessToken = "Bearer " + jwtService.createAccessToken("userid", loginUser.getId());// key, data
				String refreshToken = "Bearer " + jwtService.createRefreshToken("userid", loginUser.getId());// key, data
				userService.saveRefreshToken(userDto.getId(), refreshToken);
				logger.debug("로그인 accessToken 정보 : {}", accessToken);
				logger.debug("로그인 refreshToken 정보 : {}", refreshToken);
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패: {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}
	
	@ApiOperation(value = "회원 가입", notes = "회원 가입을 합니다.")
	@PostMapping("/join")
	public ResponseEntity<String> join(@RequestBody UserDto userDto) throws Exception {
		logger.debug("join userDto {}", userDto);
		if(userService.join(userDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} 
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	@ApiOperation(value = "회원 정보 수정", notes = "회원 정보를 수정합니다.")
	@PutMapping("/modify")
	public ResponseEntity<Map<String, Object>> modify(@RequestBody UserDto params) throws Exception {
		logger.debug("modify modify {}", params);
		Map<String, Object> result = new HashMap<String, Object>();
		String message = null;
		UserDto user = null;
		if(userService.modify(params)) {
			message = SUCCESS;
			user = userService.userInfo(params.getId());
		} else {
			message = FAIL;
		}
		result.put("message", message);
		result.put("user", user);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴합니다.")
	@DeleteMapping("/delete")
	public ResponseEntity<Map<String, String>> delete(@ApiIgnore HttpSession session) {
		String msg = "";
		String res = "0"; // 삭제 성공 : 1 , 실패: 0
		Map<String, String> result = new HashMap<String, String>();
		UserDto userDto = (UserDto) session.getAttribute("userinfo");
		try {
			userService.delete(userDto.getId());
			msg = "회원정보 삭제 완료 되었습니다.";
			res = "1";
		} catch (Exception e) {
			msg = "회원정보 삭제 실패했습니다.";
			e.printStackTrace();
		}
		result.put("msg", msg);
		result.put("res", res);
		return new ResponseEntity<Map<String,String>>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "아이디 찾기", notes = "아이디가 존재하는지 확인합니다.")
	@PostMapping("/searchId")
	public ResponseEntity<Map<String, String>> searchId(@RequestBody Map<String, String> params) throws Exception {
		logger.debug("searchId searchId {}", params);
		Map<String, String> result = new HashMap<String, String>();
		String userId = null;
		userId = userService.searchId(params);
		result.put("message", SUCCESS);
		result.put("userId", userId);
		return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "비밀번호 찾기", notes = "비밀번호를 확인합니다.")
	@PostMapping("/searchPwd")
	public ResponseEntity<Map<String, String>> searchPwd(@RequestBody Map<String, String> params) throws Exception {
		logger.debug("searchId searchPwd {}", params);
		Map<String, String> result = new HashMap<String, String>();
		String userPwd = null;
		userPwd = userService.searchPwd(params);
		result.put("message", SUCCESS);
		result.put("userPwd", userPwd);
		return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.")
	@GetMapping("/logout/{userid}")
	public ResponseEntity<?> removeToken(@PathVariable("userid") String userid) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			userService.deleRefreshToken(userid);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
		
	}
	
	@ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDto userDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refresh-token").substring(7);
		logger.debug("token : {}, memberDto : {}", token, userDto);
		if (jwtService.checkToken(token)) {
			if (token.equals(userService.getRefreshToken(userDto.getId()))) {
				String accessToken = jwtService.createAccessToken("userid", userDto.getId());
				logger.debug("token : {}", accessToken);
				logger.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
		} else {
			logger.debug("리프레쉬토큰도 사용불!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "emoji정보 수정", notes = "emoji 수정한다.", response = Map.class)
	@PutMapping("/emoji/{index}")
	public ResponseEntity<UserDto> emoji(@RequestHeader("Authorization") String accessToken,@PathVariable int index) throws Exception {
//		logger.debug("start {}", userDto);
		Map<String, Object> tokenClaims = jwtService.get(accessToken);
		UserDto userDto = new UserDto();
		userDto.setEmoji(index);
		userDto.setId((String)tokenClaims.get("userid"));
		userService.setEmoji(userDto);
		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);		
	}
}
