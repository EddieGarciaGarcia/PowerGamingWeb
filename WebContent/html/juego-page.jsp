<%@ page import="com.eddie.ecommerce.service.*, com.eddie.web.controller.*, java.util.List, com.eddie.ecommerce.model.*,java.util.Map, java.io.IOException,java.io.*" %>

<%@include file="/html/common/header.jsp"%>


	<section class="sjuego">
		<% 
			
			Juego resultados =(Juego) request.getAttribute(AttributeNames.PRODUCTO_RESULTADOS);
			Map<Usuario, ItemBiblioteca> comentarios= (Map<Usuario, ItemBiblioteca>) request.getAttribute(AttributeNames.COMENTARIOS_JUEGO);
			Boolean resultadosBiblioteca = (Boolean)request.getAttribute(AttributeNames.PRODUCTOS_EN_BIBLIOTECA);		
			Creador creador=(Creador)request.getAttribute(AttributeNames.CREADOR_JUEGO);
			List<Edicion> edicionesJuegos = (List<Edicion>) request.getAttribute(AttributeNames.EDICIONES_JUEGO);
			
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
		<div><video src="<%=request.getContextPath()%>/imgs/icojuego/<%=resultados.getIdJuego()%>.mp4" width="640" height="480" controls></video></div>	<% 
		if(u!=null){
			%>
			<div class="comentario">
				<p>Tu comentario <%=u.getNombreUser()%> :</p>
				<textarea rows="10" cols="50" name=""></textarea>
				<a href="">Modificar</a>			
			</div>
			<%
		}else{
			
		}
		%>
			
	</section>
	
<%@include file="/html/common/footer.jsp"%>