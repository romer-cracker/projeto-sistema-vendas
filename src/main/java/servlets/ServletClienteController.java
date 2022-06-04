package servlets;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOClienteRepository;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ModelCliente;


@WebServlet("/ServletClienteController")
public class ServletClienteController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
	
	private DAOClienteRepository daoClienteRepository = new DAOClienteRepository();
       
  
    public ServletClienteController() {
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			String acao = request.getParameter("acao");
			
				if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
					
					String pk_id_clienteUser = request.getParameter("pk_id_cliente");
					
					daoClienteRepository.deletarCliente(pk_id_clienteUser);
					
					List<ModelCliente> modelClientes = daoClienteRepository.consultarClienteList(super.getUserLogado(request));
					request.setAttribute("modelClientes", modelClientes);
					
					request.setAttribute("msg", "Excluido com sucesso!");
				    request.setAttribute("totalPagina", daoClienteRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/cliente.jsp").forward(request, response);
					
				}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarAjax")) {
					
					String pk_id_clienteUser = request.getParameter("pk_id_cliente");
					
					daoClienteRepository.deletarCliente(pk_id_clienteUser);
					
					response.getWriter().write("Excluído com sucesso!");
					
				}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
					
					String nomeBusca = request.getParameter("nomeBusca");
					
					List<ModelCliente> dadosJsonUser = daoClienteRepository.consultarClienteList(nomeBusca, super.getUserLogado(request));
					
					List<ModelCliente> modelClientes = daoClienteRepository.consultarClienteList(super.getUserLogado(request));
					request.setAttribute("totalPagina", daoClienteRepository.totalPagina(this.getUserLogado(request)));
					request.setAttribute("modelClientes", modelClientes);
					
					ObjectMapper mapper = new ObjectMapper();
					String json = mapper.writeValueAsString(dadosJsonUser);
					
					response.addHeader("totalPagina", ""+ daoClienteRepository.consultarClienteListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
					response.getWriter().write(json);
					
				}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {
					
					String nomeBusca = request.getParameter("nomeBusca");
					String pagina = request.getParameter("pagina");
					 
					List<ModelCliente> dadosJsonUser =  daoClienteRepository.consultarClienteListOffSet(nomeBusca, super.getUserLogado(request), Integer.parseInt(pagina));
					 
					ObjectMapper mapper = new ObjectMapper();
					 
					String json = mapper.writeValueAsString(dadosJsonUser);
					 
					response.addHeader("totalPagina", ""+ daoClienteRepository.consultarClienteListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
					response.getWriter().write(json);
					
					
				}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
					
					String pk_id_cliente = request.getParameter("pk_id_cliente");
					 
				     ModelCliente modelCliente = daoClienteRepository.consultarClienteID(pk_id_cliente, super.getUserLogado(request));
				 
				     List<ModelCliente> modelClientes = daoClienteRepository.consultarClienteList(super.getUserLogado(request));
				     request.setAttribute("modelClientes", modelClientes);
				     
				    request.setAttribute("msg", "Usuário em edição");
					request.setAttribute("modelCliente", modelCliente);
					request.setAttribute("totalPagina", daoClienteRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/cliente.jsp").forward(request, response);
					
					
				}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
					
					List<ModelCliente> modelClientes = daoClienteRepository.consultarClienteList(super.getUserLogado(request));
					
					request.setAttribute("modelClientes", modelClientes);
					request.setAttribute("totalPagina", daoClienteRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/cliente.jsp").forward(request, response);
					
				}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
					
					Integer offset = Integer.parseInt(request.getParameter("pagina"));
					
					List<ModelCliente> modelClientes = daoClienteRepository.consultaClienteListPaginada(this.getUserLogado(request), offset);
					
					request.setAttribute("modelClientes", modelClientes);
					request.setAttribute("totalPagina", daoClienteRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/cliente.jsp").forward(request, response);
					
				}else {
					
					
					List<ModelCliente> modelClientes = daoClienteRepository.consultarClienteList(super.getUserLogado(request));
					request.setAttribute("modelClientes", modelClientes);
					request.setAttribute("totalPagina", daoClienteRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/cliente.jsp").forward(request, response);
					
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
			
			String pk_id_cliente = request.getParameter("pk_id_cliente");
			String cli_cpf = request.getParameter("cli_cpf");
			String cli_nome = request.getParameter("cli_nome");
			String cli_cep = request.getParameter("cli_cep");
			String cli_endereco = request.getParameter("cli_endereco");
			String cli_bairro = request.getParameter("cli_bairro");
			String cli_cidade = request.getParameter("cli_cidade");
			String cli_uf = request.getParameter("cli_uf");
			String cli_telefone = request.getParameter("cli_telefone");
			
			ModelCliente modelCliente = new ModelCliente();
			
			modelCliente.setPk_id_cliente(pk_id_cliente != null && !pk_id_cliente.isEmpty() ? Long.parseLong(pk_id_cliente) : null);
			modelCliente.setCli_cpf(cli_cpf);
			modelCliente.setCli_nome(cli_nome);
			modelCliente.setCli_cep(cli_cep);
			modelCliente.setCli_endereco(cli_endereco);
			modelCliente.setCli_bairro(cli_bairro);
			modelCliente.setCli_cidade(cli_cidade);
			modelCliente.setCli_uf(cli_uf);
			modelCliente.setCli_telefone(cli_telefone);
			
			if(daoClienteRepository.validarCpf(modelCliente.getCli_cpf()) && modelCliente.getPk_id_cliente() == null) {
				
				msg = "Já existe cliente com o mesmo cpf!";
				
			}else {
				
				if(modelCliente.isNovo()) {
					msg = "Gravado com sucesso!";
				}else {
					msg = "Atualizado com sucesso!";
				}
				
				modelCliente = daoClienteRepository.gravarCliente(modelCliente, super.getUserLogado(request));
			}
			
			List<ModelCliente> modelClientes = daoClienteRepository.consultarClienteList(super.getUserLogado(request));
			request.setAttribute("modelClientes", modelClientes);
			
			request.setAttribute("msg", msg);
			request.setAttribute("modelCliente", modelCliente);
			 request.setAttribute("totalPagina", daoClienteRepository.totalPagina(this.getUserLogado(request)));
			request.getRequestDispatcher("principal/cliente.jsp").forward(request, response);
			
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			dispatcher.forward(request, response);
		}
	}

}
