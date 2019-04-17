<%@ page import="java.util.List" %>
<%@ page import="com.eddie.ecommerce.model.*" %>
<%@include file="/html/common/header.jsp"%>

<section>
	<%@include file="/html/common/action-error.jsp"%>
	<h2>Registro de Usuario</h2>
	<form action="<%=ControllerPaths.USUARIO%>" method="post">
	<%
				List<Provincia> provincias= (List<Provincia>) request.getAttribute(AttributeNames.PROVINCIA);
				List<Pais> paises= (List<Pais>) request.getAttribute(AttributeNames.PAISES);
				List<String> parameterErrors = errors.getErrors(ParameterNames.ACTION);
				for (String error: parameterErrors) {
					%><li><%=error%></li><%
				}
			%>
	<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.REGISTRO%>" />
		<label>Nombre: </label><input type="text" name="<%=ParameterNames.NOMBRE%>" required minlength="3" maxlength="45"/>
		<label>Apellido 1: </label><input type="text" name="<%=ParameterNames.APELLIDO1%>" minlength="3" maxlength="45"/>
		<label>Apellido 2: </label><input type="text" name="<%=ParameterNames.APELLIDO2%>" minlength="3" maxlength="45"/><br>
		<label>Email: </label><input type="email" name="<%=ParameterNames.EMAIL%>" 
				placeholder="usuario@ejemplo.com"
				value="<%=ParamsUtils.getParameter(request, ParameterNames.EMAIL)%>" required minlength="8" maxlength="75"/><br>
				<%
					parameterErrors = errors.getErrors(ParameterNames.EMAIL);
					for (String error: parameterErrors) {
						%><li><%=error%></li><%
					}
				%>
		<label>Telefono: </label><input type="text" name="<%=ParameterNames.TELEFONO%>" minlength="9" maxlength="30"><br>
		<label>Password: </label><input type="password" name="<%=ParameterNames.PASSWORD%>"required minlength="8" maxlength="30"/><br>
		<label>Fecha Nacimiento: </label><input type="date" name="<%=ParameterNames.FECHANACIMIENTO%>" required/><br>
		<label>Genero: </label><select type="text" name="<%=ParameterNames.GENERO%>" required>
			<option value="H">Hombre</option>
			<option value="M">Mujer</option>
			<option value="O">Otro</option>
		</select><br>
		
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
		
		<input type="submit" value="Registrar"/>	
	</form>
</section>

<%@include file="/html/common/footer.jsp"%>