package com.eddie.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eddie.ecommerce.exceptions.DataException;
import com.eddie.web.config.ConfigurationManager;

/**
 * Servlet implementation class Contenidos
 */
@WebServlet("/Contenidos")
public class ContenidosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(ContenidosServlet.class);
       
    public ContenidosServlet() {
        super();  
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action= request.getParameter(ParameterNames.ACTION);
		boolean redirect=false;
		String target=null;
		String ruta=null;
		try {
		
			if(ParameterNames.VIDEO.equals(action)) {
				ruta=ConfigurationManager.getInstance().getParameter("video.storage");
				
			}else if(ParameterNames.IMAGEN.equals(action)) {
				ruta=ConfigurationManager.getInstance().getParameter("imagen.storage");
				
			}else {
				logger.error("Action desconocida");
				target= ViewPaths.INICIO;
			}
			if(redirect==true) {
				logger.info("Redirect to "+target);
				response.sendRedirect(target);
			}else {
				logger.info("forwarding to "+target);
				request.getRequestDispatcher(target).forward(request, response);
			}	
		} catch (IOException e) {
			logger.info(e.getMessage(),e);
		} 
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
