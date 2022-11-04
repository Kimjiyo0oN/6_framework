//비밀번호 변경 유효성 검사

//비밀번호 변경 form 요소
const changePwForm = document.getElementById("changePwForm");


//myPage- 관련된 거에서 다 js가 실행 되는데 같은 아이디가 없는 페이지에서
//에러가 날수도 있기 때문에 null이 아닐때라는 조건문을 추가해준다

if(changePwForm != null){ //changePwForm 요소가 존재할 때

    changePwForm.addEventListener("submit",function(event){

        // ** 이벤트 핸들러 매개변수 event || e **
        // -> 현재 발생한 이벤트 정보를 가지고 있는 event 객체가 전달됨

        console.log(event);

        const currentPw = document.getElementById("currentPw");
        const newPw = document.getElementById("newPw");
        const newPwConfirm = document.getElementById("newPwConfirm");

        //현재 비밀번호가 작성되지 않았을 떄 
        if(currentPw.value.trim().length == 0 ){
            alertAndFocus(currentPw, " 현재 비밀번호 작성해라 ");
            // alert(" 현재 비밀번호 작성해라 ");
            // currentPw.focus();
            // currentPw.value ="";

            // function addchangePwFormsubmit(){
            //     return false;
            // }   --> 인라인 이벤트 모델 onsubmit = "return 함수명()"; 에서만 가능
            
            event.preventDefault();
            // -> 이벤트를 수행하지 못하게하는 함수 
            // --> 기본 이벤트 삭제 
            return;
        }

        if(newPw.value.trim().length == 0){
            alertAndFocus(newPw, " 새 비밀번호 작성해라 ");
            // alert(" 새 비밀번호 작성해라 ");
            // newPw.focus();
            // newPw.value ="";

            event.preventDefault();
            return;
        }

        if(newPwConfirm.value.trim().length == 0){
            alertAndFocus(newPwConfirm, " 새 비밀번호 확인란 작성해라 ");
            // alert(" 새 비밀번호 확인란 작성해라 ");
            // newPwConfirm.focus();
            // newPwConfirm.value ="";

            event.preventDefault();
            return;
        }

        //특수문자나 포함해야하는 거 검사하는 비밀번호 정규식 검사 필요!


        // 새 비밀번호, 새 비밀번호 확인이 같은 지 검사 추가적으로 해야함 
        if(newPw.value != newPwConfirm.value){
            alert("새 비밀번호가 일치하지 않습니다.");
            newPwConfirm.focus();
            event.preventDefault(); //기본 이벤트 제거
            return;
        }


    })
    
}

//경고창 출력 + 포커스 이동 + 값 삭제
function alertAndFocus(input, str){
    alert(str);
    input.focus();
    input.value ="";
}


//회원 탈퇴 유효성 검사
// -인라인 이벤트 모델 또는 표준 이벤트 모델로 작성

// 1) 비밀번호 미작성 -> "비밀번호를 입력해주세여" alert 출력 후
//                      포커스 이동(내용도 같이 삭제)

// 2) 동의 체크가 되지 않은 경우 
// -> "탈퇴 동의하시면 체크를 눌러주세요."  alert 출력 후 
//    포커스 이동

// 3) 1번, 2번이 모두 유효할 때
//    정말 탈퇴를 진행할 것인지 확인하는 confirm 출력
//    (확인 클릭 -> 탈퇴 / 취소 -> 탈퇴 취소)

const memberDeleteForm = document.getElementById("memberDeleteForm");


//if(memberDeleteForm != null){

    // memberDeleteForm.addEventListener("submit",function(event){
    //     const memberPw = document.getElementById("memberPw1");
    //     console.log(memberPw);
    //     if(memberPw.value.trim().length == 0){
    //         console.log(memberPw.value);
    //         alert("비밀번호를 입력해주세요!");
    //         memberPw.focus();
    //         memberPw.value="";

    //         event.preventDefault();
    //         return;
    //     }
        

    //     const agree = document.getElementById("agree");

    //     if(!agree.checked){
    //         alert("탈퇴 동의하시면 체크를 눌러주세요.");
    //         agree.focus();
    //         event.preventDefault();
    //         return;
    //     }
    //     if(agree.checked){
    //         const str ="진짜 탈퇴하시겠습니까?";
    //         if(!confirm(str)){
    //             agree.checked = false;
    //             event.preventDefault();
    //             return;
    //         }

    //     }



    // });
    
//}

if(memberDeleteForm != null){
    function deleteMember(){
        const memberPw = document.getElementById("memberPw1");
        console.log(memberPw);
        if(memberPw.value.trim().length == 0){
            console.log(memberPw.value);
            alert("비밀번호를 입력해주세요!");
            memberPw.focus();
            memberPw.value="";
            return false;

        }
        const agree = document.getElementById("agree");
        if(!agree.checked){
                alert("탈퇴 동의하시면 체크를 눌러주세요.");
                agree.focus();
                    
                return false;
            }
            if(agree.checked){
                const str ="진짜 탈퇴하시겠습니까?";
                if(!confirm(str)){
                    alert("탈퇴 취소");
                    agree.checked = false;
                    return false;
                }
            
        }
        return true;
    }
}
