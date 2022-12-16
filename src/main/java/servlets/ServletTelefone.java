package servlets;


import java.io.IOException;
import java.util.List;

import dao.DAOTelefoneRepository;
import dao.DAOUserRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;
import model.ModelTelefone;

@WebServlet(urlPatterns = { "/Telefone" }) /* Mapeamento de url que vem da tela de Usuario */
public class ServletTelefone extends ServletGenericUtil {

	private static final long serialVersionUID = 1L;

	private DAOUserRepository daoUserRepository = new DAOUserRepository();

	private DAOTelefoneRepository daoTelefoneRepository = new DAOTelefoneRepository();
	
	public ServletTelefone() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String acao = request.getParameter("acao");

			if (acao != null && !acao.isEmpty() && acao.equals("excluir")) {
				
				String idFone = request.getParameter("id");

				daoTelefoneRepository.deleteFone(Long.parseLong(idFone));

				String userPai = request.getParameter("userpai");

				ModelLogin modelLogin = daoUserRepository.consultaUsuarioID(Long.parseLong(userPai));
				
				List<ModelTelefone> modelTelefones = daoTelefoneRepository.listFone(modelLogin.getId());
				
				// Redireciona para a tela de Telefones e mantém os dados na tela
				request.setAttribute("modelTelefones", modelTelefones);
				request.setAttribute("msg", "Telefone Excluído com Sucesso");
				request.setAttribute("modelLogin", modelLogin);
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
				
				return;
			}
			
			String idUser = request.getParameter("idUser");

			if (idUser != null && !idUser.isEmpty()) {

				ModelLogin modelLogin = daoUserRepository.consultaUsuarioID(Long.parseLong(idUser));

				List<ModelTelefone> modelTelefones = daoTelefoneRepository.listFone(modelLogin.getId());
				
				// Redireciona para a tela de Telefones e mantém os dados na tela
				request.setAttribute("modelTelefones", modelTelefones);
				request.setAttribute("modelLogin", modelLogin);
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);

			} else {

				List<ModelLogin> modelLogins = daoUserRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelListUser", modelLogins);
				request.setAttribute("totalPagina", daoUserRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			/* Erro vai para a tela de erros */
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
		
			String usuario_pai_id = request.getParameter("id");
			String numero = request.getParameter("numero");

			if (!daoTelefoneRepository.existeFone(numero, Long.valueOf(usuario_pai_id))) {

				ModelTelefone modelTelefone = new ModelTelefone();

				modelTelefone.setNumero(numero);
				modelTelefone.setUsuario_pai_id(daoUserRepository.consultaUsuarioID(Long.parseLong(usuario_pai_id)));
				modelTelefone.setUsuario_cad_id(super.getUserLogadoObjt(request));
				
				daoTelefoneRepository.gravaTelefone(modelTelefone);

				request.setAttribute("msg", "Telefone salvo com sucesso");
				
			} else {
				
				request.setAttribute("msg", "Telefone já existe");
				
			}

			List<ModelTelefone> modelTelefones = daoTelefoneRepository.listFone(Long.parseLong(usuario_pai_id));
			
			ModelLogin modelLogin = daoUserRepository.consultaUsuarioID(Long.parseLong(usuario_pai_id));

			// Redireciona para a tela de Telefones e mantém os dados na tela
			request.setAttribute("modelLogin", modelLogin);
			request.setAttribute("modelTelefones", modelTelefones);
			request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
