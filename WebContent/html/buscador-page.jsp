<%@ page import="java.util.List, com.eddie.ecommerce.model.*" %>

<%@include file="/html/common/header.jsp"%>
<%@include file="/html/common/buscador.jsp"%>
<section class="sectionjuegos">
<%		
		List<Juego> resultados = (List<Juego>) request.getAttribute(AttributeNames.PRODUCTO_RESULTADOS);	

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
						<img src="<%=request.getContextPath()%>/imgs/icojuego/<%=(Integer)resultado.getIdJuego()%>.jpg"></img><p><%=resultado.getNombre()%></p>
					</a>
					</div>
				<%
					}
			}
			%><%
		
%>
</section>
<%@include file="/html/common/footer.jsp"%>
