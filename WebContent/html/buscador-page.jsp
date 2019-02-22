<%@ page import="com.eddie.ecommerce.service.*, com.eddie.web.controller.*,java.util.List, com.eddie.ecommerce.model.*" %>

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
					<a class="a2" href="<%=ControllerPaths.PRODUCTO%>?
							<%=ParameterNames.ACTION%>=<%=Actions.JUEGO%>&amp;<%=ParameterNames.ID%>=
							<%=resultado.getIdJuego()%>">
						<img src="<%=request.getContextPath()%>/imgs/icojuego/<%=(Integer)resultado.getIdJuego()%>.jpg"></img><p class="pjuego"><%=resultado.getNombre()%></p>
					</a>
					
					<% if(u!=null){
						Boolean mostrar=false;
						List<Juego> resultadosBiblioteca = (List<Juego>) request.getAttribute(AttributeNames.BIBLIOTECA_RESULTADOS);
						for(Juego j:resultadosBiblioteca){
							if(resultado.getIdJuego()==j.getIdJuego()){
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
									<%=resultado.getIdJuego()%>"><button>Añadir a la Biblioteca</button></a>
							<% 
								
						}
						%>
					
					<%}%>
					
					</div>
		
				<%
					}
			}
			%><%
		
%>
</section>
<%@include file="/html/common/footer.jsp"%>
