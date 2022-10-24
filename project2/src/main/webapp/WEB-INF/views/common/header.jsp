<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

            <header>
                <!--로고 세션 ------------------------------------------------------------------------- -->
                <section>
                    <!-- 클릭시 메인 페이지로 이동 -->
                    <a href="/">
                        <img src="\resources\images\logo.jpg" id="home-logo">
                    </a>
                </section>

                <!-- 검색창 세션 ----------------------------------------------------------------------------------------- -->
                <!-- 검색창 영역 -->
                <section>
                    <!-- section 안에서 작은 영역을 만드는 거---
                        검색창이라서 제출을 해야함 form태그-->
                    <article class="search-area">
                        <!-- 내부 iput 태그의 값을 서버 또는 페이지로 전달(제출) -->
                        <!-- action 어디다가 제출할지 -->
                        <form action="#">
                            <!-- 테두리 만들어주는 거 fieldset -->
                            <fieldset>
                                <!-- type="search" -->
                                <input type="text" id="query" name="query" 
                                placeholder="검색어를 입력해주세요.">
                                    
                                <!-- 검색 버튼 -->
                                <!-- 아이콘 특수문자로 취급됨 -->
                                <button type="submit" id="search-btn" class="fa-solid fa-magnifying-glass"></button>
                            </fieldset>
                        </form>
                    </article>
                </section>
                
                
                <section></section>
                <!-- 헤더 오른쪽 상단 메뉴 -->
                
                <div id="header-top-menu">
                    <c:choose>
                        <c:when test="${empty sessionScope.loginMember}">
                            <a href="/">메인 페이지</a>
                	        |
                            <a href="/member/login">로그인</a> <%-- a 태그 자체가 get --%>
                        </c:when>
                        <c:otherwise>
                            <label for="header-menu-toggle">
                                ${loginMember.memberNickname}
                                <i class="fa-solid fa-caret-down"></i>
                            </label>
                            <input type="checkbox" id="header-menu-toggle">

                            <div id="header-menu">
                                <a href="/member/myPage/info">내정보</a>
                                <a href="/member/logout">로그아웃</a>
                            </div>
                            <%--<a href="#">로그아웃</a>  --%>
                        </c:otherwise>
                    </c:choose>

                	    
                </div>
                
            </header>

            <nav>
                <ul>
                    <!-- a inline 형식이라서 수평분할 
                    딱 글자만큼만 해당됨 -->
                    <%-- <li><a href="#">공지사항</a></li>
                    <li><a href="#">자유 게시판</a></li>
                    <li><a href="#">질문 게시판</a></li>
                    <li><a href="#">FAQ</a></li>
                    <li><a href="#">1:1문의</a></li> --%>
                    <c:forEach var = "boardType" items="${boardTypeMap}">
                        <%-- 
                            EL을 이용해서 Map 데이터를 다루는 방법
                            Key ==> ${변수명.key}
                            value ==> ${변수명.value}
                         --%>

                         <li><a href="/board/${boardType.key}/list">${boardType.value}</a></li>
                    </c:forEach>
                </ul>
            </nav>