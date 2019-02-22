package com.eddie.web.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.eddie.ecommerce.model.ItemBiblioteca;
import com.eddie.ecommerce.model.Juego;
import com.eddie.ecommerce.model.JuegoCriteria;
import com.eddie.ecommerce.model.Plataforma;
import com.eddie.ecommerce.model.Usuario;
import com.eddie.ecommerce.service.CategoriaService;
import com.eddie.ecommerce.service.JuegoService;
import com.eddie.ecommerce.service.UsuarioService;
import com.eddie.ecommerce.service.impl.CategoriaServiceImpl;
import com.eddie.ecommerce.service.impl.JuegoServiceImpl;
import com.eddie.ecommerce.service.impl.UsuarioServiceImpl;
import com.eddie.web.model.Errors;
import com.eddie.web.util.LimpiezaValidacion;
import com.eddie.web.util.SessionAttributeNames;
import com.eddie.web.util.SessionManager;
import com.eddie.web.controller.Actions;
import com.eddie.web.controller.AttributeNames;
import com.eddie.web.controller.ParameterNames;
import com.eddie.web.controller.ViewPaths;

/**
 * Servlet implementation class ProductoServlet
 */
@WebServlet("/producto")
public class ProductoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(ProductoServlet.class);

	private JuegoService jservice = null;
	private CategoriaService cservice=null;
	private UsuarioService userv=null;
	
	public ProductoServlet() {
		super();
		jservice = new JuegoServiceImpl();
		cservice= new CategoriaServiceImpl();
		userv=new UsuarioServiceImpl();
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
			
			if (Actions.BUSCAR.equalsIgnoreCase(action)) {
				Usuario user=(Usuario) SessionManager.get(request, SessionAttributeNames.USER);
				// Recuperar parametros
				String nombre = request.getParameter(ParameterNames.NOMBRE);
				
				String nombreValid= LimpiezaValidacion.validNombreJuego(nombre);
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
				jc.setNombre(nombreValid);
				List<Juego> resultados;
				resultados = jservice.findByJuegoCriteria(jc,"ES");
				
				// Buscar juegos que tiene incluidos en la biblioteca
				if(user!=null) {
					List<ItemBiblioteca> results=userv.findByUsuario(user.getEmail());
					List<Juego> j=new ArrayList<Juego>();
					for (ItemBiblioteca it: results) {
						Juego addjuego=jservice.findById(it.getIdJuego(), "ES");
						j.add(addjuego);
					}
					request.setAttribute(AttributeNames.BIBLIOTECA_RESULTADOS, j);
				}
		
				request.setAttribute(AttributeNames.PRODUCTO_RESULTADOS, resultados);
				
				target = ViewPaths.BUSCADOR;
				
			}else if(Actions.JUEGO.equalsIgnoreCase(action)) {
				Usuario user=(Usuario) SessionManager.get(request, SessionAttributeNames.USER);
				String id=request.getParameter(ParameterNames.ID);
				Integer idJuego= Integer.valueOf(id);
				
				Juego juego = jservice.findById(idJuego, "ES");
				//Listado de comentarios
				List<ItemBiblioteca> comentarios=jservice.findByJuego(juego.getIdJuego());
				
				// Buscar juegos que tiene incluidos en la biblioteca para mostrar o no el boton de a�adir
				if(user!=null) {
					List<ItemBiblioteca> results=userv.findByUsuario(user.getEmail());
					List<Juego> j=new ArrayList<Juego>();
					for (ItemBiblioteca it: results) {
						Juego addjuego=jservice.findById(it.getIdJuego(), "ES");
						j.add(addjuego);
					}

					request.setAttribute(AttributeNames.BIBLIOTECA_RESULTADOS, j);
				}
				if(comentarios.size()<1) {
					Map<Usuario, ItemBiblioteca> comentario= new HashMap<Usuario, ItemBiblioteca>();
					for(ItemBiblioteca i:comentarios){
						Usuario u=userv.findById(i.getEmail());
						comentario.put(u, i);
					}		
					request.setAttribute(AttributeNames.COMENTARIOS_JUEGO, comentario);
				}	
				
				request.setAttribute(AttributeNames.PRODUCTO_RESULTADOS, juego);
				
				target =ViewPaths.JUEGO;
			}else {
			
				// Mmm...
				logger.error("Action desconocida");
				// target ?
			}
			request.getRequestDispatcher(target).forward(request, response);	
			} catch (DataException e) {
				logger.info(e.getMessage(),e);
			} catch (SQLException e) {
				logger.info(e.getMessage(),e);
			}	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
