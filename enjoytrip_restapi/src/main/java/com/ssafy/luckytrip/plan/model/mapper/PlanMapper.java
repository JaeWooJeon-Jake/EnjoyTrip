package com.ssafy.luckytrip.plan.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.luckytrip.plan.model.CourseDto;
import com.ssafy.luckytrip.plan.model.PlanDto;
import com.ssafy.luckytrip.plan.model.PlanLikeDto;

@Mapper
public interface PlanMapper {
	
	public int writePlan(PlanDto plan) throws SQLException;
	
	public int writePlanCourse(CourseDto course) throws SQLException;
	
	public List<PlanDto> getPlanList() throws SQLException;
	
	public PlanDto getPlanDetail(int planNo) throws SQLException;

	public List<Map<String, Object>> getCourseList(int planNo) throws SQLException;
	
	public int deletePlan(int planNo) throws SQLException;
	
	public PlanLikeDto findPlanLike(Map<String, Object> params) throws SQLException;
	
	public int createPlanLike(Map<String, Object> params) throws SQLException;
	
	public int modifyPlanLike(Map<String, Object> params) throws SQLException;
	
}
