<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<!-- Iremos extrair de dashboard-template do curso para colocarmos na pagina principal -->
<!-- descompactei mega-able-lite após ter feito download do dashboard do curso -->
<!-- acessei o arquivo index, dentro da pasta mega-able-lite, descompactada em download. -->
<!-- Copiei a pasta assets, que esta na pasta mega-able-lite, para o projeto e colei dentro da webapp, para ser utilizado -->
<!-- Para colocarmos layout de index, abrimos o arquivo index e selecionei tudo, copiei e colei na principal.jsp, e arrumando o layout para o nosso projeto -->

<!-- Buscando do https://getbootstrap.com/docs/5.0/getting-started/introduction/-->
<html lang="en">
<head>
	<meta charset="ISO-8859-1">
	<!-- Buscando do https://getbootstrap.com/docs/5.0/getting-started/introduction/-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap CSS -->
    <!--  link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous"-->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
	
	<title>Projeto JSP - Tela de Login</title>
	
	<style type="text/css">

		form{
			position: absolute;
			top: 30%;
			left: 33%;
			right: 33%
		}
		
		h5.title{
			position: absolute;
			top: 20%;
			left: 33%;
		}

		h5.msg{
			position: absolute;
			top: 90%;
			left: 33%;
			font-size: 15px;
			color: #664d03;
			background-color: #fff3cd;
			border-color: #ffecb5;
		}

	</style>	
</head>

<body>
	<h5 class="title">Curso de JSP</h5>
	
	<!-- Buscando do https://getbootstrap.com/docs/5.0/forms/layout/-->
	<!-- Chama pela action a ServelLogin.java pela anotação @WebServlet -->
	<form action="<%= request.getContextPath() %>/Login" method="post" class="row g-3 needs-validation" novalidate>
	
		<input type="hidden" value="<%= request.getParameter("url") %>" name="url">
	
  		<div class="mb-3">
  			<label class="form-label">Login</label>
			<input class="form-control" name="login" type="text" required/>
			<div class="invalid-feedback">
      			Campo Obrigatório
    		</div>
			<div class="valid-feedback">
      			ok
    		</div>
		</div>
			
		<div class="mb-3">
			<label class="form-label">Senha</label>
			<input class="form-control" name="senha" type="password" required/>
			<div class="invalid-feedback">
      			Campo Obrigatório
    		</div>
			<div class="valid-feedback">
      			ok
    		</div>
		</div>
			
		<input type="submit" class="btn btn-primary" value="Acessar">
		
	</form>
	
	<h5 class="msg">${msg}</h5>

	<!-- Buscando do https://getbootstrap.com/docs/5.0/getting-started/introduction/-->
    <!-- Option 1: Bootstrap Bundle with Popper -->
    <!-- script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script> -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
	
	<script type="text/javascript">
	
		// Example starter JavaScript for disabling form submissions if there are invalid fields
		(function () {
	  	'use strict'

	  	// Fetch all the forms we want to apply custom Bootstrap validation styles to
	  	var forms = document.querySelectorAll('.needs-validation')

	  	// Loop over them and prevent submission
	  	Array.prototype.slice.call(forms)
	    	.forEach(function (form) {
	      		form.addEventListener('submit', function (event) {
	        		if (!form.checkValidity()) {
	          			event.preventDefault()
	          			event.stopPropagation()
	        		}

	        		form.classList.add('was-validated')
	      		}, false)
	    	})
		})()
		
	</script>

</body>
</html>