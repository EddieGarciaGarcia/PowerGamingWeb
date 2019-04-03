<%@ page import="com.eddie.ecommerce.service.*, com.eddie.web.controller.*,com.eddie.web.util.*, com.eddie.ecommerce.model.Usuario" %>

	<%
		Usuario u = (Usuario) request.getSession().getAttribute(SessionAttributeNames.USER);
		if (u == null) {
		
			%><li><a href="<%=request.getContextPath()%><%=ViewPaths.LOGIN%>">Entrar</a></li><%	
		
		} else {
			%>	
			<!-- usuario autenticado -->
			<li> 
				
				<a onclick="desplegarMenu()" class="dropbtn"><%=u.getNombre()%></a>
				<div id="meumenudes" class="menudes-contido"> 
	                <a href="<%=ControllerPaths.USUARIO%>?<%=ParameterNames.ACTION%>=<%=Actions.PRECONFIGURACION%>">Mi Cuenta</a>
	               	<a href="<%=ControllerPaths.BIBLIOTECA%>?<%=ParameterNames.ACTION%>=<%=Actions.BIBLIOTECA%>">Biblioteca</a>
	                <a href="<%=ControllerPaths.HISTORIAL%>?<%=ParameterNames.ACTION%>=<%=Actions.HISTORIALPEDIDO%>">Historial de Pedidos</a>

	             </div>

			</li>
	
			<li><a href="<%=ControllerPaths.USUARIO%>?<%=ParameterNames.ACTION%>=<%=Actions.LOGOUT%>">Salir</a></li>
				
			<%
		}
	%>	
