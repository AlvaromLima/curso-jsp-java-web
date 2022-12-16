package filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import connection.SingleConnectionBanco;
import dao.DAOVersionadorBanco;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

//Porta de entrada do sistema , a partir da pasta principal
@WebFilter(urlPatterns = {"/principal/*"}) /*Intercepta todas as requisições que vierem do projeto ou mapeamento*/
public class FilterAutenticacao implements Filter {
	
	private static Connection connection;
	
	public FilterAutenticacao() {
    }

    /*Encerra os processos quando o servidor é parado*/
    /*kill os processos de conexão com o banco*/
	public void destroy() {
		try {
			System.out.println("Fechando o banco...");
			connection.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*Intercepta as requisições e as respostas no sistema*/
	/*Tudo que fizer no sistema vai passar por aqui*/
	/*Validação de autenticação*/
	/*Dar commit e rollback de transações do banco*/
	/*Validar e fazer redirecionamento de paginas*/
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			
			String usuarioLogado = (String) session.getAttribute("usuario");
			String urlParaAutenticar = req.getServletPath();/*Url que esta sendo acessada*/
			
			/*Validar se o usuário está logado senão redireciona para a tela de login*/
			if (usuarioLogado == null &&
					!urlParaAutenticar.equalsIgnoreCase("/principal/Login")) {/*Não está logado*/
				
				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor realize o login!");
				redireciona.forward(request, response);
				return; /*Para a execução e redireciona para o login*/
			}else {
				chain.doFilter(request, response);
			}
			
			connection.commit();/*Deu tudo certo, então comita as alterações no banco de dados*/
			
		}catch (Exception e){
			
			e.printStackTrace(); /*Erro vai para a console*/
			/*Erro vai para a tela de erros*/
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);

			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();/*Erro vai para a console*/
				/*Erro vai para a tela de erros*/
				//RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
				request.setAttribute("msg", e1.getMessage());
				redirecionar.forward(request, response);				
			}
		}
		
	}

	/*Inicia os processos ou recursos quando o servidor sobre o projeto*/
	/*Iniciar a conexão com o banco*/
	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConnectionBanco.getConnection();

		DAOVersionadorBanco daoVersionadorBanco = new DAOVersionadorBanco();
		
		String caminhoPastaSQL = fConfig.getServletContext().getRealPath("versionadorbancosql") + File.separator;
	
		File[] filesSql = new File(caminhoPastaSQL).listFiles();
		
		try {
		
			for (File file : filesSql) {
			
				boolean arquivoJaRodado = daoVersionadorBanco.arquivoSqlRodado(file.getName());
		
				if (!arquivoJaRodado) {
					
					FileInputStream entradaArquivo = new FileInputStream(file);
					
					Scanner lerArquivo = new Scanner(entradaArquivo, "UTF-8");
					
					StringBuilder sql = new StringBuilder();
					
					while (lerArquivo.hasNext()) {
						sql.append(lerArquivo.nextLine());
						sql.append("\n");
					}
					
					connection.prepareStatement(sql.toString()).execute();
					daoVersionadorBanco.gravaArquivoSqlRodado(file.getName());
					
					connection.commit();
					lerArquivo.close();
					
				}
			}
		
		} catch (Exception e) {

			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	
}
