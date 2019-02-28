<%@ page import="com.eddie.web.util.*, com.eddie.ecommerce.model.*, com.eddie.web.controller.*, com.eddie.ecommerce.service.*, java.util.List" %>
<%@ page import="java.util.*" %>       
<div id="buscador-form">
	
	<%
		List<Categoria> categoria= (List<Categoria>) request.getAttribute(AttributeNames.CATEGORIA_RESULTADOS);
		List<Creador> creador= (List<Creador>) request.getAttribute(AttributeNames.CREADOR_RESULTADOS);
		List<Plataforma> plataforma= (List<Plataforma>) request.getAttribute(AttributeNames.PLATAFORMA_RESULTADOS);
		List<Idioma> idioma= (List<Idioma>) request.getAttribute(AttributeNames.IDIOMA_RESULTADOS);
	%>
		<form action="<%=ControllerPaths.PRODUCTO%>" method="post">	
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.BUSCAR%>"/>
			<label>Categoria:</label>
			<select name="<%=ParameterNames.CATEGORIA%>">
			<option value="" selected disabled>Escoge</option>
				<%for(Categoria c: categoria){%>
				<option value="<%=c.getIdCategria()%>"><%=c.getNombre()%></option>
				<%}%>
			</select>
			<label>Creador:</label>
			<select name="<%=ParameterNames.CREADOR%>">
			<option value="" selected disabled>Escoge</option>
				<%for(Creador cr: creador){%>
				<option value="<%=cr.getIdCreador()%>"><%=cr.getNombre()%></option>
				<%} %>
			</select>
			<label>Plataforma:</label>
			<select name="<%=ParameterNames.PLATAFORMA%>">
			<option value="" selected disabled>Escoge</option>
				<%for(Plataforma p: plataforma){%>
				<option value="<%=p.getIdPlatadorma()%>"><%=p.getNombre()%></option>
				<%} %>
			</select>
			<label>Idioma:</label>
			<select name="<%=ParameterNames.IDIOMA%>">
			<option value="" selected disabled>Escoge</option>
				<%for(Idioma i: idioma){%>
				<option value="<%=i.getIdIdioma()%>"><%=i.getNombre()%></option>
				<%} %>
			</select>
			<label>Fecha:</label>
			<select name="<%=ParameterNames.FECHA%>">
				<option value="" selected disabled>Escoge</option>
				<option value="1990">1990</option>
                <option value="1991">1991</option>
                <option value="1992">1992</option>
                <option value="1993">1993</option>
                <option value="1994">1994</option>
                <option value="1995">1995</option>
                <option value="1996">1996</option>
                <option value="1997">1997</option>
                <option value="1998">1998</option>
                <option value="1999">1999</option>
                <option value="2000">2000</option>
                <option value="2001">2001</option>
                <option value="2002">2002</option>
                <option value="2003">2003</option>
                <option value="2004">2004</option>
                <option value="2005">2005</option>
                <option value="2006">2006</option>
                <option value="2007">2007</option>
                <option value="2008">2008</option>
                <option value="2009">2009</option>
                <option value="2010">2010</option>
                <option value="2011">2011</option>
                <option value="2012">2012</option>
                <option value="2013">2013</option>
                <option value="2014">2014</option>
                <option value="2015">2015</option>
                <option value="2016">2016</option>
                <option value="2017">2017</option>
				<option value="2018">2018</option>
				<option value="2019">2019</option>
			</select>
			<input type="text"
					name="<%=ParameterNames.NOMBRE%>" 
					value="<%=ParamsUtils.getParameter(request, ParameterNames.NOMBRE)%>" minlength="0" maxlength="45"/>
					
			<input type="submit" name="buscar" value="Buscar"/>
		</form>
	</p>

</div>	
