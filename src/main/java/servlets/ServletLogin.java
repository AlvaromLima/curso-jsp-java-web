package servlets;

import java.io.IOException;

import dao.DAOLoginRepository;
import dao.DAOUserRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

@WebServlet(urlPatterns = {"/principal/Login", "/Login"}) /*Mapeamento de url que vem da tela de Login*/
public class ServletLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();
	private DAOUserRepository daoUserRepository = new DAOUserRepository();

	public ServletLogin() {
	}

	//Recebe os dados pela url em parametros
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// Para fazer Logout
		String acao = request.getParameter("acao");
		if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
		
			request.getSession().invalidate(); //Invalida a sessão
			RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
			redirecionar.forward(request, response);

		}else {
			//Retorna a tela de login
			doPost(request, response);
		}
		
	}

	//Recebe os dados enviados por um formulário
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//Primeiro passo, criar classe modelLogin para guardar as variaveis da tela de login
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");
		
		try {
			
			if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {

				//Instanciar o objeto e Popular as variaveis da classe 
				ModelLogin modelLogin = new ModelLogin();
				modelLogin.setLogin(login);
				modelLogin.setSenha(senha);
			
				if (daoLoginRepository.validarAutenticacao(modelLogin)){ /*Busca login e senha do banco*/
					
					//Pesquisa no banco se usuario é admin
					modelLogin = daoUserRepository.consultaUsuarioLogado(login);
					
					//direcionando para a pagina principal do sistema, quando login e senha for admin
					request.getSession().setAttribute("usuario", modelLogin.getLogin());
					request.getSession().setAttribute("perfil", modelLogin.getPerfil());
					request.getSession().setAttribute("imagemUser", modelLogin.getFotoUser());
					
					if (url == null || url.equals("null")) {
						url = "principal/principal.jsp";
					}
					
					RequestDispatcher redirecionar = request.getRequestDispatcher(url);
					redirecionar.forward(request, response);
					
				}else {

					RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg", "Informe o login e senha corretamente!");
					redirecionar.forward(request, response);
		
				}
				
			}else {
				
				RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
				request.setAttribute("msg", "Informe o login e senha corretamente!");
				redirecionar.forward(request, response);
			
			}
			
		} catch (Exception e) {
			
			e.printStackTrace(); /*Erro vai para a console*/
			/*Erro vai para a tela de erros*/
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		
		}	
		
		//Criando a pag principal.jsp na pasta principal

	}

}
