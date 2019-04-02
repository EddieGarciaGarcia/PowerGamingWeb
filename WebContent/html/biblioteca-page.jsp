<%@ page import="java.util.List, com.eddie.ecommerce.model.*" %>
<%@include file="/html/common/header.jsp"%>
<section class="biblioteca">
		<% 
			List<Juego> resultados = (List<Juego>) request.getAttribute(AttributeNames.LISTADO_RESULTADOS_BIBLIOTECA);
			
			List<Formato> formato= (List<Formato>) request.getAttribute(AttributeNames.FORMATO_RESULTADOS);
			List<TipoEdicion> tipoEdicion= (List<TipoEdicion>) request.getAttribute(AttributeNames.TIPOEDICION_RESULTADOS);
			
			
			
		if (resultados!=null && !resultados.isEmpty()) {
			%>
			<h1>Resultados:</h1>
			<%
			for (Juego resultado: resultados) {
				%>
					<div>
					<a href="<%=ControllerPaths.JUEGO%>?
							<%=ParameterNames.ACTION%>=<%=Actions.JUEGO%>&amp;<%=ParameterNames.ID%>=
							<%=resultado.getIdJuego()%>">
					<img src="<%=request.getContextPath()%>/imgs/icojuego/<%=(Integer)resultado.getIdJuego()%>.jpg"></img>
					<p><%=resultado.getNombre()%></p>
					</a>
					</div>
					<a href="<%=ControllerPaths.BIBLIOTECA%>?
							<%=ParameterNames.ACTION%>=<%=Actions.DELETEJUEGO%>&amp;<%=ParameterNames.ID%>=
							<%=resultado.getIdJuego()%>"><button>Eliminar</button></a>
					<div class="addCarrito">
						<form action="<%=ControllerPaths.CARRITO%>" method="post">
						<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.ANHADIR%>"/>
						<select name="<%=ParameterNames.IDEDICION%>">
						<%for(Edicion e: resultado.getEdiciones()){  %>
						<input type="checkbox" value="<%=e.getId()%>" name="<%=ParameterNames.IDEDICION%>">
						<option value="<%=e.getId()%>">Formato: <%for(Formato f:formato){
											if(f.getIdFormato().equals(e.getIdFormato())){
												%><%=f.getNombre()%>
											<%}
											}%> 
								Tipo Edicion: <%for(TipoEdicion te:tipoEdicion){
											if(te.getIdTipoEdicion().equals(e.getIdTipoEdicion())){
												%><%=te.getNombre()%>
											<%}}%> Precio: <%=e.getPrecio() %></option><br>
		
						<%}%>
						</select>
						<input type="submit" value="Añadir a Carrito"/>
						</form>
				</div>
				<%
					}
			}
			%><%
		%>
</section>
<%@include file="/html/common/footer.jsp"%>