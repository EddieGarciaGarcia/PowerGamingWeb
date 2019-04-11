<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.eddie.ecommerce.model.*, com.eddie.web.util.*, com.eddie.web.model.*, com.eddie.web.controller.*, java.util.List "%>

<div id="carrito">
			<script>
			function desplegarMenu5() {
				  document.getElementById("meumenudes5").classList.toggle("show2");
				}
			window.onclick = function(event) {
				  if (!event.target.matches('.dropbtn')) {

				    var menudess = document.getElementsByClassName("menudes-contido2");
				    var i;
				    for (i = 0; i < menudess.length; i++) {
				      var openmenudes = menudess[i];
				      if (openmenudes.classList.contains('show2')) {
				        openmenudes.classList.remove('show2');
				      }
				    }
				  }
				}
		</script>
<a onclick="desplegarMenu5()" class="dropbtn"><fmt:message key="car" bundle="${traduccion}"/></a>
<div id="meumenudes5" class="menudes-contido2">
<%
	Carrito carrito = (Carrito) SessionManager.get(request, SessionAttributeNames.CARRITO);
	if (carrito != null) {
			
			for (LineaCarrito lineaCarrito : carrito.getLinea()){
				
%>
			<div class="lineacarrito">
				<img src="<%=request.getContextPath()%>/imgs/icojuego/<%=lineaCarrito.getEdicion().getIdJuego()%>.jpg"></img>
				<div class="edicion"><p><%for(Formato f:formato){
					if(f.getIdFormato().equals(lineaCarrito.getEdicion().getIdFormato())){
						%><%=f.getNombre()%>
					<%}
					}%> 
					<%for(TipoEdicion te:tipoEdicion){
											if(te.getIdTipoEdicion().equals(lineaCarrito.getEdicion().getIdTipoEdicion())){
												%><%=te.getNombre()%>
											<%}}%></br>
											
				<fmt:message key="precio" bundle="${traduccion}"/> <%=lineaCarrito.getEdicion().getPrecio()%> </p></br>
				<a class="a3" href="<%=ControllerPaths.CARRITO %>?<%=ParameterNames.ACTION%>=<%=Actions.ELIMINAR%>&amp;<%=ParameterNames.IDEDICION%>=<%=lineaCarrito.getEdicion().getId()%>">X</a>
				</div>
				<hr>
					
			</div>
			
<% 
				
			}
			%>
			<div class="carritofinal">
				<div>Total: <%=carrito.getTotal()%></div>
				<%if(u!=null){%>
				<a  href="<%=ControllerPaths.HISTORIAL%>?
										<%=ParameterNames.ACTION%>=<%=Actions.ADDHISTORIAL%>"><button><fmt:message key="guardarpedido" bundle="${traduccion}"/></button></a>
			
			<% }else{%>
				<p><fmt:message key="carritomensage" bundle="${traduccion}"/></p>
				<a  href="<%=ControllerPaths.USUARIO%>?
										<%=ParameterNames.ACTION%>=<%=Actions.PREREGISTRO%>"><button><fmt:message key="registrarse" bundle="${traduccion}"/></button></a>
			</div>
			<%}
	}
%>	
</div>
</div>
