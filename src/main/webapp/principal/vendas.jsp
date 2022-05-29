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
				
					<form action="<%= request.getContextPath() %>/ServletVendaController" method="post" id="formUser">
					<input type="hidden" name="acao" id="acao" value="">
					<div class="col-sm-12 col-xl-6" style="margin: 0 auto;">
					
						<div class="bg-light rounded h-100 p-4">
							<h6 class="mb-4">PDV VENDAS</h6>

							<div class="form-floating mb-3">
								<input type="text" class="form-control" id="pk_id_vendas"
									name="pk_id_vendas" placeholder="Nº Pedido" readonly="readonly" value="${modelVenda.pk_id_vendas}"> <label
									for="pk_id_vendas">Nº Pedido</label>
							</div>
							
							<div class="form-floating mb-3">
								<input type="text" class="form-control" id="ven_vendedor"
									name="ven_vendedor" placeholder="Vendedor" autocomplete="off" value="${modelVenda.ven_vendedor}"> <label
									for="ven_vendedor">Vendedor</label>
							</div>

							<div class="form-floating mb-3">
								<input type="text" class="form-control" id="fk_cliente"
									name="fk_cliente" placeholder="Cliente" required="required" value="${modelVenda.fk_cliente}" autocomplete="off"> <label
									for="fk_cliente">Cliente</label>
							</div>
							
							<div class="form-floating mb-3">
								<input type="text" class="form-control" id="ven_data_venda"
									name="ven_data_venda" placeholder="Data da Venda" required="required" value="${modelVenda.ven_data_venda}" autocomplete="off"> <label
									for="ven_data_venda">Data da Venda</label>
							</div>
							
							
							<div class="form-floating mb-3">
								<input type="text" class="form-control" id="ven_valor_liquido"
									name="ven_valor_liquido" placeholder="Valor Liquido" required="required" value="${modelVenda.ven_valor_liquido}" autocomplete="off"> <label
									for="ven_valor_liquido">Valor Líquido</label>
							</div>

							<div class="form-floating mb-3">
								<input type="text" class="form-control" id="ven_valor_bruto" name="ven_valor_bruto"
									placeholder="Valor Bruto" required="required" value="${modelVenda.ven_valor_bruto}" autocomplete="off"> <label for="ven_valor_bruto">Valor Bruto</label>
							</div>
							
							<div class="form-floating mb-3">
								<input type="text" class="form-control" id="ven_desconto" name="ven_desconto"
									placeholder="Desconto" required="required" value="${modelVenda.ven_desconto}" autocomplete="off"> <label for="ven_desconto">Desconto</label>
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
                                        <th scope="col">Nº Venda</th>
                                        <th scope="col">Cliente</th>
                                        <th scope="col">Valor</th>
                                        <th scope="col">Ver</th>
                                    </tr>
                                </thead>
                                <tbody>
                                
									<c:forEach items='${modelVendas}' var='mv'>
									<tr>
										<td><c:out value="${mv.pk_id_vendas}"></c:out></td>
										<td><c:out value="${mv.fk_cliente}"></c:out></td>
										<td><c:out value="${mv.ven_valor_bruto}"></c:out></td>
										<td><a class="btn btn btn-success"
											href="<%= request.getContextPath() %>/ServletVendaController?acao=buscarEditar&pk_id_vendas=${mv.pk_id_vendas}">Ver</a></td>
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
									String url = request.getContextPath() + "/ServletVendaController?acao=paginar&pagina=" + (p * 5);
									out.print("<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "\">" + (p + 1) + "</a></li>");
								}
								%>


							</ul>
						</nav>
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
					<h5 class="modal-title" id="exampleModalLabel">Pesquisar Vendas</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="input-group mb-3">
						<input type="text" class="form-control" placeholder="" aria-label="Example text with button addon" aria-describedby="button-addon1">
							<div class="input-group-append">
								<button class="btn btn-success" type="button"  onclick="buscarVenda();">Buscar</button>
							</div>
							
					</div>
					<div style="height: 200px; overflow: scroll;">
					<table class="table table-bordered" id="tabelaResultados">
							<thead>
								<tr>
									<th scope="col">Nº Venda</th>
									<th scope="col">Cliente</th>
									<th scope="col">Valor</th>
									<th scope="col">Ver</th>
								</tr>
							</thead>
							<tbody>

								<c:forEach items='${modelVendas}' var='mv'>
									<tr>
										<td><c:out value="${mv.pk_id_vendas}"></c:out></td>
										<td><c:out value="${mv.fk_cliente}"></c:out></td>
										<td><c:out value="${mv.ven_valor_bruto}"></c:out></td>
										<td><a class="btn btn btn-success"
											href="<%= request.getContextPath() %>/ServletVendaController?acao=buscarEditar&pk_id_vendas=${mv.pk_id_vendas}">Ver</a></td>
									</tr>


								</c:forEach>
							</tbody>
						</table>
					</div>
					<nav aria-label="Page navigation example">
						<ul class="pagination" id="ulPaginacaoUserAjax">

								<%
									totalPagina = (int) request.getAttribute("totalPagina");
	
									for (int p = 0; p < totalPagina; p++) {
									String url = request.getContextPath() + "/ServletVendaController?acao=paginar&pagina=" + (p * 5);
									out.print("<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + "\">" + (p + 1) + "</a></li>");
								}
								%>
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
	
	$("#fk_cliente").keypress(function (event) {
		   return /\d/.test(String.fromCharCode(event.keyCode)); 
		});
	
	


	
	function verEditar(pk_id_vendas) {
	   
	    var urlAction = document.getElementById('formUser').action;
	    
	    
	    window.location.href = urlAction + '?acao=buscarEditar&pk_id_vendas='+pk_id_vendas;
	    
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
			      $('#tabelaresultados > tbody').append('<tr> <td>'+json[p].pk_id_vendas+'</td> <td> '+json[p].fk_cliente+'</td> <td><button onclick="verEditar('+json[p].pk_id_vendas+')" type="button" class="btn btn-info">Ver</button></td></tr>');
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


	function buscarVenda() {
	    
	    var nomeBusca = document.getElementById('nomeBusca').value;
	    
	    if (nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != ''){ /*Validando que tem que ter valor pra buscar no banco*/
		
		 var urlAction = document.getElementById('formUser').action;
		
		 $.ajax({
		     
		     method: "get",
		     url : urlAction,
		     data : "nomeBusca=" + nomeBusca + '&acao=buscarUserAjax',
		     success: function (response, textStatus, xhr) {
			 
			 var json = JSON.parse(response);
			 
			 
			 $('#tabelaresultados > tbody > tr').remove();
			 $("#ulPaginacaoUserAjax > li").remove();
			 
			  for(var p = 0; p < json.length; p++){
			      $('#tabelaresultados > tbody').append('<tr> <td>'+json[p].pk_id_vendas+'</td> <td> '+json[p].fk_cliente+'</td> <td><button onclick="verEditar('+json[p].pk_id_vendas+')" type="button" class="btn btn-info">Ver</button></td></tr>');
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


	function criarDeleteComAjax() {
	    
	    if (confirm('Deseja realmente excluir os dados?')){
		
		 var urlAction = document.getElementById('formUser').action;
		 var pk_id_vendasUser = document.getElementById('pk_id_vendas').value;
		 
		 $.ajax({
		     
		     method: "get",
		     url : urlAction,
		     data : "pk_id_vendas=" + pk_id_vendasUser + '&acao=deletarajax',
		     success: function (response) {
			 
			  limparForm();
			  document.getElementById('msg').textContent = response;
		     }
		     
		 }).fail(function(xhr, status, errorThrown){
		    alert('Erro ao deletar usuário por id: ' + xhr.responseText);
		 });
		 
		  
	    }
	    
	}



	function criarDelete() {
	    
	    if(confirm('Deseja realmente excluir os dados?')) {
		
		    document.getElementById("formUser").method = 'get';
		    document.getElementById("acao").value = 'deletar';
		    document.getElementById("formUser").submit();
		    
	    }
	    
	}


	function limparForm() {
	    
	    var elementos = document.getElementById("formUser").elements; /*Retorna os elementos html dentro do form*/
	    
	    for (p = 0; p < elementos.length; p ++){
		    elementos[p].value = '';
	    }
	}

	
	
	
	
	</script>

</body>

</html>