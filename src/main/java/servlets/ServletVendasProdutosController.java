package servlets;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOVendasProdutosRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelVendasProdutos;


@WebServlet("/ServletVendasProdutosController")
public class ServletVendasProdutosController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
	
	private DAOVendasProdutosRepository daoVendasProdutosRepository = new DAOVendasProdutosRepository();
       
   
    public ServletVendasProdutosController() {
      
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			String acao = request.getParameter("acao");
			
				if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
					
					String pk_id_venda_produtosUser = request.getParameter("pk_id_venda_produtos");
					
					daoVendasProdutosRepository.deletarVendasProdutos(pk_id_venda_produtosUser);
					
					List<ModelVendasProdutos> modelVendaProdutos = daoVendasProdutosRepository.consultarVendasProdutosList(super.getUserLogado(request));
					request.setAttribute("modelVendasProdutos", modelVendaProdutos);
					
					request.setAttribute("msg", "Excluido com sucesso!");
				    request.setAttribute("totalPagina", daoVendasProdutosRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/vendas-produtos.jsp").forward(request, response);
					
				}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarAjax")) {
					
					String pk_id_venda_produtosUser = request.getParameter("pk_id_venda_produtos");
					
					daoVendasProdutosRepository.deletarVendasProdutos(pk_id_venda_produtosUser);
					
					response.getWriter().write("Excluído com sucesso!");
					
				}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
					
					String nomeBusca = request.getParameter("nomeBusca");
					
					List<ModelVendasProdutos> dadosJsonUser = daoVendasProdutosRepository.consultarVendasProdutosList(nomeBusca, super.getUserLogado(request));
					
					List<ModelVendasProdutos> modelVendaProdutos = daoVendasProdutosRepository.consultarVendasProdutosList(super.getUserLogado(request));
					request.setAttribute("totalPagina", daoVendasProdutosRepository.totalPagina(this.getUserLogado(request)));
					request.setAttribute("modelVendasProdutos", modelVendaProdutos);
					
					ObjectMapper mapper = new ObjectMapper();
					String json = mapper.writeValueAsString(dadosJsonUser);
					
					response.addHeader("totalPagina", ""+ daoVendasProdutosRepository.consultarVendasProdutosListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
					response.getWriter().write(json);
					
				}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {
					
					String nomeBusca = request.getParameter("nomeBusca");
					String pagina = request.getParameter("pagina");
					 
					List<ModelVendasProdutos> dadosJsonUser =  daoVendasProdutosRepository.consultarVendasProdutosListOffSet(nomeBusca, super.getUserLogado(request), Integer.parseInt(pagina));
					 
					ObjectMapper mapper = new ObjectMapper();
					 
					String json = mapper.writeValueAsString(dadosJsonUser);
					 
					response.addHeader("totalPagina", ""+ daoVendasProdutosRepository.consultarVendasProdutosListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
					response.getWriter().write(json);
					
					
				}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
					
					String pk_id_venda_produtos = request.getParameter("pk_id_venda_produtos");
					 
				     ModelVendasProdutos modelVendasProdutos = daoVendasProdutosRepository.consultarVendasProdutosID(pk_id_venda_produtos, super.getUserLogado(request));
				 
				     List<ModelVendasProdutos> modelVendaProdutos = daoVendasProdutosRepository.consultarVendasProdutosList(super.getUserLogado(request));
				     request.setAttribute("modelVendaProdutos", modelVendaProdutos);
				     
				    request.setAttribute("msg", "Usuário em edição");
					request.setAttribute("modelVendasProdutos", modelVendasProdutos);
					request.setAttribute("totalPagina", daoVendasProdutosRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/vendas-produtos.jsp").forward(request, response);
					
					
				}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
					
					List<ModelVendasProdutos> modelVendaProdutos = daoVendasProdutosRepository.consultarVendasProdutosList(super.getUserLogado(request));
					
					request.setAttribute("modelVendaProdutos", modelVendaProdutos);
					request.setAttribute("totalPagina", daoVendasProdutosRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/vendas-produtos.jsp").forward(request, response);
					
				}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
					
					Integer offset = Integer.parseInt(request.getParameter("pagina"));
					
					List<ModelVendasProdutos> modelVendaProdutos = daoVendasProdutosRepository.consultaVendasProdutosListPaginada(this.getUserLogado(request), offset);
					
					request.setAttribute("modelVendaProdutos", modelVendaProdutos);
					request.setAttribute("totalPagina", daoVendasProdutosRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
					
				}else {
					
					
					List<ModelVendasProdutos> modelVendaProdutos = daoVendasProdutosRepository.consultarVendasProdutosList(super.getUserLogado(request));
					request.setAttribute("modelVendaProdutos", modelVendaProdutos);
					request.setAttribute("totalPagina", daoVendasProdutosRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				RequestDispatcher dispatcher = request.getRequestDispatcher("erro.jsp");
				request.setAttribute("msg", e.getMessage());
				dispatcher.forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {	
			
			String msg = "Operação realizada com sucesso!";
			
			String pk_id_vendas_produtos = request.getParameter("pk_id_vendas_produtos");
			String fk_produto = request.getParameter("fk_produto");
			String fk_vendas = request.getParameter("fk_vendas");
			String ven_valor = request.getParameter("ven_valor");
			String ven_pro_qtd = request.getParameter("ven_pro_qtd");
			String ven_pro_vendedor = request.getParameter("ven_pro_vendedor");
			
			ModelVendasProdutos modelVendasProdutos = new ModelVendasProdutos();
			
			modelVendasProdutos.setPk_id_venda_produtos(pk_id_vendas_produtos != null && !pk_id_vendas_produtos.isEmpty() ? Long.parseLong(pk_id_vendas_produtos) : null);
			modelVendasProdutos.setFk_vendas(fk_vendas != null && !fk_vendas.isEmpty() ? Long.parseLong(fk_vendas) : null);
			modelVendasProdutos.setFk_produto(fk_produto != null && !fk_produto.isEmpty() ? Long.parseLong(fk_produto) : null);
			modelVendasProdutos.setVen_valor(Double.parseDouble(ven_valor));
			modelVendasProdutos.setVen_pro_qtd(ven_pro_qtd);
			modelVendasProdutos.setVen_pro_vendedor(ven_pro_vendedor);

				
				if(modelVendasProdutos.isNovo()) {
					msg = "Gravado com sucesso!";
				}else {
					msg = "Atualizado com sucesso!";
				}
				
				modelVendasProdutos = daoVendasProdutosRepository.gravarVendasProdutos(modelVendasProdutos, super.getUserLogado(request));
			
			
			List<ModelVendasProdutos> modelVendaProdutos = daoVendasProdutosRepository.consultarVendasProdutosList(super.getUserLogado(request));
			request.setAttribute("modelVendaProdutos", modelVendaProdutos);
			
			request.setAttribute("msg", msg);
			request.setAttribute("modelVendasProdutos", modelVendasProdutos);
			 request.setAttribute("totalPagina", daoVendasProdutosRepository.totalPagina(this.getUserLogado(request)));
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			dispatcher.forward(request, response);
		}
	}

}
