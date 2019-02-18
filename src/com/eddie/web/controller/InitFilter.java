package com.eddie.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

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

import com.eddie.web.util.Actions;
import com.eddie.web.util.ParameterNames;
import com.eddie.web.util.SessionAttributeNames;
import com.eddie.web.util.SessionManager;
import com.eddie.web.util.ViewPaths;

/**
 * Servlet Filter implementation class InitFilter
 */
@WebFilter("/*")
public class InitFilter implements Filter {

	
	private Logger logger=LogManager.getLogger(InitFilter.class);
	
	
    public InitFilter() {
        
    }


	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	
		HttpServletRequest httpRequest= (HttpServletRequest) request;
		HttpServletResponse httpResponse= (HttpServletResponse) response;
		String action=httpRequest.getParameter(ParameterNames.ACTION);
		if(!Actions.REGISTRO.equals(action) 
			&& Actions.PREREGISTRO.equals(action) 
			&& Actions.LOGIN.equals(action) 
			&& (SessionManager.get((HttpServletRequest)request, SessionAttributeNames.USER))==null) {
			logger.info("Filtro de autentificacion");
			httpResponse.sendRedirect(httpRequest.getContextPath()+ViewPaths.LOGIN);
		}else {
			PrintWriter pw= httpResponse.getWriter();
			
			String ip=httpRequest.getRemoteAddr().toString();
			httpResponse.sendRedirect(ViewPaths.HOME);
			logger.info("Direccion ip"+ip);
			
			pw.println("<p>Obtener IP del cliente. Obtener la IP del cliente </p>");
			pw.println("<p>La ip del cliente es " + ip + "</p>");
			chain.doFilter(httpRequest, httpResponse);
		}
		
	}

	
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
