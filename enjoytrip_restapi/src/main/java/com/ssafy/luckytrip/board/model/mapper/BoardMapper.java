package com.ssafy.luckytrip.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.luckytrip.board.model.BoardDto;
import com.ssafy.luckytrip.board.model.CommentDto;

@Mapper
public interface BoardMapper {

public List<BoardDto> getBoardListAll() throws SQLException;
	
	public BoardDto getBoardView(int boardNo) throws SQLException;
	public void registBoardView(int boardNo)throws SQLException;
	
	public int writeBoard(BoardDto boardDto) throws SQLException;
	
	public int updateBoard(BoardDto boardDto) throws SQLException; 
	
	public int deleteBoard(int boardNo) throws SQLException;

	public int writeBoardComment(CommentDto commentDto) throws SQLException;

	public List<CommentDto> getBoardListCommentAll(int boardNo) throws SQLException;

	public int deleteBoardComment(int commentNo) throws SQLException;

	public int likeBoard(Map<String, Object> map) throws SQLException;

	public int likeComment(Map<String, Object> map)throws SQLException;

	
}
