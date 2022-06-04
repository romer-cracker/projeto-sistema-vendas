package servlets;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOUsuarioRepository;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ModelLogin;


@WebServlet("/ServletUsuarioController")
public class ServletUsuarioController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
	
    private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();   
    
    public ServletUsuarioController() {
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	try {
		
		String acao = request.getParameter("acao");
		
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				
				String pk_usuarioUser = request.getParameter("pk_usuario");
				
				daoUsuarioRepository.deletarUser(pk_usuarioUser);
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				
				request.setAttribute("msg", "Excluido com sucesso!");
			    request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarAjax")) {
				
				String pk_usuarioUser = request.getParameter("pk_usuario");
				
				daoUsuarioRepository.deletarUser(pk_usuarioUser);
				
				response.getWriter().write("Excluído com sucesso!");
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
				
				String nomeBusca = request.getParameter("nomeBusca");
				
				List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultarUsuarioList(nomeBusca, super.getUserLogado(request));
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
				 request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.setAttribute("modelLogins", modelLogins);
				
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(dadosJsonUser);
				
				response.addHeader("totalPagina", ""+ daoUsuarioRepository.consultarUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
				response.getWriter().write(json);
				
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {
				
				String nomeBusca = request.getParameter("nomeBusca");
				String pagina = request.getParameter("pagina");
				 
				List<ModelLogin> dadosJsonUser =  daoUsuarioRepository.consultarUsuarioListOffSet(nomeBusca, super.getUserLogado(request), Integer.parseInt(pagina));
				 
				ObjectMapper mapper = new ObjectMapper();
				 
				String json = mapper.writeValueAsString(dadosJsonUser);
				 
				response.addHeader("totalPagina", ""+ daoUsuarioRepository.consultarUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
				response.getWriter().write(json);
				
				
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
				
				String pk_usuario = request.getParameter("pk_usuario");
				 
			     ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioID(pk_usuario, super.getUserLogado(request));
			 
			     List<ModelLogin> modelLogins = daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
			     request.setAttribute("modelLogins", modelLogins);
			     
			    request.setAttribute("msg", "Usuário em edição");
				request.setAttribute("modelLogin", modelLogin);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
				
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
				
				Integer offset = Integer.parseInt(request.getParameter("pagina"));
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioListPaginada(this.getUserLogado(request), offset);
				
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			}else {
				
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
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
		
		String pk_usuario = request.getParameter("pk_usuario");
		String usu_nome = request.getParameter("usu_nome");
		String usu_login = request.getParameter("usu_login");
		String cli_senha = request.getParameter("cli_senha");
		String usu_perfil = request.getParameter("usu_perfil");
		
		ModelLogin modelLogin = new ModelLogin();
		
		modelLogin.setPk_usuario(pk_usuario != null && !pk_usuario.isEmpty() ? Long.parseLong(pk_usuario) : null);
		modelLogin.setUsu_nome(usu_nome);
		modelLogin.setUsu_login(usu_login);
		modelLogin.setCli_senha(cli_senha);
		modelLogin.setUsu_perfil(usu_perfil);
		
		if(daoUsuarioRepository.validarLogin(modelLogin.getUsu_login()) && modelLogin.getPk_usuario() == null) {
			
			msg = "Já existe usuário com o mesmo login, Informe outro!";
			
		}else {
			
			if(modelLogin.isNovo()) {
				msg = "Gravado com sucesso!";
			}else {
				msg = "Atualizado com sucesso!";
			}
			
			modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin, super.getUserLogado(request));
		}
		
		List<ModelLogin> modelLogins = daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
		request.setAttribute("modelLogins", modelLogins);
		
		request.setAttribute("msg", msg);
		request.setAttribute("modelLogin", modelLogin);
		 request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
		request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		
	}catch (Exception e) {
		e.printStackTrace();
		RequestDispatcher dispatcher = request.getRequestDispatcher("erro.jsp");
		request.setAttribute("msg", e.getMessage());
		dispatcher.forward(request, response);
	}
	}

}
