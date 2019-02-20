<%@ page import="java.util.List" %>
<%@ page import="com.eddie.ecommerce.model.*" %>
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
	//Usuario user=(Usuario)request.getAttribute(AttributeNames.USER);
	%>
	
		<label>Nombre: </label><input type="text" name="<%=ParameterNames.NOMBRE%>"/>
		<label>Apellido 1: </label><input type="text" name="<%=ParameterNames.APELLIDO1%>"/>
		<label>Apellido 2: </label><input type="text" name="<%=ParameterNames.APELLIDO2%>"/><br>
				<%
					parameterErrors = errors.getErrors(ParameterNames.EMAIL);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
				%>
		<label>Telefono: </label><input type="text" name="<%=ParameterNames.TELEFONO%>"/><br>
		<label>Password: </label><input type="password" name="<%=ParameterNames.PASSWORD%>"/><br>
		<label>Nombre Usuario: </label><input type="text" name="<%=ParameterNames.NOMBREUSER%>"/><br>
		
		<input type="submit" value="Actualizar"/>	
	</form>
</section>

</section>
<%@include file="/html/common/footer.jsp"%>