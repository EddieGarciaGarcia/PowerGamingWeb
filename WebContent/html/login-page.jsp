<%@ page import="java.util.List" %>

<%@include file="/html/common/header.jsp"%>
<div id="login-form">
		<h3><fmt:message key="welcome" bundle="${traduccion}"/>:</h3>	
		<form action="<%=ControllerPaths.USUARIO%>" method="post">
			
		<%@include file="/html/common/action-error.jsp"%>
			
			<%
				List<String> parameterErrors = errors.getErrors(ParameterNames.ACTION);
				for (String error: parameterErrors) {
					%><li><%=error%></li><%
				}
			%>
		
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.LOGIN%>"/>
			<label><fmt:message key="loginmensage" bundle="${traduccion}"/>:</label>
			<input type="email" name="<%=ParameterNames.EMAIL%>" 
				placeholder="usuario@ejemplo.com"
				value="<%=ParamsUtils.getParameter(request, ParameterNames.EMAIL)%>" required 
				minlength="8" maxlength="75"/>
				<%
					parameterErrors = errors.getErrors(ParameterNames.EMAIL);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
				%>	
			<label><fmt:message key="loginmensage2" bundle="${traduccion}"/>:</label>		
			<input type="password" name="<%=ParameterNames.PASSWORD%>" required minlength="8" maxlength="30"/>
			<input type="submit" value="Entrar" />
		</form>
		<a href="<%=request.getContextPath()%><%=ViewPaths.FORGETPASS%>"><fmt:message key="forgotpass" bundle="${traduccion}"/></a>
		<h3><fmt:message key="forgetpassmensage2" bundle="${traduccion}"/></h3>
		<a href="<%=ControllerPaths.USUARIO%>?<%=ParameterNames.ACTION%>=
				<%=Actions.PREREGISTRO%>"><fmt:message key="registrarse" bundle="${traduccion}"/></a>
</div>

<%@include file="/html/common/footer.jsp"%>
