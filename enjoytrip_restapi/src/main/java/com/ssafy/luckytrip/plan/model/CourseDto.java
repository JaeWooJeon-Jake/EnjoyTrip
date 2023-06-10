package com.ssafy.luckytrip.plan.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PlanDto (여행계획 정보)", description = "여행계획 번호, 여행날짜, 여행지 순번")
public class CourseDto {

	@ApiModelProperty(value = "여행계획 번호")
	private int planNo;
	@ApiModelProperty(value = "여행날짜")
	private int day;
	@ApiModelProperty(value = "여행지 순번")
	private List<String> seq;
	
	public int getPlanNo() {
		return planNo;
	}
	public void setPlanNo(int planNo) {
		this.planNo = planNo;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public List<String> getSeq() {
		return seq;
	}
	public void setSeq(List<String> seq) {
		this.seq = seq;
	}
	@Override
	public String toString() {
		return "CourseDto [planNo=" + planNo + ", day=" + day + ", seq=" + seq + "]";
	}
}
