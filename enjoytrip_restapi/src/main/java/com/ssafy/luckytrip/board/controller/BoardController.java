package com.ssafy.luckytrip.board.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.luckytrip.board.model.BoardDto;
import com.ssafy.luckytrip.board.model.CommentDto;
import com.ssafy.luckytrip.board.model.service.BoardService;
import com.ssafy.luckytrip.user.model.UserDto;
import com.ssafy.luckytrip.user.model.service.JwtService;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/board")
@Api(tags = { "게시판" })
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Value("${file.path}")
	private String uploadPath;


	private BoardService boardService;
	private JwtService jwtService;
	
	public BoardController(BoardService boardService, JwtService jwtService) {
		super();
		this.boardService = boardService;
		this.jwtService = jwtService;
	}
	

	@ApiOperation(value = "게시글 목록", notes = "게시글의 <b>전체 목록</b>을 리턴합니다.")
	@GetMapping("/list")
	public ResponseEntity<List<BoardDto>> getBoardListAll() throws Exception {
		List<BoardDto> list = boardService.getBoardListAll();
		return new ResponseEntity<List<BoardDto>>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "게시글 상세", notes = "특정 게시글의 <b>상세정보</b>를 리턴합니다.")
	@ApiImplicitParams({ @ApiImplicitParam(name = "boardNo", value = "게시글 번호", required = true, dataType = "Integer") })
	@GetMapping("/{boardNo}")
	public ResponseEntity<BoardDto> getBoardView(@PathVariable int boardNo) throws Exception {
		BoardDto boardDto = boardService.getBoardView(boardNo);
		logger.debug("게시글 BoardDto : {}", boardDto);
		logger.debug("detail내용 : {}", boardDto);
		return new ResponseEntity<BoardDto>(boardDto, HttpStatus.OK);
	}

	/* 이후에 파일 업로드 추가해야 함 */
	@ApiOperation(value = "게시글 작성", notes = "게시글을 작성합니다.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "boardDto", value = "게시글 정보", required = true, dataType = "BoardDto") })
	@PostMapping()
	public ResponseEntity<String> writeBoard(@RequestHeader("Authorization") String accessToken, @RequestBody BoardDto boardDto)
			throws Exception {
		Map<String, Object> tokenClaims = jwtService.get(accessToken);
		boardDto.setUserId((String) tokenClaims.get("userid"));
		logger.debug("token Check : {}", tokenClaims);
//		logger.debug("writeBoard BoardDto : {}", boardDto);
		if(boardService.writeBoard(boardDto))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "게시글 수정", notes = "게시글을 수정합니다.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "boardDto", value = "게시글 정보", required = true, dataType = "BoardDto") })
	@PutMapping()
	public ResponseEntity<String> updateBoard(@RequestBody BoardDto boardDto) throws Exception {
		logger.debug("updateBoard BoardDto : {}", boardDto);
		if(boardService.updateBoard(boardDto))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제합니다.")
	@ApiImplicitParams({ @ApiImplicitParam(name = "boardNo", value = "게시글 번호", required = true, dataType = "Integer") })
	@DeleteMapping("/{boardNo}")
	public ResponseEntity<String> deleteBoard(@PathVariable int boardNo) throws Exception {
		logger.debug("deleteBoard BoardNo : {}", boardNo);
		if(boardService.deleteBoard(boardNo))
			return new ResponseEntity<String>("글 삭제 완료", HttpStatus.OK);
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
//댓글 관련된 메소드	
//	1.Create
	@ApiOperation(value = "댓글 작성", notes = "댓글을 작성합니다.")
	@PostMapping("/{boardNo}/comment")
	public ResponseEntity<String> writeBoardComment(@PathVariable("boardNo") int boardNo, @RequestBody CommentDto commentDto)
			throws Exception {
		commentDto.setBoardNo(boardNo);
		logger.debug("writeBoardComment comment : {}", commentDto);
		if(boardService.writeBoardComment(commentDto))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "댓글 가져오기", notes = "댓글을 가져옵니다..")
	@GetMapping("/{boardNo}/commentList")
	public ResponseEntity<List<CommentDto>> getBoardComments(@PathVariable("boardNo") int boardNo) throws Exception {
		List<CommentDto> list = boardService.getBoardListCommentAll(boardNo);
		logger.debug("comment list : {}", list);
		return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제합니다.")
	@DeleteMapping("/{commentNo}/comment")
	public ResponseEntity<String> deleteBoardComment(@PathVariable int commentNo) throws Exception {
		logger.debug("deleteBoard BoardNo : {}", commentNo);
		if(boardService.deleteBoardComment(commentNo))
			return new ResponseEntity<String>("글 삭제 완료", HttpStatus.OK);
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	/* 이후에 파일 업로드 추가해야 함 */
	@ApiOperation(value = "게시글 좋아요", notes = "게시글 좋아요눌리기.")
	@PostMapping("/like/{boardNo}")
	public ResponseEntity<String> likeBoard(@RequestHeader("Authorization") String accessToken, @PathVariable int boardNo)
			throws Exception {
		logger.debug("token확인 {}", accessToken);
		Map<String, Object> tokenClaims = jwtService.get(accessToken);
		tokenClaims.put("boardNo",boardNo);
		logger.debug("token Check : {}", tokenClaims);
//		logger.debug("writeBoard BoardDto : {}", boardDto);
		if(boardService.likeBoard(tokenClaims))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "게시글 좋아요", notes = "게시글 좋아요눌리기.")
	@PostMapping("comment/like/{commentNo}")
	public ResponseEntity<String> likeComment(@RequestHeader("Authorization") String accessToken, @PathVariable int commentNo)
			throws Exception {
		logger.debug("token확인 {}", accessToken);
		Map<String, Object> tokenClaims = jwtService.get(accessToken);
		tokenClaims.put("commentNo",commentNo);
		logger.debug("token Check : {}", tokenClaims);
//		logger.debug("writeBoard BoardDto : {}", boardDto);
		if(boardService.likeComment(tokenClaims))
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
}
