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
import com.eddie.ecommerce.model.Creador;
import com.eddie.ecommerce.model.Idioma;
import com.eddie.ecommerce.model.ItemBiblioteca;
import com.eddie.ecommerce.model.Juego;
import com.eddie.ecommerce.model.JuegoCriteria;
import com.eddie.ecommerce.model.Plataforma;
import com.eddie.ecommerce.model.Usuario;
import com.eddie.ecommerce.service.CategoriaService;
import com.eddie.ecommerce.service.CreadorService;
import com.eddie.ecommerce.service.IdiomaService;
import com.eddie.ecommerce.service.JuegoService;
import com.eddie.ecommerce.service.PlataformaService;
import com.eddie.ecommerce.service.UsuarioService;
import com.eddie.ecommerce.service.impl.CategoriaServiceImpl;
import com.eddie.ecommerce.service.impl.CreadorServiceImpl;
import com.eddie.ecommerce.service.impl.IdiomaServiceImpl;
import com.eddie.ecommerce.service.impl.JuegoServiceImpl;
import com.eddie.ecommerce.service.impl.PlataformaServiceImpl;
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
	private UsuarioService userv=null;
	private CategoriaService cservice=null;
	private PlataformaService pservice=null;
	private IdiomaService iservice=null;
	private CreadorService crservice=null;
	
	public ProductoServlet() {
		super();
		jservice = new JuegoServiceImpl();
		userv=new UsuarioServiceImpl();
		cservice=new CategoriaServiceImpl();
		pservice= new PlataformaServiceImpl();
		iservice = new IdiomaServiceImpl();
		crservice= new CreadorServiceImpl();
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
			boolean redirect=false;
			
			if (Actions.BUSCAR.equalsIgnoreCase(action)) {
				Usuario user=(Usuario) SessionManager.get(request, SessionAttributeNames.USER);
				// Recuperar parametros
				String nombre = request.getParameter(ParameterNames.NOMBRE);
				String[] categoria=request.getParameterValues(ParameterNames.CATEGORIA);
				String creador=request.getParameter(ParameterNames.CREADOR);
				//String[] plataforma=request.getParameter(ParameterNames.PLATAFORMA);
				//String[] idioma=request.getParameter(ParameterNames.IDIOMA); 
				String fecha=request.getParameter(ParameterNames.FECHA);
				
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
				List<Plataforma> plataformas=new ArrayList<Plataforma>();
				Categoria ca=new Categoria();
				List<Categoria> cat=new ArrayList<Categoria>();
				
				
				if(categoria!=null) {
					Integer categoriaValid= Integer.valueOf(categoria);
					ca.setIdCategria(categoriaValid);
					cat.add(ca);
				}
				if(idiomas!=null) {
					
				}
				if(plataformas!=null) {
					
				}
				if(creador!=null) {
					Integer creadorValid=Integer.valueOf(creador);
					jc.setIdCreador(creadorValid);
				}
				jc.setIdioma(idiomas);
				jc.setPlataforma(plataformas);
				jc.setCategoria(cat);
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
				List<Categoria> categoria=cservice.findByJuego(idJuego, "ES");
				List<Plataforma> plataforma=pservice.findByJuego(idJuego);
				List<Idioma> idioma=iservice.findByJuego(idJuego, "ES");
				Creador creador=crservice.findbyIdCreador(juego.getIdCreador());
				
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
				if(comentarios.size()>0) {
					Map<Usuario, ItemBiblioteca> comentario= new HashMap<Usuario, ItemBiblioteca>();
					for(ItemBiblioteca i:comentarios){
						Usuario u=userv.findById(i.getEmail());
						comentario.put(u, i);
					}		
					request.setAttribute(AttributeNames.COMENTARIOS_JUEGO, comentario);
				}	
				request.setAttribute(AttributeNames.CATEGORIA_JUEGO,categoria);
				request.setAttribute(AttributeNames.CREADOR_JUEGO, creador);
				request.setAttribute(AttributeNames.PLATAFORMA_JUEGO, plataforma);
				request.setAttribute(AttributeNames.IDIOMA_JUEGO, idioma);
				request.setAttribute(AttributeNames.PRODUCTO_RESULTADOS, juego);
				
				target =ViewPaths.JUEGO;
			}else {
				logger.error("Action desconocida");
				target= ViewPaths.HOME;
				
			}
			if(redirect==true) {
				logger.info("Redirect to "+target);
				response.sendRedirect(target);
			}else {
				logger.info("forwarding to "+target);
				request.getRequestDispatcher(target).forward(request, response);
			}	
			} catch (DataException e) {
				logger.info(e.getMessage(),e);
			} catch (SQLException e) {
				logger.info(e.getMessage(),e);
			}	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
