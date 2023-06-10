package com.ssafy.luckytrip.board.model;

import java.sql.Timestamp;
import java.time.Instant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "BoardDto (게시글 정보)", description = "게시글의 번호, 유형, 제목, 내용, 작성자 아이디, 작성자 이름, 등록일, 조회수 저장")
public class BoardDto {

	@ApiModelProperty(value = "게시글 번호")
	private int boardNo;
	@ApiModelProperty(value = "유형 : 일반, 추천, 후기")
	private String type;
	@ApiModelProperty(value = "제목")
	private String title;
	@ApiModelProperty(value = "내용")
	private String content;
	@ApiModelProperty(value = "작성자 아이디")
	private String userId;
	@ApiModelProperty(value = "작성자 이름")
	private String userName;
	@ApiModelProperty(value = "등록일")
	private String registDate;
	@ApiModelProperty(value = "조회수")
	private int viewCnt;
	@ApiModelProperty(value = "이모지")
	private int emoji;
	@ApiModelProperty(value = "댓글 개수")
	private int commentCnt;
	@ApiModelProperty(value = "좋아요 개수")
	private int likeCnt;
	
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	public int getViewCnt() {
		return viewCnt;
	}
	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}
	
	public int getEmoji() {
		return emoji;
	}
	public void setEmoji(int emoji) {
		this.emoji = emoji;
	}
	
	public int getLikeCnt() {
		return likeCnt;
	}
	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}
	
	public int getCommentCnt() {
		return commentCnt;
	}
	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}
	@Override
	public String toString() {
		return "BoardDto [boardNo=" + boardNo + ", type=" + type + ", title=" + title + ", content=" + content
				+ ", userId=" + userId + ", userName=" + userName + ", registDate=" + registDate + ", viewCnt="
				+ viewCnt + ", emoji=" + emoji + ", commentCnt=" + commentCnt + ", likeCnt=" + likeCnt + "]";
	}
}
