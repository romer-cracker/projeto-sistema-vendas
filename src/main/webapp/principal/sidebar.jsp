<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core"%>
    
    <c:set scope="session" var="usu_perfil" value='<%= request.getSession().getAttribute("usu_perfil").toString() %>'></c:set>
    
 <!-- Sidebar Start -->
        <div class="sidebar pe-4 pb-3">
            <nav class="navbar bg-light navbar-light">
                <a href="index.html" class="navbar-brand mx-4 mb-3">
                    <h3 class="text-primary"><i class="fa fa-hashtag me-2"></i>SOFTRPB</h3>
                </a>
                <div class="d-flex align-items-center ms-4 mb-4">
                    <div class="position-relative">
                        <img class="rounded-circle" src="<%=request.getContextPath() %>/assets/img/user.jpg" alt="" style="width: 40px; height: 40px;">
                        <div class="bg-success rounded-circle border border-2 border-white position-absolute end-0 bottom-0 p-1"></div>
                    </div>
                    <div class="ms-3">
                        <h6 class="mb-0"><%= request.getSession().getAttribute("usuario") %></h6>
                    </div>
                </div>
                <div class="navbar-nav w-100">
                  <c:if test="${usu_perfil == 'ADMIN'}">
                  
                    <a href="<%=request.getContextPath() %>/ServletUsuarioController?acao=listarUser" class="nav-item nav-link active"><i class="fa fa-users" aria-hidden="true"></i>Usuários</a>
                  
                  </c:if>
                    
                    <a href="<%=request.getContextPath() %>/ServletProdutoController?acao=listarUser" class="nav-item nav-link active"><i class="fa fa-laptop me-2"></i>Produtos</a>
                        
                    <a href="<%=request.getContextPath() %>/ServletClienteController?acao=listarUser" class="nav-item nav-link active"><i class="fa fa-id-card" aria-hidden="true"></i>Clientes</a>
                    <a href="<%=request.getContextPath() %>/ServletVendaController?acao=listarUser" class="nav-item nav-link active"><i class="fa fa-keyboard me-2"></i>Vendas</a>
                    <a href="<%=request.getContextPath() %>/ServletVendasProdutosController?acao=listarUser" class="nav-item nav-link"><i class="fa fa-table me-2"></i>Vendas Produtos</a>
                    <a href="chart.html" class="nav-item nav-link"><i class="fa fa-chart-bar me-2"></i>Charts</a>
                    <div class="nav-item dropdown">
                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"><i class="far fa-file-alt me-2"></i>Pages</a>
                        <div class="dropdown-menu bg-transparent border-0">
                            <a href="signin.html" class="dropdown-item">Sign In</a>
                            <a href="signup.html" class="dropdown-item">Sign Up</a>
                            <a href="404.html" class="dropdown-item">404 Error</a>
                            <a href="blank.html" class="dropdown-item">Blank Page</a>
                        </div>
                    </div>
                </div>
            </nav>
        </div>
        <!-- Sidebar End -->