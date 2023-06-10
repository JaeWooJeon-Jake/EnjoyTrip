package com.ssafy.luckytrip.plan.model.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.luckytrip.attraction.model.AttractionDto;
import com.ssafy.luckytrip.attraction.model.mapper.AttractionMapper;
import com.ssafy.luckytrip.plan.model.CourseDto;
import com.ssafy.luckytrip.plan.model.PlanDto;
import com.ssafy.luckytrip.plan.model.mapper.PlanMapper;

@Service
public class PlanServiceImpl implements PlanService {
	
	private PlanMapper planMapper;
	private AttractionMapper attractionMapper;
	
	public PlanServiceImpl(PlanMapper planMapper, AttractionMapper attractionMapper) {
		super();
		this.planMapper = planMapper;
		this.attractionMapper = attractionMapper;
	}

	@Transactional
	@Override
	public boolean writePlan(Map<String, Object> params) throws Exception {
		PlanDto plan = new PlanDto();
		plan.setPlanName(params.get("planName").toString());
		plan.setUserId(params.get("userId").toString());
		plan.setPeriod((int) params.get("period"));
//		plan.setStartDate(Timestamp.valueOf(params.get("startDate").toString()));
//		plan.setEndDate((Timestamp.valueOf(params.get("endDate").toString())));
		plan.setStartDate(Timestamp.valueOf("2023-05-24 00:00:00"));
		plan.setEndDate(Timestamp.valueOf("2023-05-26 00:00:00"));
		planMapper.writePlan(plan);
		System.out.println("작성된 계획 번호 " + plan.getPlanNo());
		
		List<CourseDto> list = new ObjectMapper().convertValue(params.get("course"), new TypeReference<List<CourseDto>>() {});
		System.out.println("총 여행 코스: " + list);
		for(CourseDto course : list) {
			course.setPlanNo(plan.getPlanNo());
			System.out.println("개별 여행 코스: " + course);
			if(planMapper.writePlanCourse(course) != 1) return false;
		}
		return true;
	}
	
	public List<PlanDto> getPlanList() throws Exception {
		return planMapper.getPlanList();
	}

	@Transactional
	@Override
	public Map<String, Object> getPlanDetail(int planNo) throws Exception {
		/*
		 * 순서
		 * 1. plan 테이블에서 정보 가져오기
		 * 2. course 테이블 정보  가져오기
		 * 3. attraction 상세 정보 가져오기
		 *  */
		Map<String, Object> result = new HashMap<String, Object>();
		PlanDto plan = planMapper.getPlanDetail(planNo);
//		System.out.println(plan);
		List<Map<String, Object>> list = planMapper.getCourseList(planNo);
//		System.out.println(list);
		List<List<AttractionDto>> courseList = new ArrayList<>();
		// String으로 받은 seq를 Array타입으로 변환 
		for(int i=0; i<list.size(); i++) {
			String[] seqArr = list.get(i).get("seq").toString().replaceAll("[\\[\\]\\\"\\s]", "").split(",");
			List<AttractionDto> attractionList = new ArrayList<>();
			for(int j=0; j<seqArr.length; j++) {
				AttractionDto attraction = attractionMapper.getAttractionDetail(Integer.parseInt(seqArr[j]));
				attractionList.add(attraction);
			}
			courseList.add(attractionList);
		}
		result.put("plan", plan);
		result.put("course", courseList);
		return result;
	}

	@Override
	public boolean deletePlan(int planNo) throws Exception {
		return planMapper.deletePlan(planNo)==1;
	}

	@Override
	public Boolean findPlanLike(Map<String, Object> params) throws Exception {
		return planMapper.findPlanLike(params)!=null;
	}

	@Override
	public Boolean createPlanLike(Map<String, Object> params) throws SQLException {
		return planMapper.createPlanLike(params)==1;
	}
	
	@Override
	public Boolean modifyPlanLike(Map<String, Object> params) throws Exception {
		return planMapper.modifyPlanLike(params)==1;
	}

}
