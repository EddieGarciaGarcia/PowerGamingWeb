<%@ page import="com.eddie.ecommerce.service.*, com.eddie.web.controller.*,java.util.List, com.eddie.ecommerce.model.*" %>
<%@include file="/html/common/header.jsp"%>
<%@include file="/html/common/buscador.jsp"%>

<h1><fmt:message key="resultados" bundle="${traduccion}"/></h1>
<section class="sectionjuegos">
	<%
		List<Juego> todos= (List<Juego>) request.getAttribute(AttributeNames.RESULTADOS_TODOS);
		List<Juego> valoracion= (List<Juego>) request.getAttribute(AttributeNames.RESULTADOS_TODOS_VALOR);
	%>
	
		<%		
		if (todos!=null && !todos.isEmpty()) {
			%>
			<h2>Juegos mas recientes:</h2>
			<%
			for (Juego resultado: todos) {
				%>
					<div>
					<a class="a2" href="<%=ControllerPaths.JUEGO%>?
							<%=ParameterNames.ACTION%>=<%=Actions.JUEGO%>&amp;<%=ParameterNames.ID%>=
							<%=resultado.getIdJuego()%>">
						<img src="<%=request.getContextPath()%>/imgs/icojuego/<%=(Integer)resultado.getIdJuego()%>.jpg"></img><p class="pjuego"><%=resultado.getNombre()%></p>
					</a>
					
					</div>
					<%}%>
					
					
			<%}%>
	</section>
	<aside class="sectionjuegos">
		<%		
		if (todos!=null && !todos.isEmpty()) {
			%>
			<h2>Juegos mejor valorados:</h2>
			<%
			for (Juego valor: valoracion) {
				%>
					<div>
					<a class="a2" href="<%=ControllerPaths.JUEGO%>?
							<%=ParameterNames.ACTION%>=<%=Actions.JUEGO%>&amp;<%=ParameterNames.ID%>=
							<%=valor.getIdJuego()%>">
						<img src="<%=request.getContextPath()%>/imgs/icojuego/<%=(Integer)valor.getIdJuego()%>.jpg"></img><p class="pjuego"><%=valor.getNombre()%></p>
					</a>
					
					</div>
					<%}%>
			<%}%>
	</aside>
</section>
<%@include file="/html/common/footer.jsp"%>

