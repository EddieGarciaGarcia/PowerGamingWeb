<%@ page import="java.util.List" %>
<%@ page import="com.eddie.ecommerce.model.*, com.eddie.web.controller.*" %>
<%@include file="/html/common/header.jsp"%>
<section>
<%@include file="/html/common/action-error.jsp"%>
	<h2><fmt:message key="myaccountmensage" bundle="${traduccion}"/></h2>
	<form action="<%=ControllerPaths.USUARIO%>" method="post">
	<%
				List<Provincia> provincias= (List<Provincia>) request.getAttribute(AttributeNames.PROVINCIA);
				List<Pais> paises= (List<Pais>) request.getAttribute(AttributeNames.PAISES);
				List<String> parameterErrors = errors.getErrors(ParameterNames.ACTION);
				for (String error: parameterErrors) {
					%><li><%=error%></li><%
				}
			%>
	<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.CONFIGURACION%>"/>
	<% 
		Usuario user=(Usuario)request.getAttribute(AttributeNames.USER);
	%>
	
		<label><fmt:message key="name" bundle="${traduccion}"/>:</label>
		<label><%=user.getNombre()%> , <fmt:message key="cambiar" bundle="${traduccion}"/>: </label>
		<input type="text" name="<%=ParameterNames.NOMBRE%>" /><br>
		<label><fmt:message key="apellido1" bundle="${traduccion}"/>: </label>
		<label><%=user.getApellido1()%> , <fmt:message key="cambiar" bundle="${traduccion}"/>: </label>
		<input type="text" name="<%=ParameterNames.APELLIDO1%>"/><br>
		<label><fmt:message key="apellido2" bundle="${traduccion}"/>: </label>
		<label><%=user.getApellido2()%> , <fmt:message key="cambiar" bundle="${traduccion}"/>: </label>
		<input type="text" name="<%=ParameterNames.APELLIDO2%>"/><br>
				<%
					parameterErrors = errors.getErrors(ParameterNames.EMAIL);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
				%>
		<label><fmt:message key="telefono" bundle="${traduccion}"/>: </label>
		<label><%=user.getTelefono()%> , Cambiar a: </label>
		<input type="text" name="<%=ParameterNames.TELEFONO%>" /><br>
		<label><fmt:message key="password" bundle="${traduccion}"/>, <fmt:message key="cambiar" bundle="${traduccion}"/>:  </label>
		<input type="password" name="<%=ParameterNames.PASSWORD%>" /><br>
		<label><fmt:message key="nombreuser" bundle="${traduccion}"/>: </label>
		<label><%=user.getNombreUser()%> , <fmt:message key="cambiar" bundle="${traduccion}"/>: </label>
		<input type="text" name="<%=ParameterNames.NOMBREUSER%>" /><br>
		
		<label>Pais:(Ignorar de momento)</label><select name="<%=ParameterNames.PAIS%>">
				<%
					for(Pais pais: paises){
						%>
						<option id="Pais+<%=pais.getIdPais()%>" value="<%=pais.getIdPais()%>"><%=pais.getNombre()%></option>
						<%
					}
				%>
		</select>
		<label>Provincia:</label><select name="<%=ParameterNames.PROVINCIA%>">
			<% 
				for(Provincia provincia: provincias){
			%><option id="<%=provincia.getIdProvincia()%>"><%=provincia.getNombre()%></option><%
				}
			%>
		</select>
		<label>Localidad</label><input type="text" name="<%=ParameterNames.LOCALIDAD%>"/><br>
		<label>Codigo Postal</label><input type="text" name="<%=ParameterNames.CODPOSTAL%>"/>
		<label>Calle</label><input type="text" name="<%=ParameterNames.CALLE%>"/>
		<label>Numero</label><input type="text" name="<%=ParameterNames.NUMERO%>"/>
		<label>Piso</label><input type="text" name="<%=ParameterNames.PISO%>"/><br>
		
		<input type="submit" value="Actualizar"/>	
	</form>
</section>

</section>
<%@include file="/html/common/footer.jsp"%>