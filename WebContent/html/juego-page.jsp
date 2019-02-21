<%@ page import="java.util.List, com.eddie.ecommerce.model.*" %>

<%@include file="/html/common/header.jsp"%>


	<section class="sjuego">
		<% 
			Juego j =(Juego) request.getAttribute(AttributeNames.PRODUCTO_RESULTADOS);
		
		%>
		<img src="<%=request.getContextPath()%>/imgs/icojuego/<%=j.getIdJuego()%>.jpg"></img>
		<h1><%=j.getNombre()%></h1>
		<p><%=j.getFechaLanzamiento() %></p>
		
		
		
	
	</section>
	
<%@include file="/html/common/footer.jsp"%>