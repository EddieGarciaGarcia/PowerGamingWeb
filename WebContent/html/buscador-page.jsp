<%@ page import="com.eddie.ecommerce.service.*, com.eddie.web.controller.*,java.util.List, com.eddie.ecommerce.model.*" %>

<%@include file="/html/common/header.jsp"%>
<%@include file="/html/common/buscador.jsp"%>
<section class="sectionjuegos">
<%		
		
		List<Juego> resultados = (List<Juego>) request.getAttribute(AttributeNames.PRODUCTO_RESULTADOS);	
	   
		List<Integer> idsJuegosEnBiblioteca = (List<Integer>) request.getAttribute(AttributeNames.PRODUCTOS_EN_BIBLIOTECA);	

		if (resultados!=null && !resultados.isEmpty()) {
			%>
			<h1>Resultados:</h1>
			<%
			for (Juego resultado: resultados) {
				%>
					<div>
					<a class="a2" href="<%=ControllerPaths.JUEGO%>?
							<%=ParameterNames.ACTION%>=<%=Actions.JUEGO%>&amp;<%=ParameterNames.ID%>=
							<%=resultado.getIdJuego()%>">
						<img src="<%=request.getContextPath()%>/imgs/icojuego/<%=(Integer)resultado.getIdJuego()%>.jpg"></img><p class="pjuego"><%=resultado.getNombre()%></p>
					</a>
					
					<% if(u!=null){ 
						boolean mostrar=idsJuegosEnBiblioteca.contains(resultado.getIdJuego());
						    if (mostrar==true){
								%>
									<p class="a">Ya esta Añadido</p>
								<% 
							}else{
								%>
									<a class="a" href="<%=ControllerPaths.BIBLIOTECA%>?
										<%=ParameterNames.ACTION%>=<%=Actions.ADDJUEGO%>&amp;<%=ParameterNames.ID%>=
										<%=resultado.getIdJuego()%>"><button>Añadir a la Biblioteca</button></a>
								<% 
									
							}
							%>
					
					<%}%>
					
					</div>
					<div class="addCarrito">
						<form action="<%=ControllerPaths.CARRITO%>" method="post">
						<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.ANHADIR%>"/>
						<select name="<%=ParameterNames.IDEDICION%>">
						<%for(Edicion e: resultado.getEdiciones()){  %>
						
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
			}else{
				%><h2>No se han encontrado resultados</h2><%
			}
			%>
</section>
<%@include file="/html/common/footer.jsp"%>
