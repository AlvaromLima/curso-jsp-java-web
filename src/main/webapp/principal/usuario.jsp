<%@page import="model.ModelLogin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<!-- Configuração o jstl para criação dinâmica do conteúdo da página  -->
<!-- A linha abaixo foi pega no https://www.tutorialspoint.com/jsp/jsp_standard_tag_library.htm# -->
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>

<body>

	<!-- Pre-loader start -->
	<jsp:include page="theme-loader.jsp"></jsp:include>
	<!-- Pre-loader end -->

	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">

			<jsp:include page="navbar.jsp"></jsp:include>

			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">

					<jsp:include page="navbarmainmenu.jsp"></jsp:include>

					<div class="pcoded-content">

						<!-- Page-header start -->
						<jsp:include page="page-header.jsp"></jsp:include>
						<!-- Page-header end -->

						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">
								<div class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">

										<div class="row">
											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">
													<div class="card-block">
														<h4 class="sub-title">Cadastro de Usuário</h4>

														<form class="form-material" enctype="multipart/form-data" id="formUser" action="<%=request.getContextPath()%>/ServletUserController" method="post">
															<input type="hidden" name="acao" id="acao" value="">

															<div class="form-group form-default form-static-label">
																<input type="text" name="id" id="id" class="form-control" readonly="readonly"
																	value="${modelUser.id}"> 
																<span class="form-bar"></span>
																<label class="float-label">ID:</label>
															</div>

															<div class="form-group form-default input-group mb-4">
																<div class="input-group-prepend">
																	<c:if test="${modelUser.fotoUser != '' && modelUser.fotoUser != null }">
																		<a href="<%= request.getContextPath()%>/User?acao=downLoadFoto&id=${modelUser.id}">	
																			<img alt="Imagem User" id="fotoembase64" src="${modelUser.fotoUser}" width="70px">
																		</a>
																	</c:if>
																	<c:if test="${modelUser.fotoUser == '' || modelUser.fotoUser == null}">
																		<img alt="Imagem User" id="fotoembase64" src="assets/images/avatar-blank.jpg" width="70px">
																	</c:if>
																</div>
																<input type="file" id="fileFoto" name="fileFoto" accept="image/*" onchange="trocarImg('fotoembase64', 'fileFoto')" class="form-control-file" style="margin-top: 10px; margin-left: 5px">
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="nome" id="nome" class="form-control" required="required"
																	value="${modelUser.nome}" autocomplete="off"> 
																<span class="form-bar"></span> 
																<label class="float-label">Nome:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="dataNascimento" id="dataNascimento" class="form-control" required="required"
																	value="${modelUser.dataNascimento}"> 
																<span class="form-bar"></span> 
																<label class="float-label">Data de Nascimento:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="rendaMensal" id="rendaMensal" class="form-control" required="required"
																	value="${modelUser.rendaMensal}"> 
																<span class="form-bar"></span> 
																<label class="float-label">Renda Mensal:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="email" name="email" id="email"	class="form-control" required="required"
																	value="${modelUser.email}" autocomplete="off"> 
																<span class="form-bar"></span> 
																<label class="float-label">Email:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<select class="form-control" aria-label="Default select example" name="perfil">
																	<option disabled="disabled">[Selecione o Perfil]</option>
																	<option value="ADMIN" <%
																		ModelLogin modelLogin = (ModelLogin) request.getAttribute("modelUser");
																		if (modelLogin != null && modelLogin.getPerfil().equals("ADMIN")) {
																			out.print(" ");
																			out.print("selected=\"selected\"");
																			out.print(" ");
																		}
																		%>>Admin</option>
																	<option value="SECRETARIA" <%
																		modelLogin = (ModelLogin) request.getAttribute("modelUser");
																		if (modelLogin != null && modelLogin.getPerfil().equals("SECRETARIA")) {
																			out.print(" ");
																			out.print("selected=\"selected\"");
																			out.print(" ");
																		}
																		%>>Secretária</option>
																	<option value="AUXILIAR" <%
																		modelLogin = (ModelLogin) request.getAttribute("modelUser");
																		if (modelLogin != null && modelLogin.getPerfil().equals("AUXILIAR")) {
																			out.print(" ");
																			out.print("selected=\"selected\"");
																			out.print(" ");
																		}
																		%>>Auxiliar</option>
																</select>
																<span class="form-bar"></span> 
																<label class="float-label">Perfil:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="cep" id="cep" class="form-control" required="required"
																	onblur="pesquisaCep();" value="${modelUser.cep}" autocomplete="off"> 
																<span class="form-bar"></span> 
																<label class="float-label">Cep:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="logradouro" id="logradouro" class="form-control" required="required"
																	value="${modelUser.logradouro}" autocomplete="off"> 
																<span class="form-bar"></span> 
																<label class="float-label">Logradouro:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="bairro" id="bairro" class="form-control" required="required"
																	value="${modelUser.bairro}" autocomplete="off"> 
																<span class="form-bar"></span> 
																<label class="float-label">Bairro:</label>
															</div>
															
															<div class="form-group form-default form-static-label">
																<input type="text" name="localidade" id="localidade" class="form-control" required="required"
																	value="${modelUser.localidade}" autocomplete="off"> 
																<span class="form-bar"></span> 
																<label class="float-label">Localidade:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="uf" id="uf" class="form-control" required="required"
																	value="${modelUser.uf}" autocomplete="off"> 
																<span class="form-bar"></span> 
																<label class="float-label">UF:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="numero" id="numero" class="form-control" required="required"
																	value="${modelUser.numero}" autocomplete="off"> 
																<span class="form-bar"></span> 
																<label class="float-label">Numero:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="complemento" id="complemento" class="form-control"
																	value="${modelUser.complemento}" autocomplete="off"> 
																<span class="form-bar"></span> 
																<label class="float-label">Complemento:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="text" name="login" id="login" class="form-control" required="required"
																	value="${modelUser.login}" autocomplete="off"> 
																<span class="form-bar"></span> 
																<label class="float-label">Login:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="password" name="senha" id="senha" class="form-control" required="required"
																	value="${modelUser.senha}" autocomplete="off"> 
																<span class="form-bar"></span>
																<label class="float-label">Senha:</label>
															</div>

															<div class="form-group form-default form-static-label ">
																<label style="font-size: 11px;">Sexo:&nbsp;</label>
																<input type="radio" name="sexo" checked="checked" value="MASCULINO" <%
																		modelLogin = (ModelLogin) request.getAttribute("modelUser");
																		if (modelLogin != null && modelLogin.getSexo().equals("MASCULINO")) {
																			out.print(" ");
																			out.print("checked=\"checked\"");
																			out.print(" ");
																		}
																	%>>&nbsp;Masculino&nbsp; 
																<input type="radio" name="sexo" value="FEMININO" <%
																		modelLogin = (ModelLogin) request.getAttribute("modelUser");
																		if (modelLogin != null && modelLogin.getSexo().equals("FEMININO")) {
																			out.print(" ");
																			out.print("checked=\"checked\"");
																			out.print(" ");
																		}
																	%>>&nbsp;Feminino
															</div>

															<button type="button" class="btn btn-primary waves-effect waves-light" onclick="limparForm();">Novo</button>
															<button class="btn btn-success waves-effect waves-light">Salvar</button>
															<button type="button" class="btn btn-danger waves-effect waves-light" onclick="criarDelete();">Excluir</button>
															<c:if test="${modelUser.id > 0 }">
																<a href="<%= request.getContextPath() %>/Telefone?idUser=${modelUser.id}" class="btn btn-primary waves-effect waves-light">Telefone</a>
															</c:if>
															<!-- Button trigger modal, para usar uma modal deve ser a mesma versão do boostrap(* Bootstrap v4.0.0-beta) que é a do projeto  -->
															<button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#modalUser">Pesquisar</button>

														</form>

													</div>
												</div>
											</div>
										</div>

										<span id="msg">${msg}</span>

										<div style="height: 300px; overflow: scroll;">
											<table class="table" id="modelListUserView">
												<thead>
													<tr>
														<th scope="col">ID</th>
														<th scope="col">Nome</th>
														<th scope="col">Ver</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${modelListUser}" var="ml">
														<tr>
															<td><c:out value="${ml.id}"></c:out></td>
															<td><c:out value="${ml.nome}"></c:out></td>
															<td><a href="<%= request.getContextPath() %>/User?acao=buscarEditar&id=${ml.id}" class="btn btn-success">Editar</a></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>

										<nav aria-label="Page navigation example">
  											<ul class="pagination">
  											
  												<%
  													int totalPagina = (int) request.getAttribute("totalPagina");

  													for (int p=0; p < totalPagina; p++){
  														String url = request.getContextPath() + "/User?acao=paginar&pagina=" + (p * 5);
														out.print("<li class=\"page-item\"><a class=\"page-link\" href=\""+ url +"\">"+(p + 1)+"</a></li>");  													
  													}
  													
  												%>
    											
  											</ul>
										</nav>

									</div>
									<!-- Page-body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="modalUser" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Pesquisa de Usuário</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">

					<div class="input-group mb-3">
						<input type="text" class="form-control"	id="nomeBusca" placeholder="Nome" aria-label="nome" aria-describedby="basic-addon2">
						<div class="input-group-append">
							<button class="btn btn-success" type="button" onclick="buscarUser()">Buscar</button>
						</div>
					</div>

				</div>

				<div style="height: 300px; overflow: scroll;">
					<table class="table" id="tabelaResultadoUser">
						<thead>
							<tr>
								<th scope="col">ID</th>
								<th scope="col">Nome</th>
								<th scope="col">Ver</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
				
				<nav aria-label="Page navigation example">
					<ul class="pagination" id="ulPaginacaoUserAjax">
					</ul>
				</nav>
				
				<span id="totalResultadosUser"></span>

				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"	data-dismiss="modal">Fechar</button>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="javascriptfile.jsp"></jsp:include>

	<script type="text/javascript">


		$( function() {
		
			$("#dataNascimento").datepicker({
			 	dateFormat: 'dd/mm/yy',
			 	dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
			 	dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
			 	dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
			 	monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
			 	monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
			 	nextText: 'Próximo',
			 	prevText: 'Anterior'
			});
		});

		$("#rendaMensal").maskMoney({showSymbol:true, symbol:"R$ ", decimal:",", thousands:"."});
		
		const formatter = new Intl.NumberFormat('pt-BR', {
			currency : 'BRL',
			minimumFractionDigits : 2
		});
		
		$("#rendaMensal").val(formatter.format($("#rendaMensal").val()));
		$("#rendaMensal").focus();
		
		var dataNascimento = $("#dataNascimento").val();
		
		if ( dataNascimento != null && dataNascimento != '' ) {
			
			var dateFormat = new Date(dataNascimento);
			
			$("#dataNascimento").val(dateFormat.toLocaleDateString('pt-BR', {timeZone: 'UTC'}));
			
		}

		$("#nome").focus();

		$("#numero").keypress(function (event) {
			return /\d/.test(String.fromCharCode(event.keyCode));
		});

		$("#cep").keypress(function (event) {
			return /\d/.test(String.fromCharCode(event.keyCode));
		});

		function criarDeleteComAjax() {

			var idUser = document.getElementById("id").value;

			if (idUser != null && idUser != '') {
				
				if (confirm("Deseja realmente excluir os dados?")) {
					
					var urlAction = document.getElementById('formUser').action;
					
					//Ajax para exclusão de usuários
					$.ajax({
						
						method : "get",
						url : urlAction,
						data : "id=" + idUser + "&acao=deletarAjax",
						success : function(response) {
							limparForm();
							document.getElementById('msg').textContent = response;
						}
					
					}).fail( function(xhr, status, errorThrown) {
						alert('Erro ao deletar usuário por id: '+ xhr.responseText);
					});

				}
				
			}

		}

		function criarDelete() {

			var idUser = document.getElementById("id").value;

			if (idUser != null && idUser != '') {
				
				if (confirm("Deseja realmente excluir os dados?")) {
					document.getElementById("formUser").method = 'get';
					document.getElementById("acao").value = 'deletar';
					document.getElementById("formUser").submit();
				}
				
			}

		}

		function limparForm() {
			
			var elementos = document.getElementById("formUser").elements; /*Retorna os elementos html dentro do form*/

			for (p = 0; p < elementos.length; p++) {
				elementos[p].value = '';
			}
			
		}
		
		function buscarUser() {
			
			var nomeBusca = document.getElementById("nomeBusca").value;
			
			if ( nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != '' ) { /*Validando que tem que ter valor pra buscar no BD*/ 

				var urlAction = document.getElementById('formUser').action;
				
				//Ajax para pesquisar usuários
				$.ajax({
					
					method : "get",
					url : urlAction,
					data : "nomeBusca=" + nomeBusca + "&acao=buscarUserAjax",
					success : function(response, textStatus, xhr ) {
						
						// Converte JSON para array
						var json = JSON.parse(response);
						
						$('#tabelaResultadoUser > tbody > tr').remove();
						$("#ulPaginacaoUserAjax > li").remove();
						
						for (var p=0; p < json.length; p++) {
							$('#tabelaResultadoUser > tbody').append('<tr> <td>'+json[p].id+'</td>'
									+ '<td>'+json[p].nome+'</td>'
									+ '<td><button type="button" class="btn btn-info" onclick="verEditar('+json[p].id+')">Ver/Edit</button></td>'
									+ '</tr>');
						}
						
						document.getElementById('totalResultadosUser').textContent = 'Resultados: ' + json.length;

						var totalPagina = xhr.getResponseHeader("totalPagina");
						
						for (var p=0; p < totalPagina; p++) {
							var url = "nomeBusca=" + nomeBusca + "&acao=buscarUserAjaxPage&pagina=" + (p * 5);
							$("#ulPaginacaoUserAjax").append('<li class="page-item"><a class="page-link" href="#" onclick="buscaUserPagAjax(\''+url+'\')">' + (p + 1) + '</a></li>');
						}
						
					}
				
				}).fail( function(xhr, status, errorThrown) {
					alert('Erro ao buscar usuário por nome: '+ xhr.responseText);
				});
					
			}
			
		}
		
		function buscaUserPagAjax(url){

			var urlAction = document.getElementById('formUser').action;
			var nomeBusca = document.getElementById("nomeBusca").value;

			//Ajax para pesquisar usuários
			$.ajax({
				
				method : "get",
				url : urlAction,
				data : url,
				success : function(response, textStatus, xhr ) {
					
					// Converte JSON para array
					var json = JSON.parse(response);
					
					$('#tabelaResultadoUser > tbody > tr').remove();
					$("#ulPaginacaoUserAjax > li").remove();
					
					for (var p=0; p < json.length; p++) {
						$('#tabelaResultadoUser > tbody').append('<tr> <td>'+json[p].id+'</td>'
								+ '<td>'+json[p].nome+'</td>'
								+ '<td><button type="button" class="btn btn-info" onclick="verEditar('+json[p].id+')">Ver/Edit</button></td>'
								+ '</tr>');
					}
					
					document.getElementById('totalResultadosUser').textContent = 'Resultados: ' + json.length;

					var totalPagina = xhr.getResponseHeader("totalPagina");
					
					for (var p=0; p < totalPagina; p++) {
						var url = "nomeBusca=" + nomeBusca + "&acao=buscarUserAjaxPage&pagina=" + (p * 5);
						$("#ulPaginacaoUserAjax").append('<li class="page-item"><a class="page-link" href="#" onclick="buscaUserPagAjax(\''+url+'\')">' + (p + 1) + '</a></li>');
					}
					
				}
			
			}).fail( function(xhr, status, errorThrown) {
				alert('Erro ao buscar usuário por nome: '+ xhr.responseText);
			});
		}
		
		function verEditar(id) {

			var urlAction = document.getElementById('formUser').action;
		
			//Redirecionar em javascript
			window.location.href = urlAction + '?acao=buscarEditar&id=' + id;

		}

		function trocarImg(fotoembase64, filefoto) {
			
			var preview = document.getElementById(fotoembase64); //campo IMG do html
			var fileUser = document.getElementById(filefoto).files[0];
			var reader = new FileReader();//Trabalha com a imagem na tela
			
			//evento de carregamento na tela
			reader.onloadend = function () {
				preview.src = reader.result; //Carrega a foto na tela
			};
			
			//Se realmente tiver a foto sendo carregada
			if ( fileUser ) {
				reader.readAsDataURL(fileUser); //Preview da imagem
			} else {
				preview.src = '';
			}
			
		}
		
		function pesquisaCep() {

			var cep = $("#cep").val();
			
            //Consulta o webservice viacep.com.br/
            $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {
                if (!("erro" in dados)) {
                    //Atualiza os campos com os valores da consulta.
                    $("#cep").val(dados.cep);
                    $("#logradouro").val(dados.logradouro);
                    $("#bairro").val(dados.bairro);
                    $("#localidade").val(dados.localidade);
                    $("#uf").val(dados.uf);
                } //end if.
                else {
                    //CEP pesquisado não foi encontrado.
                    alert("CEP não encontrado.");
                }
            });
		}

	</script>
</body>

</html>
