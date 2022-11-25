package edu.kh.project.board.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.board.model.dao.BoardDAO;
import edu.kh.project.board.model.exception.BoardUpdateException;
import edu.kh.project.board.model.vo.Board;
import edu.kh.project.board.model.vo.BoardImage;
import edu.kh.project.board.model.vo.Pagination;
import edu.kh.project.common.Util;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDAO dao;

	@Override
	public List<Map<String, Object>> selectBoardType() {
		return dao.selectBoardType();
	}

	/**
	 * 특정 게시판 목록 조회 + 페이징 처리 계산
	 */
	@Override
	public Map<String, Object> selectBoardList(int boardCode, int cp) {
		
		// 1.특정 게시판의 전체 게시글 수 조회(단, 삭제 제외)
		int listCount = dao.getListCount(boardCode);
		
		// 2. 전체 게시글 수 + cp(현재 페이지)이용해서 
		// 페이징 처리 객체 생성
		Pagination pagination = new Pagination(listCount,cp); //게시판 게시글 몇개 정렬인지도 매개변수 정해줌
			 
		// 3. 페이징 처리객체를 이용해서 게시글 목록 조회
		List<Board> boardList = dao.selectBoardList(pagination,boardCode); 
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("pagination", pagination);
		map.put("boardList",boardList);
		
		return map;
	}
	
	// 게시글 상세 조회 + 이미지 목록 조회 + 댓글 목록 조회
		@Override
		public Board selectBoardDetail(int boardNo) {

			return dao.selectBoardDetail(boardNo);
		}
		

		@Override
		public int updateReadCount(int boardNo) {
			
			return dao.updateReadCount(boardNo);
		}

		@Override
		public int boardLikeCheck(Map<String, Object> map) {
			
			return dao.boardLikeCheck(map);
		}
		

		@Override
		public int boardLikeUp(Map<String, Object> paramMap) {
			
			return dao.boardLikeUp(paramMap);
		}


		@Override
		public int boardLikeDown(Map<String, Object> paramMap) {
			
			return dao.boardLikeDown(paramMap);
		}

		@Override
		public int getBoardDelete(int boardNo) {
			
			return dao.boardDelete(boardNo);
		}
		
		//게시글 삽입
		//@Transactional : 예외 발생시 서비스 내에서 수행한 모든 DML 롤백
		//                 단, RuntimeExceptin 발생 시에만 롤백
		@Transactional(rollbackFor = Exception.class) //모든 예외 발생시 롤백
		@Override
		public int boardWrite(Board board, List<MultipartFile> imageList, String webpath, String folderPath) throws IOException {
			
			// 1. 게시글만 삽입
			// 1) XSS(크로스 사이트 스크립트 공격), 개행문자 처리
			board.setBoardTitle(Util.XSSHandling(board.getBoardTitle()));
			board.setBoardContent(Util.XSSHandling(board.getBoardContent()));
			
			board.setBoardContent(Util.newLineHandling(board.getBoardContent()));
			// 2) 게시글 삽입 DAO 호출 후 
			//    결과로 삽입된 게시글 번호 반환 받기
			int boardNo = dao.boardWrite(board);
			
			// 2. 이미지만 삽입 
			if(boardNo > 0 ) {
				//imageList : 실제 파일이 담겨있는 리스트 
				//boardImageList : DB에 삽입할 이미지 정보만 담겨있는 리스트
				// raNameList : 변경된 파일명만 담겨있는 리스트
				
				List<BoardImage> boardImageList = new ArrayList<BoardImage>(); 
				// 01234 리스트가 있으면 024에만 이미지를 넣으면 나머지는 데이터가 없음
				// 실제로 이미지가 들어가있는 리스트만 저장하는 용도로 만듦
				List<String> reNameList = new ArrayList<String>();
				
				// imageList에 담겨있는 파일 중
				// 실제로 업로드된 파일만 분류하는 작업 진행
				
				for(int i=0; i<imageList.size();i++) {
					// i번째 파일의 크기가 0보다 크다 == 업로드된 파일이 있다.
					if(imageList.get(i).getSize() > 0) {
						// BoardImage 객체 생성
						BoardImage img = new BoardImage();
						
						// BoardImage 값 세팅
						img.setImagePath(webpath);
					
						//원본파일명 -> 변경된 파일명
						String reName = Util.fileRename(imageList.get(i).getOriginalFilename());
						img.setImageReName(reName);
						reNameList.add(reName); //변경파일명 리스트에 추가
						
						img.setImageOriginal(imageList.get(i).getOriginalFilename());
						img.setBoardNo(boardNo);
						img.setImageOrder(i); //이미지 순서
						
						// boardImageList에 추가 
						boardImageList.add(img);
					}
				}
				
				//boardImageList가 비어있지 않다면 
				//== 업로드된 파일이 있어서 분류된 내용이 존재
				if(!boardImageList.isEmpty()) {
					//DB에 업로드된 파일 정보 INSERT
					int result = dao.insertBoardImageList(boardImageList);
					
					// 삽입 결과 행의 수 == DB에 삽입하려고 분류한 리스트의 크기
					if(result == boardImageList.size()) {
						// 파일 변환 작업
						for(int i =0; i < boardImageList.size(); i++) {
							//순서 == imageList의 인덱스 
							int index = boardImageList.get(i).getImageOrder();
							
							// 실제 파일로 변환
							imageList.get(index).transferTo(new File(folderPath+ reNameList.get(i)));
						}
					}
				}
				
			}
			
			return boardNo;
		}

		@Override
		public Board selectBoardUpdate(int boardNo) {
			Board board = dao.selectBoardUpdate(boardNo);
			return board;
		}

		@Override
		public List<BoardImage> selectBoardImgUpdate(int boardNo) {
			List<BoardImage> imageList = dao.selectBoardImgUpdate(boardNo);
			
			return imageList;
		}

		/** 게시글 수정
		 *
		 */
		@Transactional(rollbackFor = Exception.class) //모든 예외 발생시 롤백
		@Override
		public int boardUpdate(Board board, List<MultipartFile> imageList, String webPath, String folderPath,
				String deleteList) throws Exception {
			
			// 1. 게시글 부분만 수정
			// 1) XSS 방지 처리,개행 문자 처리
			board.setBoardTitle(Util.XSSHandling(board.getBoardTitle()));
			board.setBoardContent(Util.XSSHandling(board.getBoardContent()));
			board.setBoardContent(Util.newLineHandling(board.getBoardContent()));
			
			// 2) DAO 호출 
			int result = dao.boardUpdate(board); //제목 내용 게시글번호
			
			
			
			// 2. 이미지 수정 
			if(result > 0) { //게시글이 정상적으로 수정된 경우
				//1) 삭제된 이미지가 있을 경우 삭제 진행
				if(!deleteList.equals("")) {
					//deleteList : "1,2,3"
					
					// WHERE BOARD_NO = 2007 AND IMG_ORDER IN(1,2,3)
					String condition = "WHERE BOARD_NO = " + board.getBoardNo()
							+ " AND IMG_ORDER IN(" + deleteList + ")";
					
					//2) DAO 호출 
					result = dao.boardImageDelete(condition);
					
					//result =0; //테스트 용도
					
					//3) 삭제 실패 시 
					if(result == 0) {
						//강제로 예외를 발생시켜 롤백 수행 
						throw new BoardUpdateException("이미지 삭제 실패");
					}
				} //1) if 끝
					
				//2) 분류작업 
				//2) imageList에서 실제 업로드된 파일을 찾아 분류하는 작업
				//imageList : 실제 파일이 담겨있는 리스트 
				//boardImageList : DB에 삽입할 이미지 정보만 담겨있는 리스트
				// raNameList : 변경된 파일명만 담겨있는 리스트
				
				List<BoardImage> boardImageList = new ArrayList<BoardImage>(); 
				// 01234 리스트가 있으면 024에만 이미지를 넣으면 나머지는 데이터가 없음
				// 실제로 이미지가 들어가있는 리스트만 저장하는 용도로 만듦
				List<String> reNameList = new ArrayList<String>();
				
				// imageList에 담겨있는 파일 중
				// 실제로 업로드된 파일만 분류하는 작업 진행
				
				for(int i=0; i<imageList.size();i++) {
					// i번째 파일의 크기가 0보다 크다 == 업로드된 파일이 있다.
					if(imageList.get(i).getSize() > 0) {
						// BoardImage 객체 생성
						BoardImage img = new BoardImage();
						
						// BoardImage 값 세팅
						img.setImagePath(webPath);
					
						//원본파일명 -> 변경된 파일명
						String reName = Util.fileRename(imageList.get(i).getOriginalFilename());
						img.setImageReName(reName);
						reNameList.add(reName); //변경파일명 리스트에 추가
						
						img.setImageOriginal(imageList.get(i).getOriginalFilename());
						img.setBoardNo(board.getBoardNo());
						img.setImageOrder(i); //이미지 순서
						
						// boardImageList에 추가 
						boardImageList.add(img);
						
						//새로 업로드된 이미지 정보를 이용해서 DB 정보 수정
						// -> 새로운 이미지가 기존에 존재했는 데 수정한건지
						//   없었는데 추가한건지 현재 코드 작성 시점에서는 알수 없다
						// --> 일단 순서(IMG_ORDER)를 이용해서 수정
						// --> 만약 BOAD_IMG 테이블에 IMG_ORDER 가 일치하는 행이 없다면 
						// 수정 실패 == 0 반환 == 기존에 없었다 == 새로운 이미지 
						//  == INSERT 필요
						
						
						
						
						
						result = dao.boardImageUpdate(img); //일단 업데이트
						if(result == 0) {
							result = dao.boardImageInsert(img); // 기존에 이미지 없을 때 삽입(새로운 위치에 이미지 삽입)
							
							if(result == 0) { //이미지 삽입 실패
								throw new BoardUpdateException("이미지 수정/삽입 예외");
								
							}
						}
					}
				} //for 끝
				
				// 분류 작업 결과물 (boardImageList, reNameList)을 이용해서 
				// 파일을 서버에 저장
				if(!boardImageList.isEmpty()) {
					// 서버에 이미지 저장
					for(int i =0; i < boardImageList.size(); i++) {
						int index = boardImageList.get(i).getImageOrder();
						
						imageList.get(index).transferTo(new File(folderPath + reNameList.get(i)));
					}
				}
				
			}
			
			return result;
		}

		@Override
		public Map<String, Object> selectBoardList(Map<String, Object> pm, int cp) {
			// 1.검색 조건이 일치하는 전체 게시글 수 조회(단, 삭제 제외)
			int listCount = dao.getListCount(pm);
			
			// 2. 검색 조건 일치 게시글 수 + cp(현재 페이지)이용해서 
			// 페이징 처리 객체 생성
			Pagination pagination = new Pagination(listCount,cp); //게시판 게시글 몇개 정렬인지도 매개변수 정해줌
				 
			// 3. 페이징 처리객체를 이용해서 게시글 목록 조회
			List<Board> boardList = dao.selectBoardList(pagination,pm); 
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("pagination", pagination);
			map.put("boardList",boardList);
			
			return map;

		}
}
