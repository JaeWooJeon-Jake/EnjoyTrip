package com.ssafy.luckytrip.board.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.luckytrip.board.model.BoardDto;
import com.ssafy.luckytrip.board.model.CommentDto;
import com.ssafy.luckytrip.board.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	
	private BoardMapper boardMapper;
	
	public BoardServiceImpl(BoardMapper boardMapper) {
		super();
		this.boardMapper = boardMapper;
	}

	@Override
	public List<BoardDto> getBoardListAll() throws Exception {
		return boardMapper.getBoardListAll();
	}
	
	@Transactional
	@Override
	public BoardDto getBoardView(int boardNo) throws Exception {
		boardMapper.registBoardView(boardNo);
		return boardMapper.getBoardView(boardNo);
	}

	@Override
	@Transactional
	public boolean writeBoard(BoardDto boardDto) throws Exception {
		return boardMapper.writeBoard(boardDto) == 1;
	}

	@Override
	@Transactional
	public boolean updateBoard(BoardDto boardDto) throws Exception {
		return boardMapper.updateBoard(boardDto) == 1;
	}

	@Override
	@Transactional
	public boolean deleteBoard(int boardNo) throws Exception {
		return boardMapper.deleteBoard(boardNo) == 1;
	}

	@Override
	public boolean writeBoardComment(CommentDto commentDto) throws Exception {
		return boardMapper.writeBoardComment(commentDto) == 1;
	}

	@Override
	public List<CommentDto> getBoardListCommentAll(int boardNo) throws Exception {
		return boardMapper.getBoardListCommentAll(boardNo);
	}

	@Override
	public boolean deleteBoardComment(int commentNo) throws Exception {
		return boardMapper.deleteBoardComment(commentNo) == 1;
	}

	@Override
	public boolean likeBoard(Map<String, Object> map) throws Exception {
		return boardMapper.likeBoard(map) == 1;
	}

	@Override
	public boolean likeComment(Map<String, Object> map) throws Exception {
		return boardMapper.likeComment(map) == 1;
	}
}
