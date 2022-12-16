package servlets;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;

import beandto.BeanDtoGraficoSalarioUser;
import dao.DAOUserRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelLogin;
import util.ReportUtil;

@MultipartConfig /* Essa anota��o � para aceitar upload de fotos */
@WebServlet(urlPatterns = { "/User" }) /* Mapeamento de url que vem da tela de Usuario */
public class ServletUserController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;

	private DAOUserRepository daoUserRepository = new DAOUserRepository();

	public ServletUserController() {
	}

	//Use get para consultar ou excluir da tabela
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {

			String acao = request.getParameter("acao");
			
			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {

				String idUser = request.getParameter("id");
				
				daoUserRepository.deletarUser(idUser);

				List<ModelLogin> modelLogins = daoUserRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelListUser", modelLogins);
				
				request.setAttribute("msg", "Exclu�do com sucesso!");
				request.setAttribute("totalPagina", daoUserRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			
			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarAjax")) {

					String idUser = request.getParameter("id");
					
					daoUserRepository.deletarUser(idUser);
					
					response.getWriter().write("Exclu�do com sucesso!");
					
			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
				
					String nomeBusca = request.getParameter("nomeBusca");
				
					List<ModelLogin> dadosJsonUser = daoUserRepository.consultaUsuarioList(nomeBusca, super.getUserLogado(request));

					// Criando objeto json e formatando.
					ObjectMapper mapper = new ObjectMapper();
					String json = mapper.writeValueAsString(dadosJsonUser);
					
					response.addHeader("totalPagina", "" + daoUserRepository.consultaUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
					response.getWriter().write(json);

			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {
				
				String nomeBusca = request.getParameter("nomeBusca");
				String pagina = request.getParameter("pagina");
			
				List<ModelLogin> dadosJsonUser = daoUserRepository.consultaUsuarioListOffSet(nomeBusca, super.getUserLogado(request), Integer.parseInt(pagina));

				// Criando objeto json e formatando.
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(dadosJsonUser);
				
				response.addHeader("totalPagina", "" + daoUserRepository.consultaUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
				response.getWriter().write(json);

			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
					
					String idUser = request.getParameter("id");

					ModelLogin modelLogin = daoUserRepository.consultaUsuarioID(idUser, super.getUserLogado(request));
					
					List<ModelLogin> modelLogins = daoUserRepository.consultaUsuarioList(super.getUserLogado(request));
					request.setAttribute("modelListUser", modelLogins);

					// Redireciona para a tela de usu�rio e mant�m os dados na tela
					request.setAttribute("msg", "Usu�rio em edi��o");
					request.setAttribute("modelUser", modelLogin);
					request.setAttribute("totalPagina", daoUserRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
					
			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
				
					List<ModelLogin> modelLogins = daoUserRepository.consultaUsuarioList(super.getUserLogado(request));

					// Redireciona para a tela de usu�rio e mant�m os dados na tela
					request.setAttribute("msg", "Usu�rios Carregados");
					request.setAttribute("modelListUser", modelLogins);
					request.setAttribute("totalPagina", daoUserRepository.totalPagina(this.getUserLogado(request)));
					request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downLoadFoto")) {

					String idUser = request.getParameter("id");
				
					ModelLogin modelLogin = daoUserRepository.consultaUsuarioID(idUser, super.getUserLogado(request));
					
					if (modelLogin.getFotoUser() != null && !modelLogin.getFotoUser().isEmpty() ) {
						response.setHeader("Content-Disposition", "attachment;filename=arquivo." + modelLogin.getExtensaoFotoUser());
						response.getOutputStream().write(new Base64().decodeBase64(modelLogin.getFotoUser().split("\\,")[1]));
					}
				
			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {

				Integer offset = Integer.parseInt(request.getParameter("pagina")) ;
				
				List<ModelLogin> modelLogins = daoUserRepository.consultaUsuarioListPaginada(super.getUserLogado(request), offset);
				request.setAttribute("modelListUser", modelLogins);
				request.setAttribute("totalPagina", daoUserRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			
			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioUser")) {

				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");
				
				if (dataInicial == null || dataInicial.isEmpty() && 
						dataFinal == null || dataFinal.isEmpty() ) {
					request.setAttribute("listaUser", daoUserRepository.consultaUsuarioListRel(super.getUserLogado(request)));
				} else {
					request.setAttribute("listaUser", daoUserRepository.consultaUsuarioListRel(super.getUserLogado(request), dataInicial, dataFinal));
				}
				
				request.setAttribute("dataInicial", dataInicial);
				request.setAttribute("dataFinal", dataFinal);
				request.getRequestDispatcher("principal/reluser.jsp").forward(request, response);
				
			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioPDF") 
					|| acao.equalsIgnoreCase("imprimirRelatorioExcel")) {

				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");

				List<ModelLogin> modelLogins = null;
				
				if (dataInicial == null || dataInicial.isEmpty() && 
						dataFinal == null || dataFinal.isEmpty() ) {

					modelLogins = daoUserRepository.consultaUsuarioListRel(super.getUserLogado(request));
				
				} else {

					modelLogins = daoUserRepository.consultaUsuarioListRel(super.getUserLogado(request), dataInicial, dataFinal);
				}

				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("relatorio") + File.separator);
				
				byte[] relatorio = null;
				String extensao = "";
				
				if  ( acao.equalsIgnoreCase("imprimirRelatorioPDF") ) {
					relatorio = new ReportUtil().geraRelatorioPDF(modelLogins, "rel-user-jsp", params, request.getServletContext());
					extensao = "pdf";
				}else if ( acao.equalsIgnoreCase("imprimirRelatorioExcel") ) {
						relatorio = new ReportUtil().geraRelatorioExcel(modelLogins, "rel-user-jsp", params, request.getServletContext());
						extensao = "xls";
				}

				response.setHeader("Content-Disposition", "attachment;filename=arquivo." + extensao);
				response.getOutputStream().write(relatorio);

			}

			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("graficoSalario")) {

				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");
				
				if (dataInicial == null || dataInicial.isEmpty() && 
						dataFinal == null || dataFinal.isEmpty() ) {

					BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = daoUserRepository.
							montarGraficoMediaSalario(super.getUserLogado(request));

					// Criando objeto json e formatando e retorna para tela.
					ObjectMapper mapper = new ObjectMapper();
					String json = mapper.writeValueAsString(beanDtoGraficoSalarioUser);
					response.getWriter().write(json);
					
				} else {

					BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = daoUserRepository.
							montarGraficoMediaSalario(super.getUserLogado(request), dataInicial, dataFinal);

					// Criando objeto json e formatando e retorna para tela.
					ObjectMapper mapper = new ObjectMapper();
					String json = mapper.writeValueAsString(beanDtoGraficoSalarioUser);
					response.getWriter().write(json);

				}
				
			}

			else {

				List<ModelLogin> modelLogins = daoUserRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelListUser", modelLogins);
				request.setAttribute("totalPagina", daoUserRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace(); /* Erro vai para a console */
			/* Erro vai para a tela de erros */
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}

	}

	//Usa post para atualizar e gravar na tabela
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String msg = "";

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String perfil = request.getParameter("perfil");
			String sexo = request.getParameter("sexo");
			String cep = request.getParameter("cep");
			String logradouro = request.getParameter("logradouro");
			String bairro = request.getParameter("bairro");
			String localidade = request.getParameter("localidade");
			String uf = request.getParameter("uf");
			String numero = request.getParameter("numero");
			String complemento = request.getParameter("complemento");
			String dataNascimento = request.getParameter("dataNascimento");
			String rendaMensal = request.getParameter("rendaMensal");

			//Retirar . e R$ do campo rendaMensal que vem da tela
			rendaMensal = rendaMensal.split("\\ ")[1].replaceAll("\\.", "").replaceAll("\\,", ".");
			
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			modelLogin.setPerfil(perfil);
			modelLogin.setSexo(sexo);
			modelLogin.setCep(cep);
			modelLogin.setLogradouro(logradouro);
			modelLogin.setBairro(bairro);
			modelLogin.setLocalidade(localidade);
			modelLogin.setUf(uf);
			modelLogin.setNumero(numero);
			modelLogin.setComplemento(complemento);

			//convertendo data para o banco Postgresql
			modelLogin.setDataNascimento(Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento))));
			modelLogin.setRendaMensal(Double.valueOf(rendaMensal));
			
			//Chegando a imagem na servlet
			if ( request.getPart("fileFoto") != null ) {
				
				Part part = request.getPart("fileFoto"); /*Pega foto da tela* (image/jpg)*/
				
				if ( part.getSize() > 0) {

					byte[] foto = IOUtils.toByteArray(part.getInputStream()); /*Converte imagem para byte*/

					//feito o split part.getContentType().split("\\/")[1] para pegar a extensa� da foto
					String imagemBase64 = "data:image/" + part.getContentType().split("\\/")[1] + ";base64," + new Base64().encodeBase64String(foto);
					
					modelLogin.setFotoUser(imagemBase64);
					modelLogin.setExtensaoFotoUser(part.getContentType().split("\\/")[1]); /*Pega somente a extens�o png*/
					
				}
				
			}
			
			// Se ja existe mesmo login , n�o deixo cadastrar
			if ( daoUserRepository.validarLogin(modelLogin.getLogin()) 
					&& modelLogin.getId() == null ) {
				msg = "J� existe usu�rio com mesmo login, informe outro login!";
			}else {
				if ( modelLogin.isNovo()) {
					msg = "Gravado usu�rio com sucesso!";
				}else {
					msg = "Atualizado usu�rio com sucesso!";
				}
				modelLogin = daoUserRepository.gravarUsuario(modelLogin, super.getUserLogado(request));
			}
			
			// Redireciona para a tela de usu�rio e mant�m os dados na tela
			List<ModelLogin> modelLogins = daoUserRepository.consultaUsuarioList(super.getUserLogado(request));
			request.setAttribute("modelListUser", modelLogins);

			request.setAttribute("msg", msg);
			request.setAttribute("modelUser", modelLogin);
			request.setAttribute("totalPagina", daoUserRepository.totalPagina(this.getUserLogado(request)));
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace(); /* Erro vai para a console */
			/* Erro vai para a tela de erros */
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}

	}

}
