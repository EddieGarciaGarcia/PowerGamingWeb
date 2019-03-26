<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.eddie.web.util.*, com.eddie.web.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="heigth:100%; width:100%;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="<%=request.getContextPath()%>/html/css/index.css" rel="stylesheet" type="text/css">
    <script type="text/Javascript" href="<%=request.getContextPath()%>/html/js/index.js"></script>
	<script type="text/Javascript" href="<%=request.getContextPath()%>/html/js/fucniones.js"></script>
</head>

<body>
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
     </header>
	
	