package edu.kh.project.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kh.project.board.model.vo.Board;
import edu.kh.project.board.model.vo.Pagination;

@Repository
public class BoardDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	/*게시판 이름 목록 조회
	 * @return boardTypeLsit
	 * */
	public List<Map<String, Object>> selectBoardType() {
		
		
		return sqlSession.selectList("boardMapper.selectBoardType");
	}

	/** 게시글 수 조회
	 * @param boardCode
	 * @return listCount
	 */
	public int getListCount(int boardCode) {
		return sqlSession.selectOne("boardMapper.getListCount", boardCode);
	}

	/** 특정 게시판 목록 조회
	 * @param pagination
	 * @param boardCode
	 * @return
	 */
	public List<Board> selectBoardList(Pagination pagination, int boardCode) {
			
		//RowBounds 객체 (마이바티스)
		// -여러 행 조회 결과 중 특정 위치부터 지정된 행의 개수만 조회하는 객체
		// 					(몇 행을 건너 뛸 것인가????)
		
		int offset = (pagination.getCurrentPage()-1) * pagination.getLimit(); // 5페이지일때 4*10(10개 정렬) -> 40개의 게시글을 건너뛰어라
		
		RowBounds rowBounds = new RowBounds(offset, pagination.getLimit());
				
		return sqlSession.selectList("boardMapper.selectBoardList",boardCode, rowBounds); //rowBounds는 항상 3번째
									//  namespace.id            , 파라미터,   rowBounds
									//                          , 파라미터 없을 때 NULL 대입
	}
	
	/** 게시글 상세 조회 + 이미지 목록 조회 + 댓글 목록 조회
	 * @param boardNo
	 * @return board
	 */
	public Board selectBoardDetail(int boardNo) {
		return sqlSession.selectOne("boardMapper.selectBoardDetail", boardNo);
	}

	public int updateReadCount(int boardNo) {
		// TODO Auto-generated method stub
		System.out.println(boardNo);
		return sqlSession.update("boardMapper.updateReadCount", boardNo);
	}

	public int boardLikeCheck(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("boardMapper.boardLikeCheck", map);
	}

	public int boardLikeUp(Map<String, Object> paramMap) {
		
		return sqlSession.insert("boardMapper.boardLikeUp", paramMap);
	}

	public int boardLikeDown(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return sqlSession.delete("boardMapper.boardLikeDown", paramMap);
	}
}
