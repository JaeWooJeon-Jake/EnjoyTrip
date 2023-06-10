package com.ssafy.luckytrip.attraction.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.luckytrip.attraction.model.AttractionDto;
import com.ssafy.luckytrip.attraction.model.SidoGugunCodeDto;
import com.ssafy.luckytrip.attraction.model.service.AttractionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/attraction")
@Api(tags = {"관광지"})
public class AttractionController {
	
	private final Logger logger = LoggerFactory.getLogger(AttractionController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private AttractionService attractionService;
	
	public AttractionController(AttractionService attractionService) {
		super();
		this.attractionService = attractionService;
	}
	
	@ApiOperation(value = "시도 목록", notes = "시도 목록을 리턴합니다.", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> getSidos() throws Exception {
		List<SidoGugunCodeDto> list = attractionService.getSidos();
		return new ResponseEntity<List<SidoGugunCodeDto>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "구군 목록", notes = "구군 목록을 리턴합니다.", response = List.class)
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> getGuguns(@RequestParam("sidoCode") int sidoCode) throws Exception {
		List<SidoGugunCodeDto> list = attractionService.getGuguns(sidoCode);
		return new ResponseEntity<List<SidoGugunCodeDto>>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "관광지 목록", notes = "관광지 목록을 리턴합니다.", response = List.class)
	@PostMapping("/list")
	public ResponseEntity<List<AttractionDto>> getAttractionList(@RequestBody Map<String, String> map) throws Exception {
		List<AttractionDto> list = attractionService.getAttractionList(map);
		return new ResponseEntity<List<AttractionDto>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "관광지 상세", notes = "관광지 상세내용을 리턴합니다.", response = AttractionDto.class)
	@GetMapping("/detail")
	public ResponseEntity<Map<String,Object>> getAttractionDetail(@RequestParam("contentId") int contentId) throws Exception {
		logger.debug("getAttractionDetail contentId {}", contentId);
		Map<String,Object> result = new HashMap<String, Object>();
		AttractionDto attraction = null;
		String message = null;
		attraction = attractionService.getAttractionDetail(contentId);
		if(attraction != null) {
			message = SUCCESS;
		} else {
			message = FAIL;
		}
		result.put("message", message);
		result.put("attraction", attraction);
		return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
	}
}
