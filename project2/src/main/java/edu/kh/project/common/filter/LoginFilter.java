package edu.kh.project.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginFilter--web.xml에 등록헤서 사용하는 필터는 등록되어 있는 순서대로
 */
// 필터 등록 + 필터링할 주소 매핑
@WebFilter(filterName = "loginFilter" // 필터 이름,필터가 여러개 존재할 때 순서 지정 시 사용 
, urlPatterns = {"/member/myPage/*","/member/logout"}) // 필터링한 요청 주소(패턴 가능)-- {} : 그냥 자바에서는 배열 의미 / 자바스크립트에서는 객체 
//해당 클래스가 필터라고 지정해주고 필터링할 주소랑 매핑
public class LoginFilter implements Filter {
	
	/**
	 * @see Filter#init(FilterConfig) --초기화 / 필터 생성시 수행 
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("로그인 필터 생성");
	}
	
	/**
	 * @see Filter#destroy() --서버 실행 중 필터 내용 변경 시 수행 후 init() 다시 수행 
	 */
	public void destroy() {
		System.out.println("로그인 필터 파괴");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain) 매개변수로 전달 / ServletRequest  다형성 적용 HttpServletRequest의 부모
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		// 필터는 클라이언트의 요청이 되자마자 
		// 또는 응답이 되기 직전에 필터링 코드를 추가 할 수 있는 기능
		
		//다운캐스팅 진행
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		//로그인 여부 확인
		//-> session에 loginMember가 있는 지 확인
		HttpSession session = req.getSession();
		
		//키 값이 loginMember인 것을 들고 오겠다
		if(session.getAttribute("loginMember") == null) { //로그인 X
			//로그인을 안했으면 주소로 다른 페이지에 접근하는 걸 막기 위한것
			resp.sendRedirect("/"); //메인페이지로 리다이렉트
			
		}else { //로그인 O
			//연결된 다음 필터로 이동(없으면 Servlet /JSP(-controller)로 이동)
			chain.doFilter(request, response); 
		}
		
		
	}


    /**
     * 없으면 자동으로 생성되서 지워도 됨 
     */
	/*
	 * public LoginFilter() {
	 * 
	 * }
	 */

}
