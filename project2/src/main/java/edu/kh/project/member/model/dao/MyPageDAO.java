package edu.kh.project.member.model.dao;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kh.project.member.model.vo.Member;

//저장소랑 관련이 있는 거고 bean으로 생성하고 관리하게 만들어짐 
//스프링이 bean 등록 + 관리(IoC)
@Repository
public class MyPageDAO {
	@Autowired //스프링으로붙터 bean을 주입 받음(DI)
	private SqlSessionTemplate sqlSession;

	/** 회원 정보 수정 DAO
	 * @param inputMember
	 * @return result
	 */
	public int updateInfo(Member inputMember) {

		
		return sqlSession.update("myPageMapper.updateInfo",inputMember);
	}

	
	/****************************************************************
	 * @param memberNo
	 * @return
	 */
	public String selectEncPw(int memberNo) {
		
		return sqlSession.selectOne("myPageMapper.selectEncPw",memberNo);
	}

	/** 비밀번호 변경하는 DAO
	 * @param paramM
	 * @return
	 */
	public int changePw(Map<String, Object> paramMap) {
		return sqlSession.update("myPageMapper.changePw",paramMap);
	}


	public int memberDelete(int memberNo) {
		return sqlSession.update("myPageMapper.memberDelete",memberNo);
	}
}
