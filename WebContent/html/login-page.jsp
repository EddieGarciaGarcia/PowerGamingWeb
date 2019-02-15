<%@ page import="java.util.List" %>

<%@include file="/html/common/header.jsp"%>
<div id="login-form">
		<h3>Bienvenido:</h3>	
		<form action="<%=ControllerPaths.USUARIO%>" method="post">
			
		<%@include file="/html/common/action-error.jsp"%>
			
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.LOGIN%>"/>
			<input type="email" name="<%=ParameterNames.EMAIL%>" 
				placeholder="usuario@ejemplo.com"
				value="<%=ParamsUtils.getParameter(request, ParameterNames.EMAIL)%>" />
				<%
					parameterErrors = errors.getErrors(ParameterNames.EMAIL);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
				%>			
			<input type="password" name="<%=ParameterNames.PASSWORD%>"/>
			<input type="submit" value="Entrar" />
		</form>
		<h3>¿No tienes cuenta?</h3>
		<a href="<%=request.getContextPath()%>?action=<%=Actions.PREREGISTRO%>">Regístrate</a>
</div>

<%@include file="/html/common/footer.jsp"%>
