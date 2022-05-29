<%@page import="model.ModelLogin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
	
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
			
			<div class="container-fluid pt-4 px-4">
				<div class="row">
				
					<form action="<%= request.getContextPath() %>/ServletProdutoController" method="post" id="formUser">
					<input type="hidden" name="acao" id="acao" value="">
					<div class="col-sm-12 col-xl-6" style="margin: 0 auto;">
					
						<div class="bg-light rounded h-100 p-4">
							<h6 class="mb-4">Cadastro de Produtos</h6>

							<div class="form-floating mb-3">
								<input type="text" class="form-control" id="pk_id_produto"
									name="pk_id_produto" placeholder="Id" readonly="readonly" value="${modelProduto.pk_id_produto}"> <label
									for="pk_id_produto">ID</label>
							</div>

							<div class="form-floating mb-3">
								<input type="text" class="form-control" id="pro_nome"
									name="pro_nome" placeholder="Nome" required="required" value="${modelProduto.pro_nome}"> <label
									for="pro_nome">Nome</label>
							</div>
							<div class="form-floating mb-3">
								<input type="text" class="form-control" id="pro_valor"
									name="pro_valor" placeholder="Valor" required="required" value="${modelProduto.pro_valor}"> <label
									for="usu_login">Valor</label>
							</div>

							<div class="form-floating mb-3">
								<input type="text" class="form-control" id="pro_estoque" name="pro_estoque"
									placeholder="Estoque" required="required" value="${ModelProduto.pro_estoque}"> <label for="pro_estoque">Estoque</label>
							</div>
							
							
							<button type="button" class="btn btn-primary waves-effect waves-light" onclick="limparForm();">Novo</button>
							<button class="btn btn-success waves-effect waves-light">Gravar</button>
							<button type="button" class="btn btn-danger waves-effect waves-light" onclick="criarDelete();">Excluir</button>
							<button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#exampleModal">Pesquisar</button>
							</div>
						<span id="msg">${msg}</span>
					</div>
					</form>
				</div>
					
				
				 <div class="col-sm-12 col-xl-6" style="margin: 0 auto;">
				 	<div style="height: 200px; overflow: scroll;">
                            <table class="table table-bordered" id="tabelaResultadosView">
                                <thead>
                                    <tr>
                                        <th scope="col">ID</th>
                                        <th scope="col">Nome</th>
                                        <th scope="col">Ver</th>
                                    </tr>
                                </thead>
                                <tbody>
                                
									<c:forEach items='${modelProdutos}' var='mp'>
									<tr>
										<td><c:out value="${mp.pk_id_produto}"></c:out></td>
										<td><c:out value="${mp.pro_nome}"></c:out></td>
										<td><a class="btn btn btn-success"
											href="<%= request.getContextPath() %>/ServletProdutoController?acao=buscarEditar&pk_id_produto=${mp.pk_id_produto}">Ver</a></td>
									</tr>


								</c:forEach>
								</tbody>
                            </table>
                        </div>
                        <nav aria-label="Page navigation example">
							<ul class="pagination">

								<%
								int totalPagina = (int) request.getAttribute("totalPagina");

								for (int p = 0; p < totalPagina; p++) {
									String url = request.getContextPath() + "/ServletProdutoController?acao=paginar&pagina=" + (p * 5);
									out.print("<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "\">" + (p + 1) + "</a></li>");
								}
								%>


							</ul>
						</nav>
						
					</div>	 
                    </div>
				
			</div>

		</div>
		

	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Pesquisa de Produtos</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="input-group mb-3">
						<input type="text" class="form-control" placeholder="" aria-label="Example text with button addon" aria-describedby="button-addon1">
							<div class="input-group-append">
								<button class="btn btn-success" type="button"  onclick="buscarUsuario();">Buscar</button>
							</div>
							
					</div>
					<div style="height: 200px; overflow: scroll;">
					<table class="table table-bordered" id="tabelaResultados">
						<thead>
							<tr>
								<th scope="col">ID</th>
								<th scope="col">Nome</th>
								<th scope="col">Ver</th>
							</tr>
						</thead>
						<tbody>
								<c:forEach items='${modelProdutos}' var='mp'>
									<tr>
										<td><c:out value="${mp.pk_id_produto}"></c:out></td>
										<td><c:out value="${mp.pro_nome}"></c:out></td>
										<td><a class="btn btn btn-success"
											href="<%= request.getContextPath() %>/ServletProdutoController?acao=buscarEditar&pk_id_produto=${mp.pk_id_produto}">Ver</a></td>
									</tr>


								</c:forEach>
							</tbody>
					</table>
					</div>
					<nav aria-label="Page navigation example">
						<ul class="pagination" id="ulPaginacaoUserAjax">

						</ul>
					</nav>
					<span id="totalResultados"></span>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Fechar</button>
				</div>
			</div>
		</div>
	</div>


	<jsp:include page="javascript.jsp"></jsp:include>
	

	
	<script type="text/javascript">
	
		function verEditar(pk_id_produto){
		
			var urlAction = document.getElementById('formUser').action; // redireciona para a ServletLoginController;
		
			window.location.href = urlAction + '?acao=buscarEditar&id='+ pk_id_produto; // executa um get;
		
		}
		
		function limparForm(){
			
			var elementos = document.getElementById("formUser").elements;
			
			for(var p = 0; p < elementos.length; p++){
				elementos[p].value = '';
			}
		}
		
		
		
		function criarDelete(){
			
			if(confirm('Deseja realmente excluir esses dados?')){
				
				document.getElementById("formUser").method = 'get';
				document.getElementById("acao").value = 'deletar';
				document.getElementById("formUser").submit();
				
			}
		}
		
		function buscaUserPagAjax(url){
			   
		    
		    var urlAction = document.getElementById('formUser').action;
		    var nomeBusca = document.getElementById('nomeBusca').value;
		    
			 $.ajax({	     
			     method: "get",
			     url : urlAction,
			     data : url,
			     success: function (response, textStatus, xhr) {
				 
				 var json = JSON.parse(response);
				 
				 
				 $('#tabelaresultados > tbody > tr').remove();
				 $("#ulPaginacaoUserAjax > li").remove();
				 
				  for(var p = 0; p < json.length; p++){
				      $('#tabelaresultados > tbody').append('<tr> <td>'+json[p].pk_id_produto+'</td> <td> '+json[p].pro_nome+'</td> <td><button onclick="verEditar('+json[p].pk_id_produto+')" type="button" class="btn btn-info">Ver</button></td></tr>');
				  }
				  
				  document.getElementById('totalResultados').textContent = 'Resultados: ' + json.length;
				  
				    var totalPagina = xhr.getResponseHeader("totalPagina");
			
				  
				    
					  for (var p = 0; p < totalPagina; p++){
					      
				
					      
					      var url = 'nomeBusca=' + nomeBusca + '&acao=buscarUserAjaxPage&pagina='+ (p * 5);
					      
					   
					      $("#ulPaginacaoUserAjax").append('<li class="page-item"><a class="page-link" href="#" onclick="buscaUserPagAjax(\''+url+'\')">'+ (p + 1) +'</a></li>'); 
					      
					  }
				 
			     }
			     
			 }).fail(function(xhr, status, errorThrown){
			    alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
			 });
		    
		}
		
		
		
		function buscarUsuario() {
		    
			var nomeBusca = document.getElementById('nomeBusca').value;
			
			if (nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != ''){ 
			
			 var urlAction = document.getElementById('formUser').action;
			
			 $.ajax({
				 
				 method: "get",
				 url : urlAction,
				 data : "nomeBusca=" + nomeBusca + '&acao=buscarUserAjax',
				 success: function (response, textStatus, xhr) {
				 
				  var json = JSON.parse(response);
				  
				  $('#tabelaResultados > tbody > tr').remove();
					 
				 	 for(var p = 0; p < json.length; p++){
				      $('#tabelaResultados > tbody').append('<tr> <td>'+json[p].pk_id_produto+'</td> <td> '+json[p].pro_nome+'</td> <td><button onclick="verEditar('+json[p].pk_id_produto+')" type="button" class="btn btn-info">Ver</button></td></tr>');
				  }
				  
				  document.getElementById('totalResultados').textContent = 'Resultados: ' + json.length;
				  
				  var totalPagina = xhr.getResponseHeader("totalPagina");
				  
				  	for (var p = 0; p < totalPagina; p++){
				      
				      var url = 'nomeBusca=' + nomeBusca + '&acao=buscarUserAjaxPage&pagina='+ (p * 5);
				      
				   
				      $("#ulPaginacaoUserAjax").append('<li class="page-item"><a class="page-link" href="#" onclick="buscaUserPagAjax(\''+url+'\')">'+ (p + 1) +'</a></li>');
				      
				  	}
				 
				 }
				 
			 }).fail(function(xhr, status, errorThrown){
				alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
			 });
			
			
			}
			
		}

	
	
	</script>

</body>

</html>