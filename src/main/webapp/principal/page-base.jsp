<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>

<body>
	<div class="container-xxl position-relative bg-white d-flex p-0">
		<div id="spinner"
			class="show bg-white position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
			<div class="spinner-border text-primary"
				style="width: 3rem; height: 3rem;" role="status">
				<span class="sr-only">Loading...</span>
			</div>
		</div>

		<jsp:include page="sidebar.jsp"></jsp:include>

		<div class="content">
			<jsp:include page="navbar.jsp"></jsp:include>
			
				<h4>Conteúdos do sistema</h4>

		</div>

	</div>

	<jsp:include page="javascript.jsp"></jsp:include>

</body>

</html>