<%@include file="/html/common/header.jsp"%>
<section>
	<form action="<%=ControllerPaths.USUARIO%>" method="post">
	<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.CHANGEPASS%>"/>
	<label><fmt:message key="cambiarpass" bundle="${traduccion}"/></label><br>
	<input type="text" name="<%=ParameterNames.PASSWORD%>" 
				value="<%=ParamsUtils.getParameter(request, ParameterNames.PASSWORD)%>" required 
				minlength="8" maxlength="75"/><br>
	<input type="submit" value="Enviar"/>
	</form>
</section>
<%@include file="/html/common/footer.jsp"%>