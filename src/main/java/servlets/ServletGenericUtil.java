package servlets;

import java.io.Serializable;

import dao.DAOUserRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.ModelLogin;


public class ServletGenericUtil extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private DAOUserRepository daoUsuarioRepository = new DAOUserRepository();
	
	public Long getUserLogado(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		
		String usuarioLogado = (String) session.getAttribute("usuario");
		
		return daoUsuarioRepository.consultaUsuarioLogado(usuarioLogado).getId();
		
	}

	public ModelLogin getUserLogadoObjt(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		
		String usuarioLogado = (String) session.getAttribute("usuario");
		
		return daoUsuarioRepository.consultaUsuarioLogado(usuarioLogado);
		
	}
	
}
