<%@ page import="com.eddie.ecommerce.service.*, com.eddie.web.controller.*,com.eddie.web.util.*, com.eddie.ecommerce.model.Usuario" %>



	<%
		Usuario u = (Usuario) request.getSession().getAttribute(SessionAttributeNames.USER);
	
		if (u == null) {
		
			%><li><a href="<%=request.getContextPath()%><%=ViewPaths.LOGIN%>"><fmt:message key="entrar" bundle="${traduccion}"></fmt:message></a></li><%	
		
		} else {
			%>	
			<!-- usuario autenticado -->
			<li> 
				
				<a onclick="desplegarMenu()" class="dropbtn"><%=u.getNombre()%></a>
				<div id="meumenudes" class="menudes-contido"> 
	                <a href="<%=ControllerPaths.USUARIO%>?<%=ParameterNames.ACTION%>=<%=Actions.PRECONFIGURACION%>"><fmt:message key="micuenta" bundle="${traduccion}"></fmt:message></a>
	               	<a href="<%=ControllerPaths.BIBLIOTECA%>?<%=ParameterNames.ACTION%>=<%=Actions.BIBLIOTECA%>"><fmt:message key="biblioteca" bundle="${traduccion}"></fmt:message></a>
	                <a href="<%=ControllerPaths.HISTORIAL%>?<%=ParameterNames.ACTION%>=<%=Actions.HISTORIALPEDIDO%>"><fmt:message key="historialpedidos" bundle="${traduccion}"></fmt:message></a>

	             </div>

			</li>
	
			<li><a href="<%=ControllerPaths.USUARIO%>?<%=ParameterNames.ACTION%>=<%=Actions.LOGOUT%>"><fmt:message key="salir" bundle="${traduccion}"></fmt:message></a></li>
				
			<%
		}
	%>	
