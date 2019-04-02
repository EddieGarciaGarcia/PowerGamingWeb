<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.eddie.web.util.*, com.eddie.web.model.*, com.eddie.web.controller.* "%>
<div id="carrito">
			

<a onclick="desplegarMenu2()" class="dropbtn">Carrito</a>
<div id="meumenudes2" class="menudes-contido">
<%
	Carrito carrito = (Carrito) SessionManager.get(request, SessionAttributeNames.CARRITO);
	
	if (carrito != null) {
			
			for (LineaCarrito lineaCarrito : carrito.getLinea()){
			
%>
			<div class="lineacarrito">
				<img src="<%=request.getContextPath()%>/imgs/icojuego/<%=lineaCarrito.getEdicion().getIdJuego()%>.jpg"></img>
				<p class="precio"><%=lineaCarrito.getEdicion().getPrecio()%></p>
				<a href="<%=ControllerPaths.CARRITO %>?<%=ParameterNames.ACTION%>=<%=Actions.ELIMINAR%>&amp;<%=ParameterNames.IDEDICION%>">X</a>
				
				<hr>
					
			</div>
			
<% 
				
			}
			%>
			<div>
				<p><%=carrito.getTotal()%></p>
			</div>
			<% 
	}
%>	
</div>
</div>
