package com.eddie.web.filter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
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
import com.eddie.ecommerce.model.Formato;
import com.eddie.ecommerce.model.Idioma;
import com.eddie.ecommerce.model.Juego;
import com.eddie.ecommerce.model.Plataforma;
import com.eddie.ecommerce.model.TipoEdicion;
import com.eddie.ecommerce.service.CategoriaService;
import com.eddie.ecommerce.service.CreadorService;
import com.eddie.ecommerce.service.FormatoService;
import com.eddie.ecommerce.service.IdiomaService;
import com.eddie.ecommerce.service.JuegoService;
import com.eddie.ecommerce.service.PlataformaService;
import com.eddie.ecommerce.service.TipoEdicionService;
import com.eddie.ecommerce.service.impl.CategoriaServiceImpl;
import com.eddie.ecommerce.service.impl.CreadorServiceImpl;
import com.eddie.ecommerce.service.impl.FormatoServiceImpl;
import com.eddie.ecommerce.service.impl.IdiomaServiceImpl;
import com.eddie.ecommerce.service.impl.JuegoServiceImpl;
import com.eddie.ecommerce.service.impl.PlataformaServiceImpl;
import com.eddie.ecommerce.service.impl.TipoEdicionServiceImpl;
import com.eddie.web.controller.AttributeNames;
import com.eddie.web.util.SessionManager;
import com.eddie.web.util.WebConstants;


/**
 * Servlet Filter implementation class InitStaticDataFilter
 */
@WebFilter("/*")
public class InitStaticDataFilter implements Filter {
	private CategoriaService categoriaService=null;
	private CreadorService creadorService=null;
	private PlataformaService plataformaService=null;
	private IdiomaService idiomaService=null;
	private FormatoService formatoService=null;
	private TipoEdicionService tipoEdicionService= null;
	private JuegoService juegoService=null;
	
	private Logger logger=LogManager.getLogger(InitFilter.class);
	
    public InitStaticDataFilter() {
    	categoriaService=new CategoriaServiceImpl();
    	creadorService= new CreadorServiceImpl();
    	plataformaService= new PlataformaServiceImpl();
    	idiomaService = new IdiomaServiceImpl();
    	formatoService = new FormatoServiceImpl();
    	tipoEdicionService= new TipoEdicionServiceImpl();
    	juegoService= new JuegoServiceImpl();
    }

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		try {
			HttpServletRequest httpRequest= (HttpServletRequest) request;
			HttpServletResponse httpResponse= (HttpServletResponse) response;
	
			Enumeration<String> headerNames = httpRequest.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				String headerValue = httpRequest.getHeader(headerName);
				if(logger.isDebugEnabled()) {
					logger.debug(headerName+"="+headerValue);
				}
			}
			String idiomaPagina=SessionManager.get(httpRequest,WebConstants.USER_LOCALE).toString().substring(0,2).toUpperCase();
			List<Juego> valoracion=juegoService.findAllByValoracion(idiomaPagina);
			
			//Precarga de todos los datos en cache
			List<Categoria> categorias= categoriaService.findAll(idiomaPagina);
			List<Creador> creador= creadorService.findAll();
			List<Plataforma> plataformas=plataformaService.findAll();
			List<Idioma> idioma=idiomaService.findAll(idiomaPagina); 
			List<Formato> formatos= formatoService.findAll(idiomaPagina);
			List<TipoEdicion> tipoEdicion=tipoEdicionService.findAll(idiomaPagina);
			
			request.setAttribute(AttributeNames.FORMATO_RESULTADOS, formatos);
			request.setAttribute(AttributeNames.TIPOEDICION_RESULTADOS, tipoEdicion);
			request.setAttribute(AttributeNames.CATEGORIA_RESULTADOS, categorias);
			request.setAttribute(AttributeNames.CREADOR_RESULTADOS, creador);
			request.setAttribute(AttributeNames.PLATAFORMA_RESULTADOS, plataformas);
			request.setAttribute(AttributeNames.IDIOMA_RESULTADOS, idioma);
			
			if(logger.isDebugEnabled()) {
				logger.debug(valoracion);
				logger.debug(categorias);
				logger.debug(creador);
				logger.debug(plataformas);
				logger.debug(idioma);
				logger.debug(formatos);
				logger.debug(tipoEdicion);
			}
			
			request.setAttribute(AttributeNames.RESULTADOS_TODOS_VALOR, valoracion);
			
		} catch (DataException e) {
			logger.info(e.getMessage(),e);
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {}

}
