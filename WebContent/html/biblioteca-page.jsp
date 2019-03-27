<%@ page import="java.util.List, com.eddie.ecommerce.model.*" %>
<%@include file="/html/common/header.jsp"%>
<section class="biblioteca">
		<% 
			List<Juego> resultados = (List<Juego>) request.getAttribute(AttributeNames.LISTADO_RESULTADOS_BIBLIOTECA);
			List<Edicion> edicionesJuegos = (List<Edicion>) request.getAttribute(AttributeNames.EDICIONES_JUEGO);
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
						<form>
							<select>
							<%for(Formato f : formato){
								%>
								<option><%=f.getNombre()%></option>
								<%
								}
							%>
							</select>
							<select>
							<%for(TipoEdicion tipoE : tipoEdicion){
								%>
								<option><%=tipoE.getNombre()%></option>
								<%
							}%>
							</select>
							<label>Precio:</label>
							<input type="button" value="Añadir a Carrito"/>
						</form>
					</div>
				<%
					}
			}
			%><%
		%>
</section>
<%@include file="/html/common/footer.jsp"%>