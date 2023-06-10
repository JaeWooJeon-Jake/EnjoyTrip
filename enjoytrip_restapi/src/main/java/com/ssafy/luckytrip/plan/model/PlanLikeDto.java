package com.ssafy.luckytrip.plan.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PlanLikeDto (여행계획 좋아요 정보)", description = "여행계획 번호, 여행계획 이름, 작성자, 여행기간, 여행 시작일, 여행 종료일, 여행계획 작성일자")
public class PlanLikeDto {

	@ApiModelProperty(value = "여행계획 번호")
	private int planNo;
	@ApiModelProperty(value = "유저 아이디")
	private String userId;
	@ApiModelProperty(value = "좋아요 상태")
	private boolean state;

	public int getPlanNo() {
		return planNo;
	}

	public void setPlanNo(int planNo) {
		this.planNo = planNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "PlanLikeDto [planNo=" + planNo + ", userId=" + userId + ", state=" + state + "]";
	}
}
