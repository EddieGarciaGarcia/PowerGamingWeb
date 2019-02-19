<%@ page import="com.eddie.ecommerce.service.*, com.eddie.web.util.*, com.eddie.ecommerce.model.Usuario" %>

	<%
		Usuario u = (Usuario) request.getSession().getAttribute(SessionAttributeNames.USER);
		if (u == null) {
		
			%><li><a href="<%=request.getContextPath()%><%=ViewPaths.LOGIN%>">Entrar</a></li><%	
		
		} else {
			%>	
			<!-- usuario autenticado -->
			<li><%=u.getNombre()%></li>
			<li><a href="<%=request.getContextPath()%>/usuario?action=logout">Salir</a></li>
				
			<%
		}
	%>	
