<%@ page import="com.eddie.web.util.*, com.eddie.web.model.*" %>
<%
	Errors errors = (Errors) request.getAttribute(AttributeNames.ERRORS);
	if (errors == null) errors = new Errors();
%>