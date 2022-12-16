<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- Configuração o jstl para criação dinâmica do conteúdo da página  -->
<!-- A linha abaixo foi pega no https://www.tutorialspoint.com/jsp/jsp_standard_tag_library.htm# -->
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

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
														<h4 class="sub-title">Relatório de Usuário</h4>
														
														<form class="form-material" id="formUser" action="<%=request.getContextPath()%>/ServletUserController" method="get">

															<input type="hidden" id="acaoRelatorioImprimirTipo" name="acao" value="imprimirRelatorioUser">

															<div class="form-row align-items-center">
																<label class="col-3">Data Inicial</label>
																<label class="col-3">Data Final</label>
															</div>							
																							
															<div class="form-row align-items-center">
																<div class="col-3">
																	<input type="text" class="form-control mb-2"
																			id="dataInicial" name="dataInicial" value="${dataInicial}">
																</div>
																
																<div class="col-3">
																	<input type="text" class="form-control mb-2"
																			id="dataFinal" name="dataFinal" value="${dataFinal}">
																</div>

																<div class="col-auto my-1">
																	<button type="button" onclick="imprimirHtml();" class="btn btn-primary mb-2">Imprimir Relatório</button>
																	<button type="button" onclick="imprimirPdf();" class="btn btn-primary mb-2">Imprimir PDF</button>
																	<button type="button" onclick="imprimirXls();" class="btn btn-primary mb-2">Imprimir Xls</button>
																</div>
															</div>
														</form>

														<div style="height: 300px; overflow: scroll;">
															<table class="table" id="modelListUserView">
																<thead>
																	<tr>
																		<th scope="col">ID</th>
																		<th scope="col">Nome</th>
																		<th scope="col">Nascimento</th>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach items="${listaUser}" var="ml">
																		<tr>
																			<td><c:out value="${ml.id}"></c:out></td>
																			<td><c:out value="${ml.nome}"></c:out></td>
																			<td><fmt:formatDate pattern="dd/MM/yyyy" value="${ml.dataNascimento}"/></td>
																		</tr>
																		<c:forEach items="${ml.telefones}" var="tl">
																			<tr>
																			<td/>
																				<td style="font-size: 10px;"><c:out value="${tl.numero}"></c:out></td>
																			</tr>
																		</c:forEach>
																	</c:forEach>
																</tbody>
															</table>
														</div>

													</div>
												</div>
											</div>
										</div>
											
										<span>${msg}</span>

									</div>
									<!-- Page-body end -->
								
								</div>
								<div id="styleSelector"></div>
							
							</div>
							<!-- Main-body end -->

						</div>
					</div>
				
				</div>
			</div>
		
		</div>
	</div>

	<jsp:include page="javascriptfile.jsp"></jsp:include>

	<script type="text/javascript">

		function imprimirHtml() {
			document.getElementById("acaoRelatorioImprimirTipo").value = 'imprimirRelatorioUser';
			$("#formUser").submit();
		}

		function imprimirPdf() {
			document.getElementById("acaoRelatorioImprimirTipo").value = 'imprimirRelatorioPDF';
			$("#formUser").submit();
		}

		function imprimirXls() {
			document.getElementById("acaoRelatorioImprimirTipo").value = 'imprimirRelatorioExcel';
			$("#formUser").submit();
		}
		
		$( function() {
		
			$("#dataInicial").datepicker({
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

		$( function() {
			
			$("#dataFinal").datepicker({
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
		
	</script>
	
</body>

</html>
