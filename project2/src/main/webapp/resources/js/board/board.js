//목록으로 버튼 

const goToListBtn = document.getElementById("goToListBtn");

goToListBtn.addEventListener("click",()=>{
    
    // location : 주소,주소창과 관련 내장 객체 
    // location.href : 현재 주소(전체)
    // location.href = "주소" : 작성된 주소 요청 
    // location.pathname : 현재 요청 주소만의 반환(프로토콜, ip, 포트 제외)
    // location.search : 쿼리스트링만 반환
    const pathname = location.pathname; // / board/1/1500

    const queryString = location.search;

    const url = pathname.substring(0,pathname.lastIndexOf("/")) + queryString; // /board/1?cp =7
    
    location.href = url;
})

//좋아요 버튼 클릭 시 동작 
// (전역변수 memberNo, boardNo 사용(boardDetail.jsp))
const boardLike = document.getElementById("boardLike");

boardLike.addEventListener("click", e=>{

    // 로그인 상태가 아닌 경우 
    if(memberNo == ""){
        alert("로그인 후 이용해주세요")
        return;
    }
    const likeCount = e.target.nextElementSibling;
    // 로그인 상태이면서 좋아요 상태가 아닌 경우
    if(e.target.classList.contains('fa-regular')){ //빈하트인 경우
        $.ajax({
            url: "/boardLikeUp",
            data: {"boardNo" : boardNo,
                    "memberNo" : memberNo},
            type:"GET",
            success : (result)=>{
                if(result > 0){ //성공
                    e.target.classList.remove('fa-regular'); //빈하트 클래스 삭제
                    e.target.classList.add('fa-solid'); //채워진 하트 클래스 추가
                    likeCount.innerText = Number(likeCount.innerText) +1; //1 증가
                }else{ // 실패
                    console.log("증가실패");
                }
            }, //컨트롤러에서 반환되는 값이 여기 담긴다
            error:()=>{console.log("증가 에러")}
        });
        
    }

    // 로그인 상태이면서 좋아요 상태인 경우
    else{
        
        $.ajax({
            url: "/boardLikeDown",
            data: {"boardNo" : boardNo,
                    "memberNo" : memberNo},
            type:"GET",
            success : (result)=>{
                if(result > 0){ //성공
                    e.target.classList.remove('fa-solid'); //빈하트 클래스 추가
                    e.target.classList.add('fa-regular'); //채워진 하트 클래스 삭제
                    likeCount.innerText = Number(likeCount.innerText) -1;
                }else{ // 실패
                    console.log("감소실패");
                }
            }, //컨트롤러에서 반환되는 값이 여기 담긴다
            error:()=>{console.log("감소 에러")}
        });

    }
});

//게시글 삭제
const deleteBtn = document.getElementById("deleteBtn");

deleteBtn.addEventListener("click",()=>{
    if(confirm("정말 삭제 하시겠습니까??")){

        //와 그러면 a태그를 click하면 클릭 이벤트 발생시켜서 이걸로 하면 되는 거였넹??ㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎ

        // /board/{boardCode}/{boardNo}/delete GET방식
        location.href = location.pathname + "/delete";//(a태그로 href 보내는 거랑 똑같따!!!!!ㅎㅎㅎ)
        //              /board/1/1980/delete

    }

});


//글 수정 버튼 
(()=>{
    const updateBtn = document.getElementById("updateBtn");

    if(updateBtn != null){ //버튼이 존쟈할 때만
        updateBtn.addEventListener("click",()=>{
            //location.href = "/update/"+boardCode +"/" +boardNo;
            location.href = location.pathname+"/update"+location.search
        })
    }
})();