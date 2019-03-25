<%@ page import="java.util.List, com.eddie.ecommerce.model.*" %>
<%@include file="/html/common/header.jsp"%>
<section class="biblioteca">
		<% 
			List<Juego> resultados = (List<Juego>) request.getAttribute(AttributeNames.LISTADO_RESULTADOS_BIBLIOTECA);
		
		if (resultados!=null && !resultados.isEmpty()) {
			%>
			<h1>Resultados:</h1>
			<%
			for (Juego resultado: resultados) {
				%>
					<div>
					<a href="<%=ControllerPaths.PRODUCTO%>?
							<%=ParameterNames.ACTION%>=<%=Actions.JUEGO%>&amp;<%=ParameterNames.ID%>=
							<%=resultado.getIdJuego()%>">
					<img src="<%=request.getContextPath()%>/imgs/icojuego/<%=(Integer)resultado.getIdJuego()%>.jpg"></img>
					<p><%=resultado.getNombre()%></p>
					</a>
					</div>
					<a href="<%=ControllerPaths.BIBLIOTECA%>?
							<%=ParameterNames.ACTION%>=<%=Actions.DELETEJUEGO%>&amp;<%=ParameterNames.ID%>=
							<%=resultado.getIdJuego()%>"><button>Eliminar</button></a>
				<%
					}
			}
			%><%
		%>
</section>
<%@include file="/html/common/footer.jsp"%>