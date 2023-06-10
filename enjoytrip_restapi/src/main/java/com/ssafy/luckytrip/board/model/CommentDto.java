package com.ssafy.luckytrip.board.model;

import java.sql.Timestamp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CommentDto (게시글 댓글 정보)", description = "게시글의 번호, 내용, 작성자 아이디, 작성자 이름")
public class CommentDto {

	@ApiModelProperty(value = "게시글 번호")
	private int boardNo;
	@ApiModelProperty(value = "댓글 번호")
	private int commentNo;
	@ApiModelProperty(value = "작성자 아이디")
	private String userId;
	@ApiModelProperty(value = "내용")
	private String content;
	@ApiModelProperty(value = "등록일")
	private String registDate;
	@ApiModelProperty(value = "작성자 이름")
	private String userName;
	@ApiModelProperty(value = "이모지")
	private int emoji;

	@ApiModelProperty(value = "좋아요 개수")
	private int likeCnt;
	
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	@Override
	public String toString() {
		return "CommentDto [boardNo=" + boardNo + ", commentNo=" + commentNo + ", userId=" + userId + ", content="
				+ content + ", registDate=" + registDate + ", userName=" + userName + ", emoji=" + emoji + ", likeCnt="
				+ likeCnt + "]";
	}
}
