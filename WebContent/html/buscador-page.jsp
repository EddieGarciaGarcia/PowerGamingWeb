<%@ page import="com.eddie.ecommerce.service.*, com.eddie.web.controller.*,java.util.List, com.eddie.ecommerce.model.*" %>

<%@include file="/html/common/header.jsp"%>
<%@include file="/html/common/buscador.jsp"%>
<section class="sectionjuegos">


<c:set var="juego" value="${requestScope.juego}" />
<c:set var="juegobiblioteca" value="${requestScope.}"/>
<%		

		List<Juego> resultados = (List<Juego>) request.getAttribute(AttributeNames.PRODUCTO_RESULTADOS);	
	   
		List<Integer> idsJuegosEnBiblioteca = (List<Integer>) request.getAttribute(AttributeNames.PRODUCTOS_EN_BIBLIOTECA);	

			%>
			<p>
			<c:if test="${not empty total}">
				<fmt:message key="resultados" bundle="${messages}">
					<fmt:param value="${total}"></fmt:param>
				</fmt:message>
			</c:if>
			</p>
		<c:if test="${not empty resultados}">
			<c:foreach items="${resultados}" var="resultado">
				<div>
					<c:url var="urljuegodetalle" scope="page" value="juego">
						<c:param name="action" value="<%=Actions.JUEGO%>"/>
						<c:param name="id" value="${resultado.id}"/>
					</c:url>
				<a href="${urljuegodetalle}"><img src="${pageContext.request.contextPath}/img/icojuego/${resultado.id}"><p class="pjuego">${resultado.nombre}</p></a>
				</div>
				<c:when test="${not empty u}">
					<%boolean mostrar=idsJuegosEnBiblioteca.contains(resultado.getIdJuego());%>
					<c:when test="${mostrar==true}">
						<p class="a"><fmt:message key="anhadido" bundle="${messages}"></fmt:message></p>
					</c:when>
					<c:otherwise>
						<c:url var="urlbiblioteca" scope="page" value="juego">
							<c:param name="action" value="<%=Actions.ADDJUEGO%>"/>
							<c:param name="id" value="${resultado.id}"/>
						</c:url>
						<a class="a" href="${urlbiblioteca}"><button><fmt:message key="addbiblioteca" bundle="${messages}"></fmt:message></button></a>
					</c:otherwise>
				</c:when>
			</c:foreach> 
			</div>
				<div class="addCarrito">
					<form action="<%=ControllerPaths.CARRITO%>" method=post>
						<input type="hidden" name="action" value="${Actions.ANHADIR}">
						<select name="${ParameterNames.IDEDICION}" >
							<c:forEach items="${resultado.ediciones}" var="edicion">
								<option value="${edicion.id}">
									<fmt:message key="formato" bundle="${messages}">
										<c:forEach items="${edicion.idformato}" var="formato">
											<c:if test="${formato.id==edicion.idformato}">
												${formato.nombre}
											</c:if>
										</c:forEach>
									</fmt:message>
									<fmt:message key="tipoedicion" bundle="${messages}">
									 	<c:forEach items="${edicion.idtipoedicion}" var="tipoedicion">
									 		<c:if test="${tipoedicion.id==edicion.idtipoedicion}">
									 			${tipoedicion.nombre}
									 		</c:if>
									 	</c:forEach>
									</fmt:message>
								</option><br>
							</c:forEach>
						</select>
						<input type="submit" value="A�adir a Carrito"/>
					</form>
			</div>
		</c:if>
						
		<c:when test="${not empty resultados}">
			<!-- Paginacion  -->
			<p><center>
				
			<c:url var="urlBase" value="/juego" scope="page">
				<c:param name="action" value="buscar"/>
				<c:param name="nombre" value="${nombre}"/>
				<c:param name="categoria" value="${categoria}"/>
				<c:param name="creador" value="${creador}"/>
				<c:param name="plataforma" value="${plataforma}"/>
				<c:param name="idioma" value="${idioma}"/>
				<c:param name="fecha" value="${fecha}"/>
				<!--  y asi todos los parametros de la busqueda anterior ... -->
			</c:url>

			<!-- A la primera pagina -->
			<c:if test="${page > 1}">
				<a href="${urlBase}&page=1">
					<fmt:message key="primera" bundle="${messages}"/>
				</a>
				&nbsp;&nbsp;
			</c:if>

			<!-- A la anterior pagina -->
			<c:if test="${page > 1}">
				<a href="${urlBase}&page=${page - 1}">
					<fmt:message key="anterior" bundle="${messages}"/>
				</a>
				&nbsp;&nbsp;
			</c:if>

			<c:if test="${totalPages > 1}">	
			
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

			</c:if>

			<!-- A la siguiente página -->	
			<c:if test="${page < totalPages}">
				&nbsp;&nbsp;		
				<a href="${urlBase}&page=${page + 1}">
					<fmt:message key="siguiente" bundle="${messages}"/>
				</a>			
			</c:if>	
			<!-- A la ultima página -->
			<c:if test="${page != totalPages}">
				&nbsp;&nbsp;<a href="${urlBase}&page=${totalPages}"><fmt:message key="ultima" bundle="${messages}"/></a>
			</c:if>		

			</center></p>
			
		</c:when>
		
		<c:otherwise><h2>No se han encontrado resultados</h2></c:otherwise>
</section>
<%@include file="/html/common/footer.jsp"%>
