<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.eddie.web.util.*, com.eddie.web.model.*" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setBundle basename = "resources.messages" var="traduccion" scope="session"/>
<fmt:setLocale value="${sessionScope['user-locale']}" scope="session"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="heigth:100%; width:100%;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="<%=request.getContextPath()%>/html/css/index.css" rel="stylesheet" type="text/css">
    <script type="text/Javascript" href="<%=request.getContextPath()%>/html/js/index.js"></script>
	<script type="text/Javascript" href="<%=request.getContextPath()%>/html/js/fucniones.js"></script>
</head>

<body>

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
<%
List<Formato> formato= (List<Formato>) request.getAttribute(AttributeNames.FORMATO_RESULTADOS);
List<TipoEdicion> tipoEdicion= (List<TipoEdicion>) request.getAttribute(AttributeNames.TIPOEDICION_RESULTADOS);
%>
	<header class="menu"> 
                <img class="menufig" src="<%=request.getContextPath()%>/imgs/powergaming.png"></img>
                <div class="menuimg">
                    <a  href="<%=ControllerPaths.USUARIO%>?<%=ParameterNames.ACTION%>=<%=Actions.CAMBIAR_IDIOMA%>&amp;<%=ParameterNames.LOCALE%>=<%=ParameterNames.EN%>">
                    <img id="img1" src="<%=request.getContextPath()%>/imgs/en-us.png"></img></a>
                    <a  href="<%=ControllerPaths.USUARIO%>?<%=ParameterNames.ACTION%>=<%=Actions.CAMBIAR_IDIOMA%>&amp;<%=ParameterNames.LOCALE%>=<%=ParameterNames.ES%>">
                    <img id="img2" src="<%=request.getContextPath()%>/imgs/es.png"></img></a>
                </div>
                <nav>
                    <ul class="nav">
                    	<li><a href="<%=request.getContextPath()%><%=ViewPaths.INICIO%>">Inicio</a></li>
                    	<%@include file="/html/common/user-menu.jsp"%>
                    </ul>
                </nav>
                <div>
                	<%@include file="/html/carrito.jsp"%>
                </div>
     </header>
	
	