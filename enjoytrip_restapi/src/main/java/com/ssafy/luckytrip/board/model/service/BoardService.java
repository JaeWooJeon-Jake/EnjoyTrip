package com.ssafy.luckytrip.board.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.luckytrip.board.model.BoardDto;
import com.ssafy.luckytrip.board.model.CommentDto;

public interface BoardService {
	
	public List<BoardDto> getBoardListAll() throws Exception;
	
	public BoardDto getBoardView(int boardNo) throws Exception;
	
	public boolean writeBoard(BoardDto boardDto) throws Exception;
	
	public boolean updateBoard(BoardDto boardDto) throws Exception; 
	
	public boolean deleteBoard(int boardNo) throws Exception;

	public boolean writeBoardComment(CommentDto commentDto) throws Exception;

	public List<CommentDto> getBoardListCommentAll(int boardNo) throws Exception;

	public boolean deleteBoardComment(int commentNo) throws Exception;

	public boolean likeBoard(Map<String, Object> map)throws Exception;

	public boolean likeComment(Map<String, Object> map)throws Exception;
	
}
