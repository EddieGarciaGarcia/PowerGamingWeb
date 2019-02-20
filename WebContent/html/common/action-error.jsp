<%@ page import="com.eddie.web.util.*, com.eddie.web.model.*, com.eddie.web.controller.*" %>
<%
	Errors errors = (Errors) request.getAttribute(AttributeNames.ERRORS);
	if (errors == null) errors = new Errors();
%>