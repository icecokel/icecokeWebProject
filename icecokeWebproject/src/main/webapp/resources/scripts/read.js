
let btnList = document.getElementById("btnList");

btnList.addEventListener("click", function(e) {
	location.href = "/board/list";
});

let btndelete = document.getElementById("btndelete");

let boardnum = document.getElementById("boardnum");

let btncomment = document.getElementById("btncomment");
let comment = document.getElementById("comment");

btncomment.addEventListener("click", function(e){
	 if(comment.value.trim().length < 1){
		 return;
	 }
	 
	 $.ajax({
		 url : 'commentwrite',
		 dataType : 'json',
		 async : true,
		 type : 'POST',
		 data : {
			 "boardnum" : boardnum.value,
			 "commentcontent" : comment.value
		 },
		 success : function(data){
			 if(result == true){
				 alert("댓글 입력 성공");
			 }else{
				 alert("댓글 입력 실패");
			 }
		 
		 },
		 errer : function(errer){
			 alert("500번 에러.");
		 }
		 
	 });
});

btndelete.addEventListener("click", function(e) {
	let result = confirm("정말 삭제 하시겠습니까?");

	if (result == true) {
		alert("게시글이 삭제 되었습니다.");
		location.href = "/board/delete/" + boardnum.value;
	}

});