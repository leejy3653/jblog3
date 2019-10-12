<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
</head>
<script type="text/javascript">/* 자바스크립트 */
function ajaxremoveCategory(categoryId) {
	
    $.ajax({
        type : 'GET',
        url : "${pageContext.request.contextPath}/category/api/remove",
        data : {"id" : categoryId},
        success : function (data) {
            if (data.data) {
            	$('#tr-'+categoryId).remove();
            	alert('카테고리 삭제 완료');
            }else{
            	alert('삭제 실패');
            }
        }
    });
}
function ajaxcreateCategory() {
	$.ajax({
        type : 'POST',
        url : "${pageContext.request.contextPath}/category/api/create",
        data : {"name" : $("#catName").val(), "exp": $("#exp").val()},
        success : function (data) {
        		var categoryNo = data.data.no;
            	var topCategoryIndex = parseInt(document.getElementsByClassName("td-category-count")[0].innerHTML)+1;
            	var catName = $('#catName').val();
            	var exp = $('#exp').val();
            	var count = 0;
            	
            	var category_tr = 
            	'<tr blog_id="tr-'+categoryNo+'">'+
					'<td>'+'<p class="td-category-count">'+topCategoryIndex+'</p>'+'</td>'+
					'<td>'+catName+'</td>'+
					'<td>'+count+'</td>'+
					'<td>'+exp+'</td>'+
					'<td>'+
						'<img onclick="ajaxremoveCategory('+categoryNo+')" src="${pageContext.request.contextPath}/assets/images/delete.jpg">'+
					'</td>'+
				'</tr>';
            $('#category-column').after(category_tr);
            $('#catName').val('');
            $('#exp').val('');
            alert('카테고리 추가 완료');
            location.reload();
        }
    });
}
</script>

<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/blog-header.jsp" />
		<div id="wrapper">
			<div id="content" class="full-screen">
				<c:import url="/WEB-INF/views/includes/admin-menu.jsp"></c:import>
				<table class="admin-cat">
					<tr>
						<th>번호</th>
						<th>카테고리명</th>
						<th>포스트 수</th>
						<th>설명</th>
						<th>삭제</th>
					</tr>
					<c:forEach items="${list }" var="categoryvo" varStatus="status">
						<tr id="tr-${categoryvo.no}">
							<td>
								<p class="td-category-count">${list.size()-status.count+1}</p>
							</td>
							<td>${categoryvo.name }</td>
							<td>${categoryvo.count }</td>
							<td>${categoryvo.exp }</td>
							<td><img onclick="ajaxremoveCategory(${categoryvo.no})"
								src="${pageContext.request.contextPath}/assets/images/delete.jpg">
							</td>
						</tr>
					</c:forEach>
				</table>

				<h4 class="n-c">새로운 카테고리 추가</h4>
				<table id="admin-cat-add">
					<tr>
						<td class="t">카테고리명</td>
						<td><input type="text" id="catName" name="name"></td>
					</tr>
					<tr>
						<td class="t">설명</td>
						<td><input type="text" id="exp" name="exp"></td>
					</tr>
					<tr>
						<td class="s">&nbsp;</td>
						<td><input type="button" onclick="ajaxcreateCategory()"
							value="카테고리 추가" id="admin-cat-add"></td>
					</tr>
				</table>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp" />
	</div>
</body>
</html>