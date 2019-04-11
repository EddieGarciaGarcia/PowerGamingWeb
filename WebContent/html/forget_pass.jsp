<%@include file="/html/common/header.jsp"%>
<section>
	<form action="<%=ControllerPaths.USUARIO%>" method="post">
	<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.FORGOTPASS%>"/>
	<label><fmt:message key="forgetpassmensage" bundle="${traduccion}"/></label><br>
	<input type="email" name="<%=ParameterNames.EMAIL%>" 
				placeholder="usuario@ejemplo.com"
				value="<%=ParamsUtils.getParameter(request, ParameterNames.EMAIL)%>" required 
				minlength="8" maxlength="75"/><br>
	<input type="submit" value="Enviar"/>
	</form>
	<h3><fmt:message key="forgetpassmensage2" bundle="${traduccion}"/></h3>
		<a href="<%=ControllerPaths.USUARIO%>?<%=ParameterNames.ACTION%>=
				<%=Actions.PREREGISTRO%>"><fmt:message key="registrarse" bundle="${traduccion}"/></a>
</section>
<%@include file="/html/common/footer.jsp"%>