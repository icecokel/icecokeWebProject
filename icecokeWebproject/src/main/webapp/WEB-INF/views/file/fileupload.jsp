<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
@import url(//fonts.googleapis.com/earlyaccess/jejugothic.css);

.filelistdiv {
	font-family: 'Jeju Gothic', sans-serif;
}
</style>
<section>
	<div>
		<c:if test="${user ==null }">
		
			<h3>파일 업로드  <br/>서비스는 로그인을 해야 사용 하실 수 있습니다.</h3>
			<input type="button" value ="로그인 하러가기."  id ="btnlogin"/>
		
		</c:if>
		<c:if test="${user !=null }">
		<form method="post" enctype="multipart/form-data" >
			<input type="file" multiple="multiple" id="files" name ="files"><br/>

			<div ><textarea id="filelist" readonly></textarea></div>
			<input type="submit" value="업로드"/>
		</form>
		</c:if>
	</div>
</section>

<script>
	var btnlogin = document.getElementById("btnlogin");
	
	btnlogin.addEventListener("click" ,function(e){
		location.href="/user/login";	
	});


</script>