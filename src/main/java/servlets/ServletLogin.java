package servlets;

import java.io.IOException;

import dao.DAOLoginRepository;
import dao.DAOUsuarioRepository;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ModelLogin;

@WebServlet(urlPatterns = {"/principal/ServletLogin", "/ServletLogin"})
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
	public ServletLogin() {
		
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		
		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
			
			request.getSession().invalidate();
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
			
		}else {
			
			doPost(request, response);
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String usu_nome = request.getParameter("usu_nome");
		String usu_login = request.getParameter("usu_login");
		String cli_senha = request.getParameter("cli_senha");
		String url = request.getParameter("url");
		
	try {
		
			if(usu_login != null && !usu_login.isEmpty() && cli_senha != null && !cli_senha.isEmpty()) {
				
				ModelLogin modelLogin = new ModelLogin();
				
				modelLogin.setUsu_nome(usu_nome);
				modelLogin.setUsu_login(usu_login);
				modelLogin.setCli_senha(cli_senha);
				
				if(daoLoginRepository.validarAutenticacao(modelLogin)) {
					
					modelLogin = daoUsuarioRepository.consultarUsuarioLogado(usu_login);
					
					request.getSession().setAttribute("usuario", modelLogin.getUsu_login());
					request.getSession().setAttribute("usu_perfil", modelLogin.getUsu_perfil());
					
					if(url == null || url.equals("null")) {
						url = "principal/principal.jsp";
					}
					
					RequestDispatcher dispatcher = request.getRequestDispatcher(url);
					dispatcher.forward(request, response);
					
				}else {
					
					RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg", "Informe o login corretamente!");
					dispatcher.forward(request, response);
				}
				
			}else {
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
				request.setAttribute("msg", "Informe o login corretamente!");
				dispatcher.forward(request, response);
			}
		
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			dispatcher.forward(request, response);
		}
	}

}
