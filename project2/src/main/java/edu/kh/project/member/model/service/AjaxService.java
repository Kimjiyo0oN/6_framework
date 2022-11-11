package edu.kh.project.member.model.service;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import edu.kh.project.member.model.vo.Member;

// 서비스 인터페이스 왜 만들었지>
// 설계 유지 보수성 
public interface AjaxService {

	/**이메일 중복 검사
	 * @param memberEmail
	 * @return
	 */
	int emailDupCheck(String memberEmail);

	/**닉네임 중복 검사
	 * @param memberNickname
	 * @return
	 */
	int nicknameDupCheck(String memberNickname);

	Member selectEmail(String email);

	List<Member> selectMemberList();
	

	
}
