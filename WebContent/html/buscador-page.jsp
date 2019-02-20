<%@ page import="java.util.List, com.eddie.ecommerce.model.*" %>

<%@include file="/html/common/header.jsp"%>

<%		
		List<Juego> resultados = (List<Juego>) request.getAttribute(AttributeNames.PRESULTADOS);	

		if (resultados!=null && !resultados.isEmpty()) {
			%>
			<p>Resultados:</p>
			<ul><%
			for (Juego resultado: resultados) {
				%>
					<li>
					<img src="<%=request.getContextPath()%>/imgs/<%=(Integer)resultado.getIdJuego()%>.jpg"></img>
					<%=resultado.getNombre()%>
					</li>
				<%
					}
			}
			%></ul><%
		
%>
	
<%@include file="/html/common/footer.jsp"%>
