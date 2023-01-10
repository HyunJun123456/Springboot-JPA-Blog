<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="layout/header.jsp"%>
<!-- 앞에 /를 붙이면 못찾음 -->

<div class="container">
	<c:forEach var="board" items="${boards.content }">
		<!-- boards가 index로 날라감 -->

		<div class="card m-2">
			<div class="card-body">
				<h4 class="card-title">${board.title }</h4>
				<!-- getter로 가져오게 됨 board.getTitle()이 호출 -->
				<a href="#" class="btn btn-primary">상세보기 </a>
			</div>
		</div>

	</c:forEach>										<!-- start/center/end -->
	<ul class="pagination justify-content-center"> <!-- 가운데로 정렬하는거(부트스트랩에서) -->
	<c:choose>
		<c:when test="${boards.first }">
		<li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1 }">Previous</a></li>
		</c:when>
		<c:otherwise>
		<li class="page-item"><a class="page-link" href="?page=${boards.number-1 }">Previous</a></li>
		</c:otherwise>
	</c:choose>
		<c:choose>
		<c:when test="${boards.last }">
		<li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1 }">Next</a></li> <!-- '?'꼭 써야함! -->
		</c:when>
		<c:otherwise>
		<li class="page-item"><a class="page-link" href="?page=${boards.number+1 }">Next</a></li> <!-- '?'꼭 써야함! -->
		</c:otherwise>
	</c:choose>
		
		
		
	</ul>

</div>



<%@ include file="layout/footer.jsp"%>

