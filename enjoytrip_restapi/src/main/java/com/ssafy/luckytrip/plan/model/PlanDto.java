package com.ssafy.luckytrip.plan.model;

import java.sql.Timestamp;
import java.util.List;

import com.ssafy.luckytrip.attraction.model.AttractionDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PlanDto (여행계획 정보)", description = "여행계획 번호, 여행계획 이름, 작성자, 여행기간, 여행 시작일, 여행 종료일, 여행계획 작성일자")
public class PlanDto {

	@ApiModelProperty(value = "여행계획 번호")
	private int planNo;
	@ApiModelProperty(value = "여행계획 이름")
	private String planName;
	@ApiModelProperty(value = "작성자")
	private String userId;
	@ApiModelProperty(value = "여행기간")
	private int period;
	@ApiModelProperty(value = "여행 시작일")
	private Timestamp startDate;
	@ApiModelProperty(value = "여행 종료일")
	private Timestamp endDate;
	@ApiModelProperty(value = "여행계획 작성일자")
	private Timestamp registDate;
	
	public int getPlanNo() {
		return planNo;
	}
	public void setPlanNo(int planNo) {
		this.planNo = planNo;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public Timestamp getRegistDate() {
		return registDate;
	}
	public void setRegistDate(Timestamp registDate) {
		this.registDate = registDate;
	}
	
	@Override
	public String toString() {
		return "PlanDto [planNo=" + planNo + ", planName=" + planName + ", userId=" + userId + ", period=" + period
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", registDate=" + registDate + "]";
	}
}
