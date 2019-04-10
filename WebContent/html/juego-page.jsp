<%@ page import="com.eddie.ecommerce.service.*, com.eddie.web.controller.*, java.util.List, com.eddie.ecommerce.model.*,java.util.Map, java.io.IOException,java.io.*" %>

<%@include file="/html/common/header.jsp"%>


	<section class="sjuego">
		<% 
			
			Juego resultados =(Juego) request.getAttribute(AttributeNames.PRODUCTO_RESULTADOS);
			Map<String, ItemBiblioteca> comentarios= (Map<String, ItemBiblioteca>) request.getAttribute(AttributeNames.COMENTARIOS_JUEGO);
			Boolean resultadosBiblioteca = (Boolean)request.getAttribute(AttributeNames.PRODUCTOS_EN_BIBLIOTECA);		
			Creador creador=(Creador)request.getAttribute(AttributeNames.CREADOR_JUEGO);
			
			
		%>
		<img src="<%=request.getContextPath()%>/imgs/icojuego/<%=resultados.getIdJuego()%>.jpg"></img>
		<h1><%=resultados.getNombre()%></h1>
		<p>Fecha de Lanzamiento : <%=resultados.getFechaLanzamiento() %><br>
		Creador : <%=creador.getNombre()%><br>
		Categorias : <%for(Categoria c: resultados.getCategoria()){%><%=c.getNombre()%> <%}%><br>
		Plataformas disponibles: <%for(Plataforma p: resultados.getPlataformas()){%><%=p.getNombre()%> <%}%><br>
		Idiomas disponibles : <%for(Idioma i: resultados.getIdiomas()){%><%=i.getNombre()%> <%}%>
		</p>
		
		<% if(u!=null){	
						if(resultadosBiblioteca==true){
							%>
								<p class="a">Ya esta Añadido</p>
							<% 
						}else if(resultadosBiblioteca==false){
							%>
								<a class="a" href="<%=ControllerPaths.BIBLIOTECA%>?
									<%=ParameterNames.ACTION%>=<%=Actions.ADDJUEGO%>&amp;<%=ParameterNames.ID%>=
									<%=resultados.getIdJuego()%>"><button>Añadir a la Biblioteca</button></a>
							<% 
								
						}
						%>
						
						
		<%}%>
		
		<div class="addCarrito">
						<form action="<%=ControllerPaths.CARRITO%>" method="post">
						<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.ANHADIR%>"/>
						<select name="<%=ParameterNames.IDEDICION%>">
						<%if(resultados.getEdiciones()!=null){for(Edicion e: resultados.getEdiciones()){  %>
						<option value="<%=e.getId()%>"><fmt:message key="formato" bundle="${traduccion}"></fmt:message><%for(Formato f:formato){
											if(f.getIdFormato().equals(e.getIdFormato())){
												%><%=f.getNombre()%>
											<%}
											}%> 
						<fmt:message key="tipoedicion" bundle="${traduccion}"></fmt:message><%for(TipoEdicion te:tipoEdicion){
											if(te.getIdTipoEdicion().equals(e.getIdTipoEdicion())){
												%><%=te.getNombre()%>
											<%}}%> Precio: <%=e.getPrecio() %></option><br>
		
						<%}}%>
						</select>
						<input type="submit" value="Añadir a Carrito"/>
						</form>
</div>
		
		
		<div><video src="<%=request.getContextPath()%>/imgs/icojuego/<%=resultados.getIdJuego()%>.mp4" width="640" height="480" controls></video></div>	
		<% if(u!=null){%>
			<div class="comentario">
			<form action="<%=ControllerPaths.JUEGO%>" method="post">
				<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.ADDCOMENTARIO%>"/>
				<p>Tu comentario <%=u.getNombreUser()%> :</p>
				<textarea rows="10" cols="50" name="<%=ParameterNames.COMENTARIO%>"></textarea>
				<input type="submit" name="Modificar" value="modificar"/>	
			</form>		
			</div>
			<%
		}
		if(!comentarios.isEmpty() && comentarios!=null){%>
			<div class="comentarios">
				<%for (Map.Entry<String, ItemBiblioteca> entry : comentarios.entrySet()) {%>
				   	<div>
				   		<%if(entry.getValue().getComentario()!=null && entry.getValue().getIdJuego()!=null){%>
					    	<h3><%=entry.getKey()%></h3><br>
					    	<p><%=entry.getValue().getFechaComentario()%></p>
					    	<p><%=entry.getValue().getComentario()%></p>
				    	<%}%>
				    </div>
				<%}%>
			</div>
		<%}%>
			
	</section>
	
<%@include file="/html/common/footer.jsp"%>