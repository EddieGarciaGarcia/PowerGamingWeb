<%@ page import="java.util.List, com.eddie.ecommerce.model.*" %>
<%@include file="/html/common/header.jsp"%>
<section>
	<%if(u==null){ %>
		<form action="<%=ControllerPaths.USUARIO%>" method="post">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.MENSAGE%>"/>
			<p>Por favor indique su email para poder devolverle la respuesta</p>
			<input type="email" name="<%=ParameterNames.EMAIL%>" 
				placeholder="usuario@ejemplo.com"
				value="<%=ParamsUtils.getParameter(request, ParameterNames.EMAIL)%>" required 
				minlength="8" maxlength="75"/>
			<p>Escriba aqui su error o lo que quiera decirnos:</p>
			<textarea name="<%=ParameterNames.MENSAGE %>" rows="10" cols="50"></textarea><br>
			<input type="submit" value="Enviar" />
		</form>
	<%}if(u!=null){ %>
		<form action="<%=ControllerPaths.USUARIO%>" method="post">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.MENSAGE%>"/>
			<p>Su respuesta sera enviada a su email: <%=u.getNombreUser()%><br>
			Escriba aqui su error o lo que quiera decirnos:</p>
			<textarea name="<%=ParameterNames.MENSAGE %>" rows="10" cols="50"></textarea><br>
			<input type="submit" value="Enviar" />
		</form>
	<%}%>
</section>
<%@include file="/html/common/footer.jsp"%>