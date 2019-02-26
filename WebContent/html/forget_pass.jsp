<%@include file="/html/common/header.jsp"%>
<section>
	<form action="<%=ControllerPaths.USUARIO%>" method="post">
	<label>Ingresa tu email para poder cambiar la contraseña:</label><br>
	<input type="email" name="<%=ParameterNames.EMAIL%>" 
				placeholder="usuario@ejemplo.com"
				value="<%=ParamsUtils.getParameter(request, ParameterNames.EMAIL)%>" required 
				minlength="8" maxlength="75"/><br>
	<input type="submit" value="Enviar"/>
	</form>
	<h3>¿No tienes cuenta?</h3>
		<a href="<%=ControllerPaths.USUARIO%>?<%=ParameterNames.ACTION%>=
				<%=Actions.PREREGISTRO%>">Regístrate</a>
</section>
<%@include file="/html/common/footer.jsp"%>