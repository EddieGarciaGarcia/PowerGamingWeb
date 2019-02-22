<%@ page import="java.util.List" %>
<%@ page import="com.eddie.ecommerce.model.*, com.eddie.web.controller.*" %>
<%@include file="/html/common/header.jsp"%>
<section>
<%@include file="/html/common/action-error.jsp"%>
	<h2>Configuracion de Usuario</h2>
	<form action="<%=ControllerPaths.USUARIO%>" method="post">
	<%
				List<String> parameterErrors = errors.getErrors(ParameterNames.ACTION);
				for (String error: parameterErrors) {
					%><li><%=error%></li><%
				}
			%>
	<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.CONFIGURACION%>"/>
	<% 
		Usuario user=(Usuario)request.getAttribute(AttributeNames.USER);
	%>
	
		<label>Nombre:</label>
		<label><%=user.getNombre()%> , Cambiar a: </label>
		<input type="text" name="<%=ParameterNames.NOMBRE%>" /><br>
		<label>Apellido 1: </label>
		<label><%=user.getApellido1()%> , Cambiar a: </label>
		<input type="text" name="<%=ParameterNames.APELLIDO1%>"/><br>
		<label>Apellido 2: </label>
		<label><%=user.getApellido2()%> , Cambiar a: </label>
		<input type="text" name="<%=ParameterNames.APELLIDO2%>"/><br>
				<%
					parameterErrors = errors.getErrors(ParameterNames.EMAIL);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
				%>
		<label>Telefono: </label>
		<label><%=user.getTelefono()%> , Cambiar a: </label>
		<input type="text" name="<%=ParameterNames.TELEFONO%>" /><br>
		<label>Password cambiar a:  </label>
		<input type="password" name="<%=ParameterNames.PASSWORD%>" /><br>
		<label>Nombre Usuario: </label>
		<label><%=user.getNombreUser()%> , Cambiar a: </label>
		<input type="text" name="<%=ParameterNames.NOMBREUSER%>" /><br>
		
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
		
		<input type="submit" value="Actualizar"/>	
	</form>
</section>

</section>
<%@include file="/html/common/footer.jsp"%>