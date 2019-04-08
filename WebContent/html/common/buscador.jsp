<%@ page import="com.eddie.web.util.*, com.eddie.ecommerce.model.*, com.eddie.web.controller.*, com.eddie.ecommerce.service.*, java.util.List" %>
<%@ page import="java.util.*" %>       
<div id="buscador-form">
	
	<%
		List<Categoria> categoria= (List<Categoria>) request.getAttribute(AttributeNames.CATEGORIA_RESULTADOS);
		List<Creador> creador= (List<Creador>) request.getAttribute(AttributeNames.CREADOR_RESULTADOS);
		List<Plataforma> plataforma= (List<Plataforma>) request.getAttribute(AttributeNames.PLATAFORMA_RESULTADOS);
		List<Idioma> idioma= (List<Idioma>) request.getAttribute(AttributeNames.IDIOMA_RESULTADOS);
	%>
	<script>
		function ver(n) {
        	document.getElementById("meumenudes"+n).style.display="block"
        }
		function ocultar(n) {
        	document.getElementById("meumenudes"+n).style.display="none"
        }
	</script>
		<form action="<%=ControllerPaths.JUEGO%>" method="post">	
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.BUSCAR%>"/>
			<a onmouseover="ver(1)" onmouseout="ocultar(1)" class="dropbtn">Categoria:
			<div id="meumenudes1" class="menu-search" > 
			
				<%for(Categoria c: categoria){%>
				<input type="checkbox" name="<%=ParameterNames.CATEGORIA%>" value="<%=c.getIdCategria()%>"/><%=c.getNombre()%></br>
				<%}%>
			</div>
			</a>
			<label>Creador:</label>
			<select name="<%=ParameterNames.CREADOR%>">
			<option selected disabled>Escoge</option>
				<%for(Creador cr: creador){%>
				<option value="<%=cr.getIdCreador()%>"><%=cr.getNombre()%></option>
				<%} %>
			</select>
			<a onmouseover="ver(2)" onmouseout="ocultar(2)" class="dropbtn">Plataforma:
			<div id="meumenudes2" class="menu-search2"> 
				<%for(Plataforma p: plataforma){%>
				<input type="checkbox" name="<%=ParameterNames.PLATAFORMA%>" value="<%=p.getIdPlatadorma()%>"/><%=p.getNombre()%></br>
				<%} %>
			</div>
			</a>
			<a onmouseover="ver(3)" onmouseout="ocultar(3)" class="dropbtn">Idioma:
			<div id="meumenudes3" class="menu-search3"> 
				<%for(Idioma i: idioma){%>
				<input type="checkbox" name="<%=ParameterNames.IDIOMA%>" value="<%=i.getIdIdioma()%>"/><%=i.getNombre()%></br>
				<%} %>
			</div>
			</a>
			<label>Fecha:</label>
			<select name="<%=ParameterNames.FECHA%>">
				<option  selected disabled>Escoge</option>
				<option value="2019">2019</option>
				<option value="2018">2018</option>
				<option value="2017">2017</option>
				<option value="2016">2016</option>
				<option value="2015">2015</option>
				<option value="2014">2014</option>
				<option value="2013">2013</option>
				<option value="2012">2012</option>
				<option value="2011">2011</option>
				<option value="2010">2010</option>
				<option value="2009">2009</option>
				<option value="2008">2008</option>
				<option value="2007">2007</option>
				<option value="2006">2006</option>
				<option value="2005">2005</option>
				<option value="2004">2004</option>
				<option value="2003">2003</option>
				<option value="2002">2002</option>
				<option value="2001">2001</option>
                <option value="2000">2000</option>
			</select>
			<input type="text"
					name="<%=ParameterNames.NOMBRE%>" 
					value="<%=ParamsUtils.getParameter(request, ParameterNames.NOMBRE)%>" minlength="0" maxlength="45"/>
					
			<input type="submit" name="buscar" value="Buscar"/>
		</form>
	</p>

</div>	
