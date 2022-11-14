package edu.kh.project.member.model.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.member.model.vo.Member;

// 설계 + 유지보수 + AOP
public interface MyPageService {

	//묵시적으로 지정 되어 있다 
	/** 회원정보 수정
	 * @param inputMember
	 * @return result
	 */
	/* public abstract */ int updateInfo(Member inputMember);

	
	/** 비밀번호 변경 서비스
	 * @param paramMap
	 * @return result
	 */
	int changePw(Map<String, Object> paramMap);


	int memberDelete(int memberNo, String memberPw);


	int updateProfile(String webPath, String filePath, MultipartFile profileImage, Member loginMember) throws Exception;
	
}
