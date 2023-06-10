package com.ssafy.luckytrip.plan.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.luckytrip.plan.model.PlanDto;

public interface PlanService {

	public boolean writePlan(Map<String, Object> params) throws Exception;
	
	public List<PlanDto> getPlanList() throws Exception;
	
	public Map<String, Object> getPlanDetail(int planNo) throws Exception;
	
	public boolean deletePlan(int planNo) throws Exception;
	
	public Boolean findPlanLike(Map<String, Object> params) throws Exception;
	
	public Boolean createPlanLike(Map<String, Object> params) throws SQLException;
	
	public Boolean modifyPlanLike(Map<String, Object> params) throws Exception;
	
}
