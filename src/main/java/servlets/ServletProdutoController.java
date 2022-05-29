package servlets;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOProdutoRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelProduto;


@WebServlet("/ServletProdutoController")
public class ServletProdutoController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
       
	private DAOProdutoRepository daoProdutoRepository = new DAOProdutoRepository();
    
    public ServletProdutoController() {
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			String acao = request.getParameter("acao");
			
				if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
					
					String pk_id_produtoUser = request.getParameter("pk_id_produto");
					
					daoProdutoRepository.deletarProduto(pk_id_produtoUser);
					
					List<ModelProduto> modelProdutos = daoProdutoRepository.consultarProdutoList(super.getUserLogado(request));
					request.setAttribute("modelProdutos", modelProdutos);
					
					request.setAttribute("msg", "Excluido com sucesso!");
				    request.setAttribute("totalPagina", daoProdutoRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/produto.jsp").forward(request, response);
					
				}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarAjax")) {
					
					String pk_id_produtoUser = request.getParameter("pk_id_produto");
					
					daoProdutoRepository.deletarProduto(pk_id_produtoUser);
					
					response.getWriter().write("Excluído com sucesso!");
					
				}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
					
					String nomeBusca = request.getParameter("nomeBusca");
					
					List<ModelProduto> dadosJsonUser = daoProdutoRepository.consultarProdutoList(nomeBusca, super.getUserLogado(request));
					
					List<ModelProduto> modelProdutos = daoProdutoRepository.consultarProdutoList(super.getUserLogado(request));
					request.setAttribute("totalPagina", daoProdutoRepository.totalPagina(this.getUserLogado(request)));
					request.setAttribute("modelProdutos", modelProdutos);
					
					ObjectMapper mapper = new ObjectMapper();
					String json = mapper.writeValueAsString(dadosJsonUser);
					
					response.addHeader("totalPagina", ""+ daoProdutoRepository.consultarProdutoListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
					response.getWriter().write(json);
					
				}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {
					
					String nomeBusca = request.getParameter("nomeBusca");
					String pagina = request.getParameter("pagina");
					 
					List<ModelProduto> dadosJsonUser =  daoProdutoRepository.consultarProdutoListOffSet(nomeBusca, super.getUserLogado(request), Integer.parseInt(pagina));
					 
					ObjectMapper mapper = new ObjectMapper();
					 
					String json = mapper.writeValueAsString(dadosJsonUser);
					 
					response.addHeader("totalPagina", ""+ daoProdutoRepository.consultarProdutoListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
					response.getWriter().write(json);
					
					
				}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
					
					String pk_id_produto = request.getParameter("pk_id_produto");
					 
				     ModelProduto modelProduto = daoProdutoRepository.consultarProdutoID(pk_id_produto, super.getUserLogado(request));
				 
				     List<ModelProduto> modelProdutos = daoProdutoRepository.consultarProdutoList(super.getUserLogado(request));
				     request.setAttribute("modelProdutos", modelProdutos);
				     
				    request.setAttribute("msg", "Usuário em edição");
					request.setAttribute("modelProduto", modelProduto);
					request.setAttribute("totalPagina", daoProdutoRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/produto.jsp").forward(request, response);
					
					
				}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
					
					List<ModelProduto> modelProdutos = daoProdutoRepository.consultarProdutoList(super.getUserLogado(request));
					
					request.setAttribute("modelProdutos", modelProdutos);
					request.setAttribute("totalPagina", daoProdutoRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/produto.jsp").forward(request, response);
					
				}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
					
					Integer offset = Integer.parseInt(request.getParameter("pagina"));
					
					List<ModelProduto> modelProdutos = daoProdutoRepository.consultaProdutoListPaginada(this.getUserLogado(request), offset);
					
					request.setAttribute("modelProdutos", modelProdutos);
					request.setAttribute("totalPagina", daoProdutoRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/produto.jsp").forward(request, response);
					
				}else {
					
					
					List<ModelProduto> modelProdutos = daoProdutoRepository.consultarProdutoList(super.getUserLogado(request));
					request.setAttribute("modelProdutos", modelProdutos);
					request.setAttribute("totalPagina", daoProdutoRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/produto.jsp").forward(request, response);
					
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
			
			String pk_id_produto = request.getParameter("pk_id_produto");
			String pro_nome = request.getParameter("pro_nome");
			String pro_valor = request.getParameter("pro_valor");
			String pro_estoque = request.getParameter("pro_estoque");
			
			ModelProduto modelProduto = new ModelProduto();
			
			modelProduto.setPk_id_produto(pk_id_produto != null && !pk_id_produto.isEmpty() ? Long.parseLong(pk_id_produto) : null);
			modelProduto.setPro_nome(pro_nome);
			modelProduto.setPro_valor(Double.parseDouble(pro_valor));
			modelProduto.setPro_estoque(Integer.parseInt(pro_estoque));
			
			if(modelProduto.isNovo()) {
				msg = "Gravado com sucesso!";
			}else {
				msg = "Atualizado com sucesso!";
			}
			
			modelProduto = daoProdutoRepository.gravarProduto(modelProduto, super.getUserLogado(request));
			
			List<ModelProduto> modelProdutos = daoProdutoRepository.consultarProdutoList(super.getUserLogado(request));
			request.setAttribute("modelProdutos", modelProdutos);
			
			request.setAttribute("msg", msg);
			request.setAttribute("modelProduto", modelProduto);
			request.setAttribute("totalPagina", daoProdutoRepository.totalPagina(this.getUserLogado(request)));
			request.getRequestDispatcher("principal/produto.jsp").forward(request, response);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			dispatcher.forward(request, response);
		}
		
		
	}

}
