package edu.kh.project.member.model.service;

import edu.kh.project.member.model.vo.Member;

/*
 * Service Interface 사용 이유
 * 		
 * 		1. 프로젝트에 규칙성을 부여하기 위해서 
 * 		
 * 		2. 클래스 간의 결합도를 약화시키기 위함
 * 		   -> 유지 보수성 향상 (객체지향적 설계)
 * 		
 * 		3. AOP를 사용하기 위함
 * 		   --> spring-proxy를 이용하여 AOP코드가 동적하는데
 * 			   이 spring-proxy는 Service 인터페이스를 상속받아 동작
 */

//AOP(Aspect Oriented Programming)란 말 그대로 관점 지향 프로그래밍입니다.
//대부분 소프트웨어 개발 프로세스에서 사용하는 방법은 OOP(Object Oriented Programming) 입니다. 
//OOP는 객체지향 원칙에 따라 관심사가 같은 데이터를 한곳에 모아 분리하고 낮은 결합도를 갖게하여 
//독립적이고 유연한 모듈로 캡슐화를 하는 것을 일컫습니다. 하지만 이러한 과정 중 중복된 코드들이 많아지고 
//가독성, 확장성, 유지보수성을 떨어 뜨립니다. 이러한 문제를 보완하기 위해 나온 것이 AOP입니다.
//AOP에서는 핵심기능과 공통기능을 분리시켜 핵심 로직에 영향을 끼치지 않게 공통기능을 끼워 넣는 개발 형태이며 
//이렇게 개발함에 따라 무분별하게 중복되는 코드를 한 곳에 모아 중복 되는 코드를 제거 할 수 있어지고 
//공통기능을 한 곳에 보관함으로써 공통 기능 하나의 수정으로 모든 핵심기능들의 공통기능을 수정 할 수 있어 
//효율적인 유지보수가 가능하며 재활용성이 극대

public interface MemberService {
	
	/**
	 * @param inputMember
	 * @return
	 */
	public abstract Member login(Member inputMember);
	
	public abstract int signUp(Member inputMember);
}
