package com.ssafy.luckytrip.plan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.luckytrip.plan.model.PlanDto;
import com.ssafy.luckytrip.plan.model.service.PlanService;
import com.ssafy.luckytrip.user.model.service.JwtService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/plan")
@Api(tags = {"여행계획"})
public class PlanController {

	private static final Logger logger = LoggerFactory.getLogger(PlanController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private PlanService planService;

	public PlanController(PlanService planService) {
		super();
		this.planService = planService;
	}

	@ApiOperation(value = "여행계획 작성", notes = "여행계획을 작성합니다.")
	@PostMapping("/write")
	public ResponseEntity<String> writePlan(@RequestBody Map<String, Object> params) throws Exception {
		logger.debug("writePlan PlanDto {}", params);
		if(planService.writePlan(params)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} 
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	@ApiOperation(value = "여행계획 리스트", notes = "여행계획 목록을 불러옵니다.")
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> getPlanList() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<PlanDto> planList = null;
		String message = null;
		try {
			planList = planService.getPlanList();
			message = SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			message = FAIL;
		}
		result.put("message", message);
		result.put("planList", planList);
		return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "여행계획 상세", notes = "여행계획 상세내용을 불러옵니다.")
	@GetMapping("/detail/{planNo}")
	public ResponseEntity<Map<String, Object>> getPlanDetail(@RequestParam("userId") String userId, @PathVariable("planNo") int planNo) throws Exception {
		logger.debug("getPlanDetail planNo {} and userId {}", planNo, userId);
		Map<String, Object> result = null;
		String message = null;
		// 해당 번호의 여행계획 정보 가져오기
		result = planService.getPlanDetail(planNo);
		if(result != null) {
			message = SUCCESS;
		} else {
			message = FAIL;
		}
		result.put("message", message);
		// 해당 번호의 좋아요 가져오기(로그인 했을때만)
		Boolean isLike = false;
		if(userId != null || userId.equals("")) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("planNo", planNo);
			params.put("userId", userId);
			isLike = planService.findPlanLike(params);
		}
		result.put("isLike", isLike);
		return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "여행계획 삭제", notes = "여행계획 상세내용을 불러옵니다.")
	@DeleteMapping("/delete/{planNo}")
	public ResponseEntity<String> deletePlan(@PathVariable("planNo") int planNo) throws Exception {
		logger.debug("deletePlan planNo {}", planNo);
		if(planService.deletePlan(planNo)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	@ApiOperation(value = "여행계획 좋아요 상태 변경", notes = "특정 여행계획의 좋아요 상태를 변경합니다.")
	@PutMapping("/modifyLike")
	public ResponseEntity<Map<String,Object>> modifyPlanLike(@RequestBody Map<String,Object> params) throws Exception {
		logger.debug("modifyPlanLike params {}", params);
		Map<String,Object> result = new HashMap<String, Object>();
		/*
		 * 순서
		 * 1. plan_like 테이블에 이미 값이 존재하는지 확인
		 * 2. 존재 여부에 따른 이벤트 수행(없으면: create, 있으면 update)
		 *  */
		String message = null;
		boolean isLike = false;
		if(planService.findPlanLike(params)) {
			// update
			System.out.println("기존 값 수정");
			if(planService.modifyPlanLike(params)) {
				isLike = (boolean) params.get("isLike");
				message = SUCCESS;
			} else {
				message = FAIL;
			}
		} else {
			// create
			System.out.println("새로 생성");
			if(planService.createPlanLike(params)) {
				isLike = true;
				message = SUCCESS;
			} else {
				message = FAIL;
			}
		}
		result.put("message", message);
		result.put("isLike", isLike);
		return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "여행계획 좋아요 확인", notes = "특정 여행계획의 좋아요 상태를 확인합니다.")
	@PostMapping("/findLike")
	public ResponseEntity<Boolean> findPlanLike(@RequestBody Map<String,Object> params) throws Exception {
		logger.debug("findPlanLike params {}", params);
		Boolean result = planService.findPlanLike(params);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
}
