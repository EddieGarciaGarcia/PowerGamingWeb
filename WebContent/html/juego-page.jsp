<%@ page import="com.eddie.ecommerce.service.*, com.eddie.web.controller.*, java.util.List, com.eddie.ecommerce.model.*,java.util.Map" %>

<%@include file="/html/common/header.jsp"%>


	<section class="sjuego">
		<% 
			
			Juego resultados =(Juego) request.getAttribute(AttributeNames.PRODUCTO_RESULTADOS);
			Map<Usuario, ItemBiblioteca> comentarios= (Map<Usuario, ItemBiblioteca>) request.getAttribute(AttributeNames.COMENTARIOS_JUEGO);
			List<Juego> resultadosBiblioteca = (List<Juego>) request.getAttribute(AttributeNames.BIBLIOTECA_RESULTADOS);
			
			resultadosBiblioteca.forEach(j -> System.out.println(j.getNombre()));
			
			
		%>
		<img src="<%=request.getContextPath()%>/imgs/icojuego/<%=resultados.getIdJuego()%>.jpg"></img>
		<h1><%=resultados.getNombre()%></h1>
		<p><%=resultados.getFechaLanzamiento() %></p>
		
		<% if(u!=null){
						Boolean mostrar=false;
						for(Juego j:resultadosBiblioteca){
							if(resultados.getIdJuego()==j.getIdJuego()){
								mostrar=true;
							}
						}
						if(mostrar==true){
							%>
								<p class="a">Ya esta Añadido</p>
							<% 
						}else if(mostrar==false){
							%>
								<a class="a" href="<%=ControllerPaths.BIBLIOTECA%>?
									<%=ParameterNames.ACTION%>=<%=Actions.ADDJUEGO%>&amp;<%=ParameterNames.ID%>=
									<%=resultados.getIdJuego()%>"><button>Añadir a la Biblioteca</button></a>
							<% 
								
						}
						%>
						
		<%}
	   
	     
		
		%>
				
	</section>
	
<%@include file="/html/common/footer.jsp"%>