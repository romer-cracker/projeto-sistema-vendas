package servlets;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOVendaRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;
import model.ModelVenda;


@WebServlet("/ServletVendaController")
public class ServletVendaController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
	
	private DAOVendaRepository daoVendaRepository = new DAOVendaRepository();
       
   
    public ServletVendaController() {
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			String acao = request.getParameter("acao");
			
				if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
					
					String pk_id_vendasUser = request.getParameter("pk_id_vendas");
					
					daoVendaRepository.deletarVenda(pk_id_vendasUser);
					
					List<ModelVenda> modelVendas = daoVendaRepository.consultarVendaList(super.getUserLogado(request));
					request.setAttribute("modelVendas", modelVendas);
					
					request.setAttribute("msg", "Excluido com sucesso!");
				    request.setAttribute("totalPagina", daoVendaRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/vendas.jsp").forward(request, response);
					
				}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarAjax")) {
					
					String pk_id_vendasUser = request.getParameter("pk_id_vendas");
					
					daoVendaRepository.deletarVenda(pk_id_vendasUser);
					
					response.getWriter().write("Excluído com sucesso!");
					
				}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
					
					String nomeBusca = request.getParameter("nomeBusca");
					
					List<ModelVenda> dadosJsonUser = daoVendaRepository.consultarVendaList(nomeBusca, super.getUserLogado(request));
					
					List<ModelVenda> modelVendas = daoVendaRepository.consultarVendaList(super.getUserLogado(request));
					 request.setAttribute("totalPagina", daoVendaRepository.totalPagina(this.getUserLogado(request)));
					request.setAttribute("modelVendas", modelVendas);
					
					ObjectMapper mapper = new ObjectMapper();
					String json = mapper.writeValueAsString(dadosJsonUser);
					
					response.addHeader("totalPagina", ""+ daoVendaRepository.consultarVendaListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
					response.getWriter().write(json);
					
				}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {
					
					String nomeBusca = request.getParameter("nomeBusca");
					String pagina = request.getParameter("pagina");
					 
					List<ModelVenda> dadosJsonUser =  daoVendaRepository.consultarVendaListOffSet(nomeBusca, super.getUserLogado(request), Integer.parseInt(pagina));
					 
					ObjectMapper mapper = new ObjectMapper();
					 
					String json = mapper.writeValueAsString(dadosJsonUser);
					 
					response.addHeader("totalPagina", ""+ daoVendaRepository.consultarVendaListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
					response.getWriter().write(json);
					
					
				}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
					
					String pk_id_vendas = request.getParameter("pk_id_vendas");
					 
				     ModelVenda modelVenda = daoVendaRepository.consultarVendaID(pk_id_vendas, super.getUserLogado(request));
				 
				     List<ModelVenda> modelVendas = daoVendaRepository.consultarVendaList(super.getUserLogado(request));
				     request.setAttribute("modelVendas", modelVendas);
				     
				    request.setAttribute("msg", "Usuário em edição");
					request.setAttribute("modelVenda", modelVenda);
					request.setAttribute("totalPagina", daoVendaRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/vendas.jsp").forward(request, response);
					
					
				}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
					
					List<ModelVenda> modelVendas = daoVendaRepository.consultarVendaList(super.getUserLogado(request));
					
					request.setAttribute("modelVendas", modelVendas);
					request.setAttribute("totalPagina", daoVendaRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/vendas.jsp").forward(request, response);
					
				}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
					
					Integer offset = Integer.parseInt(request.getParameter("pagina"));
					
					List<ModelVenda> modelVendas = daoVendaRepository.consultaVendaListPaginada(this.getUserLogado(request), offset);
					
					request.setAttribute("modelVendas", modelVendas);
					request.setAttribute("totalPagina", daoVendaRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/vendas.jsp").forward(request, response);
					
				}else {
					
					
					List<ModelVenda> modelVendas = daoVendaRepository.consultarVendaList(super.getUserLogado(request));
					request.setAttribute("modelVendas", modelVendas);
					request.setAttribute("totalPagina", daoVendaRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/vendas.jsp").forward(request, response);
					
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
			
			String pk_id_vendas = request.getParameter("pk_id_vendas");
			String fk_cliente = request.getParameter("fk_cliente");
			String ven_vendedor = request.getParameter("ven_vendedor");
			String ven_data_venda = request.getParameter("ven_data_venda");
			String ven_valor_liquido = request.getParameter("ven_valor_liquido");
			String ven_valor_bruto = request.getParameter("ven_valor_bruto");
			String ven_desconto = request.getParameter("ven_desconto");
			
			
			ModelVenda modelVenda = new ModelVenda();
			
			modelVenda.setPk_id_vendas(pk_id_vendas != null && !pk_id_vendas.isEmpty() ? Long.parseLong(pk_id_vendas) : null);
			modelVenda.setFk_cliente(fk_cliente != null && !fk_cliente.isEmpty() ? Long.parseLong(fk_cliente) : null);
			modelVenda.setVen_vendedor(ven_vendedor);
			modelVenda.setVen_data_venda(ven_data_venda);
			modelVenda.setVen_valor_liquido(Double.parseDouble(ven_valor_liquido));
			modelVenda.setVen_valor_bruto(Double.parseDouble(ven_valor_bruto));
			modelVenda.setVen_desconto(Double.parseDouble(ven_desconto));
			
				
			if(modelVenda.isNovo()) {
					msg = "Gravado com sucesso!";
				}else {
					msg = "Atualizado com sucesso!";
				}
				
				modelVenda = daoVendaRepository.gravarVenda(modelVenda, super.getUserLogado(request));
			
			
			List<ModelVenda> modelVendas = daoVendaRepository.consultarVendaList(super.getUserLogado(request));
			request.setAttribute("modelVendas", modelVendas);
			
			request.setAttribute("msg", msg);
			request.setAttribute("modelVenda", modelVenda);
			 request.setAttribute("totalPagina", daoVendaRepository.totalPagina(this.getUserLogado(request)));
			request.getRequestDispatcher("principal/vendas.jsp").forward(request, response);
			
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			dispatcher.forward(request, response);
		}
	}

}
