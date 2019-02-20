<%@ page import="com.eddie.web.util.*, com.eddie.web.controller.*" %>
<%@ page import="java.util.*" %>       

<div id="buscador-form">
	
		<form action="<%=ControllerPaths.PRODUCTO%>" method="post">	
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.BUSCAR%>"/>
			<label>Categoria:</label>
			<select multiple name="<%=ParameterNames.CATEGORIA%>">
				<option></option>
			</select>
			<label>Creador:</label>
			<select multiple name="<%=ParameterNames.CREADOR%>">
				<option></option>
			</select>
			<label>Plataforma:</label>
			<select multiple name="<%=ParameterNames.PLATAFORMA%>">
				<option></option>
			</select>
			<label>Idioma:</label>
			<select multiple name="<%=ParameterNames.IDIOMA%>">
				<option></option>
			</select>
			<label>Fecha:</label>
			<select multiple name="<%=ParameterNames.FECHA%>">
				<option></option>
			</select>
			<input type="text"
					name="<%=ParameterNames.NOMBRE%>" 
					value="<%=ParamsUtils.getParameter(request, ParameterNames.NOMBRE) %>"/>
					
			<input type="submit" name="buscar" value="Buscar"/>
		</form>
	</p>

</div>	
