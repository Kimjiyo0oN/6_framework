<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>

<c:set var="boardName" value="${boardTypeList[boardCode-1].BOARD_NAME}" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${boardName} 수정</title>

    <link rel="stylesheet" href="/resources/css/main-style.css">

    <link rel="stylesheet" href="/resources/css/board/boardUpdate-style.css">


    <script src="https://kit.fontawesome.com/a2e8ca0ae3.js" crossorigin="anonymous"></script>
</head>
<body>
    <main>
        <jsp:include page="/WEB-INF/views/common/header.jsp"/>

        <%-- 상대경로 --%>
        <form action="update" method="POST" enctype="multipart/form-data" class="board-update" id="boardUpdateForm" onsubmit="return updateValidate()">  


            <!-- 제목 -->
            <h1 class="board-title">
                <input type="text" name="boardTitle" placeholder="제목" value="${board.boardTitle}">
            </h1>

             <%-- imageList에 존재하는 이미지 순서에 따라 변수 선언 --%>
             <c:forEach items="${board.imageList}" var="img">
                <c:choose>
                    <c:when test="${img.imageOrder ==0}">
                        <c:set var="thumbnail" value="${img.imagePath}${img.imageReName}"></c:set>
                    </c:when>
                    <c:when test="${img.imageOrder ==1}">
                        <c:set var="img1" value="${img.imagePath}${img.imageReName}"></c:set>
                    </c:when>
                    <c:when test="${img.imageOrder ==2}">
                        <c:set var="img2" value="${img.imagePath}${img.imageReName}"></c:set>
                    </c:when>
                    <c:when test="${img.imageOrder ==3}">
                        <c:set var="img3" value="${img.imagePath}${img.imageReName}"></c:set>
                    </c:when>
                    <c:when test="${img.imageOrder ==4}">
                        <c:set var="img4" value="${img.imagePath}${img.imageReName}"></c:set>
                    </c:when>
                </c:choose>
             </c:forEach>   

            <!-- 썸네일 영역 -->
            <h5>썸네일</h5>
            <div class="img-box">
                <div class="boardImg thumbnail">
                    <label for="img0">
                        <img class="preview" src="${thumbnail}">
                        <%-- <c:choose>
                            <c:when test="${not empty imageMap.img0}">
                                <img class="preview" src="${imageMap.img0}">
                            </c:when>
                            <c:otherwise>
                                <img class="preview" src="">
                            </c:otherwise>
                        </c:choose>  --%>
                    </label>
                    <input type="file" name="images" class="inputImage" id="img0" accept="image/*">
                    <span class="delete-image">&times;</span>
                </div>
            </div>


            <!-- 업로드 이미지 영역 -->
            <h5>업로드 이미지</h5>
            <div class="img-box">

                <div class="boardImg">
                    <label for="img1">
                        <img class="preview" src="${img1}">
                        <%-- <c:choose>
                            <c:when test="${not empty imageMap.img1}">
                                <img class="preview" src="${imageMap.img1}">
                            </c:when>
                            <c:otherwise>
                                <img class="preview" src="">
                            </c:otherwise>
                        </c:choose>  --%>
                    </label>
                    <input type="file" name="images" class="inputImage" id="img1" accept="image/*">
                    <span class="delete-image">&times;</span>
                </div>

                <div class="boardImg">
                    <label for="img2">
                        <img class="preview" src="${img2}">
                        <%-- <c:choose>
                            <c:when test="${not empty imageMap.img2}">
                                <img class="preview" src="${imageMap.img2}">
                            </c:when>
                            <c:otherwise>
                                <img class="preview" src="">
                            </c:otherwise>
                        </c:choose>  --%>     
                    </label>
                    <input type="file" name="images" class="inputImage" id="img2" accept="image/*">
                    <span class="delete-image">&times;</span>
                </div>

                <div class="boardImg">
                    <label for="img3">
                        <img class="preview" src="${img3}">
                        <%-- <c:choose>
                            <c:when test="${not empty imageMap.img3}">
                                <img class="preview" src="${imageMap.img3}">
                            </c:when>
                            <c:otherwise>
                                <img class="preview" src="">
                            </c:otherwise>
                        </c:choose>  --%>
                    </label>
                    <input type="file" name="images" class="inputImage" id="img3" accept="image/*">
                    <span class="delete-image">&times;</span>
                </div>

                <div class="boardImg">
                    <label for="img4">
                        <img class="preview" src="${img4}">
                        <%-- <c:choose>
                            <c:when test="${not empty imageMap.img4}">
                                <img class="preview" src="${imageMap.img4}">
                            </c:when>
                            <c:otherwise>
                                <img class="preview" src="">
                            </c:otherwise>
                        </c:choose>  --%>
                    </label>
                    <input type="file" name="images" class="inputImage" id="img4" accept="image/*">
                    <span class="delete-image">&times;</span>
                </div>
            </div>

            <!-- 내용 -->
            <div class="board-content">
                <textarea name="boardContent">${board.boardContent}</textarea>
            </div>


             <!-- 버튼 영역 -->
            <div class="board-btn-area">
                <button type="submit" id="updatebtn">수정</button>
            </div>

            <%-- 삭제될 이미지 순서를 저장한 input태그 --%>
            <input type="hidden" name="deleteList" id="deleteList" value="">

            <%-- 수정 완료 후 리다이렉트 시 사용 예정 --%>
            <input type="hidden" name="cp" id="cp" value="${param.cp}">
        </form>

    </main>

    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
    <script>
        // 로그인한 회원 번호 얻어오기

        //(참고) JSP 해석 순서 : EL/JSTL > HTML > JS
        // *** JS에 EL/JSTL 사용 시 양쪽에 "" 또는 ''를 붙이는 것을 권장 ***
        // -> 왜? 값이 없어서 공백이 되더라도 ""(빈 문자열)로 인식하여
        //     에러 발생을 막음.
        const memberNo = "${loginMember.memberNo}"; //EL은 NULL일 때 빈칸

        const boardNo ="${boardNo}";

        const boardCode ="${boardCode}";
    </script>

    <script src="/resources/js/board/boardUpdate.js"></script>

</body>
</html>