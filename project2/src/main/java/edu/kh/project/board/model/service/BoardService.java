package edu.kh.project.board.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.board.model.vo.Board;

public interface BoardService {

	
	/** 게시판 이름 조회
	 * @return
	 */
	List<Map<String,Object>> selectBoardType();

	/** 특정 게시판 목록 조회 + 페이징 처리 계산
	 * @param boardCode
	 * @param cp
	 * @return map
	 */
	Map<String, Object> selectBoardList(int boardCode, int cp);
	
	/** 게시글 상세조회
	 * @param boardNo
	 * @return board
	 */
	Board selectBoardDetail(int boardNo);

	int updateReadCount(int boardNo);

	int boardLikeCheck(Map<String, Object> map);

	int boardLikeUp(Map<String, Object> paramMap);

	int boardLikeDown(Map<String, Object> paramMap);

	int getBoardDelete(int boardNo);

	int boardWrite(Board board, List<MultipartFile> imageList, String webpath, String folderPath) throws IOException;
	
	

}
