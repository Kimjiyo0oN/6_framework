package edu.kh.project.member.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kh.project.member.model.vo.Member;

@Repository //DB 연결하는 역할 + bean 등록
public class AjaxDAO {
	
	@Autowired // 같은 자료형이 bean으로 등록되어 있으면 자동으로 DI
	private SqlSessionTemplate sqlsession;
	//SqlSessionTemplate : 커넥션 + 마이바티스 + 스프링 TX 제어
	
	
	public int emailDupCheck(String memberEmail) {
		// TODO Auto-generated method stub
		return sqlsession.selectOne("ajaxMapper.emailDupCheck",memberEmail);
	}


	public int nicknameDupCheck(String memberNickname) {
		return sqlsession.selectOne("ajaxMapper.nicknameDupCheck",memberNickname);
	}


	public Member selectEmail(String email) {
		// TODO Auto-generated method stub
		return sqlsession.selectOne("ajaxMapper.selectEmail",email);
	}


	/**회원 목록 조회
	 * @return memberList
	 */
	public List<Member> selectMemberList() {
		// selectList() :
		// 조회 결과의 각 행을 resultType 또는 
		return sqlsession.selectList("ajaxMapper.selectMemberList");
	}
	
	
	

}
