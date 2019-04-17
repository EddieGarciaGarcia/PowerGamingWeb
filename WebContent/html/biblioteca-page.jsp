<%@ page import="java.util.List, com.eddie.ecommerce.model.*" %>
<%@include file="/html/common/header.jsp"%>
<section class="biblioteca">
	<h1><fmt:message key="biblioteca" bundle="${traduccion}"></fmt:message></h1>
		<% 
			List<Juego> resultados = (List<Juego>) request.getAttribute(AttributeNames.LISTADO_RESULTADOS_BIBLIOTECA);
		
		if (resultados!=null && !resultados.isEmpty()) {
			
			for (Juego resultado: resultados) {
				%>
					<div class="divjuegobiblioteca">
					<a href="<%=ControllerPaths.JUEGO%>?
							<%=ParameterNames.ACTION%>=<%=Actions.JUEGO%>&amp;<%=ParameterNames.ID%>=
							<%=resultado.getIdJuego()%>">
					<img src="<%=request.getContextPath()%>/imgs/icojuego/<%=(Integer)resultado.getIdJuego()%>.jpg"></img>
					</a>
					</div>
					<div class="divbiblioteca">
					<p><%=resultado.getNombre()%></p>
					<a href="<%=ControllerPaths.BIBLIOTECA%>?
							<%=ParameterNames.ACTION%>=<%=Actions.DELETEJUEGO%>&amp;<%=ParameterNames.ID%>=
							<%=resultado.getIdJuego()%>"><button><fmt:message key="eliminar" bundle="${traduccion}"></fmt:message></button></a>
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
											<%}}%> <fmt:message key="precio" bundle="${traduccion}"></fmt:message> <%=e.getPrecio() %></option><br>
		
						<%}%>
						</select>
						<input type="submit" value="<fmt:message key="addcarrito" bundle="${traduccion}"></fmt:message>"/>
						</form>
					</div>
				</div>
				<%
					}
			%>
		
		<!-- Paginacion  -->
		
						<p><center>
						<c:url var="urlBase" value="/biblioteca" scope="page">
							<c:param name="action" value="biblioteca"/>
						</c:url>
					
						<!-- A la anterior pagina -->
						<c:if test="${page > 1}">
							<a href="${urlBase}&page=${page - 1}">
								<fmt:message key="anterior" bundle="${traduccion}"/>
							</a>
							&nbsp;&nbsp;
						</c:if>
					
						<c:if test="${totalPages > 1}">	
					
							<c:if test="${firstPagedPage > 2}">
								<a href="${urlBase}&page=1"><b>1</b></a><b>&nbsp;.&nbsp;.&nbsp;.&nbsp;</b> 
							</c:if>
						
							<c:forEach begin="${firstPagedPage}" end="${lastPagedPage}" var="i">
								<c:choose>
								  <c:when test="${page != i}">
										&nbsp;<a href="${urlBase}&page=${i}"><b>${i}</b></a>&nbsp;
								  </c:when>
								  <c:otherwise>
										&nbsp;<b>${i}</b>&nbsp;
								  </c:otherwise>
								</c:choose>
							</c:forEach>
					
							<c:if test="${lastPagedPage < totalPages-1}">
								<b>&nbsp;.&nbsp;.&nbsp;.&nbsp;</b><a href="${urlBase}&page=${totalPages}"><b>${totalPages}</b></a>
							</c:if>	
					
						</c:if>
					
						<!-- A la siguiente página -->	
						<c:if test="${page < totalPages}">
							&nbsp;&nbsp;		
							<a href="${urlBase}&page=${page + 1}">
								<fmt:message key="siguiente" bundle="${traduccion}"/>
							</a>			
						</c:if>	
					
						</center></p>
		
		<%}
	%>		
</section>
<%@include file="/html/common/footer.jsp"%>