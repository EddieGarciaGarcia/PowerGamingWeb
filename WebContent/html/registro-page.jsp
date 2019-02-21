<%@ page import="java.util.List" %>
<%@ page import="com.eddie.ecommerce.model.*" %>
<%@include file="/html/common/header.jsp"%>

<section>
	<%@include file="/html/common/action-error.jsp"%>
	<h2>Registro de Usuario</h2>
	<form action="<%=ControllerPaths.USUARIO%>" method="post">
	<%
				List<String> parameterErrors = errors.getErrors(ParameterNames.ACTION);
				for (String error: parameterErrors) {
					%><li><%=error%></li><%
				}
			%>
	<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.REGISTRO%>"/>
		<label>Nombre: </label><input type="text" name="<%=ParameterNames.NOMBRE%>"/>
		<label>Apellido 1: </label><input type="text" name="<%=ParameterNames.APELLIDO1%>"/>
		<label>Apellido 2: </label><input type="text" name="<%=ParameterNames.APELLIDO2%>"/><br>
		<label>Email: </label><input type="email" name="<%=ParameterNames.EMAIL%>" 
				placeholder="usuario@ejemplo.com"
				value="<%=ParamsUtils.getParameter(request, ParameterNames.EMAIL)%>" /><br>
				<%
					parameterErrors = errors.getErrors(ParameterNames.EMAIL);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
				%>
		<label>Telefono: </label><input type="text" name="<%=ParameterNames.TELEFONO%>"/><br>
		<label>Password: </label><input type="password" name="<%=ParameterNames.PASSWORD%>"/><br>
		<label>Fecha Nacimiento: </label><input type="date" name="<%=ParameterNames.FECHANACIMIENTO%>"/><br>
		<label>Genero: </label><select type="text" name="<%=ParameterNames.GENERO%>">
			<option value="H">Hombre</option>
			<option value="M">Mujer</option>
			<option value="O">Otro</option>
		</select><br>
		
		<label>Pais:</label><select name="<%=ParameterNames.PAIS%>">
				<%
					List<Pais> paises= (List<Pais>) request.getAttribute(AttributeNames.PAISES);
					for(Pais pais: paises){
						%>
						<option value="<%=pais.getIdPais()%>"><%=pais.getNombre()%></option>
						<%
					}
				%>
		</select><br>
		
		<input type="submit" value="Registrar"/>	
	</form>
</section>

<%@include file="/html/common/footer.jsp"%>