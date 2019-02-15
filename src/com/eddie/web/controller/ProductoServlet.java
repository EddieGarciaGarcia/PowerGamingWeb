package com.eddie.web.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eddie.ecommerce.exceptions.DataException;
import com.eddie.ecommerce.model.Categoria;
import com.eddie.ecommerce.model.Idioma;
import com.eddie.ecommerce.model.Juego;
import com.eddie.ecommerce.model.JuegoCriteria;
import com.eddie.ecommerce.model.Plataforma;
import com.eddie.ecommerce.service.JuegoService;
import com.eddie.ecommerce.service.impl.JuegoServiceImpl;
import com.eddie.web.model.Errors;
import com.eddie.web.util.Actions;
import com.eddie.web.util.AttributeNames;
import com.eddie.web.util.ParameterNames;
import com.eddie.web.util.ViewPaths;

/**
 * Servlet implementation class ProductoServlet
 */
@WebServlet("/producto")
public class ProductoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(ProductoServlet.class);

	private JuegoService jservice = null;

	public ProductoServlet() {
		super();
		jservice = new JuegoServiceImpl();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// MAÃ‘Ã� SEGUIREMOS...
			String action = request.getParameter(ParameterNames.ACTION);

			if (logger.isDebugEnabled()) {
				logger.debug("Action {}: {}", action, ToStringBuilder.reflectionToString(request.getParameterMap()));
			}

			Errors errors = new Errors(); 
			String target = null;
			
			if(Actions.ALLJUEGO.equalsIgnoreCase(action)) {
				List<Juego> juegos=jservice.findAllByDate();
				request.setAttribute(AttributeNames.RESULTADOS_TODOS, juegos);
				target= ViewPaths.HOME;
			}
			
			if(Actions.ALLJUEGOVALOR.equalsIgnoreCase(action)) {
				List<Juego> juegos=jservice.findAllByValoraci�n();
				request.setAttribute(AttributeNames.RESULTADOS_TODOS_VALOR, juegos);
				target= ViewPaths.HOME;
			}
			
			if (Actions.BUSCAR.equalsIgnoreCase(action)) {

				// Recuperar parametros
				String nombre = request.getParameter(ParameterNames.NOMBRE);

				// Limpiar
				// ...

				// Validar
				//..

				// if hasErrors

				// else
				JuegoCriteria jc=new JuegoCriteria();
				Idioma idiom=new Idioma();
				List<Idioma>idiomas=new ArrayList<Idioma>();
				Plataforma plataf=new Plataforma();
				List<Plataforma> plataforma=new ArrayList<Plataforma>();
				Categoria ca=new Categoria();
				List<Categoria> cat=new ArrayList<Categoria>();
				
				jc.setCategoria(cat);
				jc.setIdioma(idiomas);
				jc.setPlataforma(plataforma);
				jc.setNombre(nombre);
				List<Juego> resultados;
				
					resultados = jservice.findByJuegoCriteria(jc,"ES");
				
			
				request.setAttribute(AttributeNames.RESULTADOS, resultados);
				
				target = ViewPaths.BUSCADOR;
				
			} else {
				// Mmm...
				logger.error("Action desconocida");
				// target ?
			}
			request.getRequestDispatcher(target).forward(request, response);	
			} catch (DataException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
