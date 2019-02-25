<%@ page import="com.eddie.ecommerce.service.*, com.eddie.web.controller.*,com.eddie.web.util.*, com.eddie.ecommerce.model.Usuario" %>

	<%
		Usuario u = (Usuario) request.getSession().getAttribute(SessionAttributeNames.USER);
		if (u == null) {
		
			%><li><a href="<%=request.getContextPath()%><%=ViewPaths.LOGIN%>">Entrar</a></li><%	
		
		} else {
			%>	
			<!-- usuario autenticado -->
			<li> 
				<script>
				function desplegarMenu() {
					  document.getElementById("meumenudes").classList.toggle("show");
					}

					window.onclick = function(event) {
						  if (!event.target.matches('.dropbtn')) {

						    var menudess = document.getElementsByClassName("menudes-contido");
						    var i;
						    for (i = 0; i < menudess.length; i++) {
						      var openmenudes = menudess[i];
						      if (openmenudes.classList.contains('show')) {
						        openmenudes.classList.remove('show');
						      }
						    }
						  }
						}
				</script>
				<a onclick="desplegarMenu()" class="dropbtn"><%=u.getNombre()%></a>
				<div id="meumenudes" class="menudes-contido"> 
	                <a href="<%=ControllerPaths.USUARIO%>?<%=ParameterNames.ACTION%>=<%=Actions.PRECONFIGURACION%>">Mi Cuenta</a>
	               	<a href="<%=ControllerPaths.BIBLIOTECA%>?<%=ParameterNames.ACTION%>=<%=Actions.BIBLIOTECA%>&amp;<%=ParameterNames.EMAIL%>=<%=u.getEmail()%>">Biblioteca</a>
	                <a href="<%=request.getContextPath()%><%=ViewPaths.HISTORIALPEDIDO%>">Historial de Pedidos</a>

	             </div>

			</li>
	
			<li><a href="<%=ControllerPaths.USUARIO%>?<%=ParameterNames.ACTION%>=<%=Actions.LOGOUT%>">Salir</a></li>
				
			<%
		}
	%>	
