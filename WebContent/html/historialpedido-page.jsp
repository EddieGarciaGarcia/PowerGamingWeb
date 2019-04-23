<%@include file="/html/common/header.jsp"%>
<section>
	<h1><fmt:message key="biblioteca" bundle="${traduccion}">Historial</fmt:message></h1>
		<% 
			List<Pedido> resultados = (List<Pedido>) request.getAttribute(AttributeNames.LISTADO_PEDIDOS);
		
		if (resultados!=null && !resultados.isEmpty()) {
			
			for (Pedido resultado: resultados) {
				%>
				<div class="divjuegobiblioteca">
					<p>Numero de Pedido: <%=resultado.getIdPedido()%></p>
					<p>Fecha de Pedido: <%=resultado.getFecha_pedido()%></p>
					<p>Pecio Tota: <%=resultado.getTotal()%></p>
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