package com.eddie.web.filter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eddie.ecommerce.exceptions.DataException;
import com.eddie.ecommerce.model.Categoria;
import com.eddie.ecommerce.model.Creador;
import com.eddie.ecommerce.model.Idioma;
import com.eddie.ecommerce.model.Juego;
import com.eddie.ecommerce.model.Plataforma;
import com.eddie.ecommerce.service.CategoriaService;
import com.eddie.ecommerce.service.CreadorService;
import com.eddie.ecommerce.service.IdiomaService;
import com.eddie.ecommerce.service.JuegoService;
import com.eddie.ecommerce.service.PlataformaService;
import com.eddie.ecommerce.service.impl.CategoriaServiceImpl;
import com.eddie.ecommerce.service.impl.CreadorServiceImpl;
import com.eddie.ecommerce.service.impl.IdiomaServiceImpl;
import com.eddie.ecommerce.service.impl.JuegoServiceImpl;
import com.eddie.ecommerce.service.impl.PlataformaServiceImpl;
import com.eddie.web.controller.AttributeNames;
import com.eddie.web.controller.ParameterNames;

/**
 * Servlet Filter implementation class InitStaticDataFilter
 */
@WebFilter("/index.jsp/*")
public class InitStaticDataFilter implements Filter {

	private CategoriaService cservice=null;
	private CreadorService crservice=null;
	private PlataformaService pservice=null;
	private IdiomaService iservice=null;
	private JuegoService jservice=null;
	private Logger logger=LogManager.getLogger(InitFilter.class);
	
    public InitStaticDataFilter() {
    	cservice = new CategoriaServiceImpl();
    	crservice= new CreadorServiceImpl();
    	pservice = new PlataformaServiceImpl();
    	iservice= new IdiomaServiceImpl();
    	jservice= new JuegoServiceImpl();
    }

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			HttpServletRequest httpRequest= (HttpServletRequest) request;
			HttpServletResponse httpResponse= (HttpServletResponse) response;
			
			List<Categoria> categorias= cservice.findAll("ES");
			List<Creador> creador= crservice.findAll();
			List<Plataforma> plataformas=pservice.findAll();
			List<Idioma> idioma=iservice.findAll("ES"); 
			
			List<Juego> todos=jservice.findAllByDate();
			List<Juego> valoracion=jservice.findAllByValoración();
			if(logger.isDebugEnabled()) {
				logger.debug(todos);
				logger.debug(valoracion);
			}
			request.setAttribute(AttributeNames.RESULTADOS_TODOS, todos);
			request.setAttribute(AttributeNames.RESULTADOS_TODOS_VALOR, valoracion);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {}

}
