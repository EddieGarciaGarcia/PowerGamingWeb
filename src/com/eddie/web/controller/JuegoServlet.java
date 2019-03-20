package com.eddie.web.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import com.eddie.ecommerce.service.Resultados;
import com.eddie.ecommerce.exceptions.DataException;
import com.eddie.ecommerce.model.Creador;
import com.eddie.ecommerce.model.ItemBiblioteca;
import com.eddie.ecommerce.model.Juego;
import com.eddie.ecommerce.model.JuegoCriteria;
import com.eddie.ecommerce.model.Usuario;
import com.eddie.ecommerce.service.CreadorService;
import com.eddie.ecommerce.service.JuegoService;
import com.eddie.ecommerce.service.UsuarioService;
import com.eddie.ecommerce.service.impl.CreadorServiceImpl;
import com.eddie.ecommerce.service.impl.JuegoServiceImpl;
import com.eddie.ecommerce.service.impl.UsuarioServiceImpl;
import com.eddie.web.model.Errors;
import com.eddie.web.util.ArrayUtils;
import com.eddie.web.util.LimpiezaValidacion;
import com.eddie.web.util.SessionAttributeNames;
import com.eddie.web.util.SessionManager;
import com.eddie.web.util.WebUtils;
import com.eddie.web.config.ConfigurationManager;
import com.eddie.web.config.ConfigurationParameterNames;
import com.eddie.web.controller.Actions;
import com.eddie.web.controller.AttributeNames;
import com.eddie.web.controller.ParameterNames;
import com.eddie.web.controller.ViewPaths;

/**
 * Servlet implementation class ProductoServlet
 */
@WebServlet("/producto")
public class JuegoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static int pageSize = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
						ConfigurationParameterNames.RESULTS_PAGE_SIZE_DEFAULT)); 

	private static int pagingPageCount = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
						ConfigurationParameterNames.RESULTS_PAGING_PAGE_COUNT)); 
	
	private static Logger logger = LogManager.getLogger(JuegoServlet.class);

	private JuegoService jservice = null;
	private UsuarioService userv=null;

	private CreadorService crservice=null;
	
	public JuegoServlet() {
		super();
		jservice = new JuegoServiceImpl();
		userv=new UsuarioServiceImpl();
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
			boolean hasErrors = false;
			
			if (Actions.BUSCAR.equalsIgnoreCase(action)) {
				Usuario user=(Usuario) SessionManager.get(request, SessionAttributeNames.USER);
				// Recuperar parametros
				String nombre = request.getParameter(ParameterNames.NOMBRE);
				String[] categorias=request.getParameterValues(ParameterNames.CATEGORIA);
				String creador=request.getParameter(ParameterNames.CREADOR);
				String[] plataforma=request.getParameterValues(ParameterNames.PLATAFORMA);
				String[] idioma=request.getParameterValues(ParameterNames.IDIOMA); 
				String fecha=request.getParameter(ParameterNames.FECHA);
				
				
				String nombreValid= LimpiezaValidacion.validNombreJuego(nombre);
				
				// Limpiar
				// ...

				// Validar Arrays e creador
				//..

				// if hasErrors

				// else
				if (hasErrors) {
					
					target = ViewPaths.BUSCADOR;
				} else {
					int page = WebUtils.
							getPageNumber(request.getParameter(ParameterNames.PAGE), 1);
					
					JuegoCriteria jc=new JuegoCriteria();
					
					
						if(nombreValid!=null && nombreValid!="") {
							jc.setNombre(nombreValid);
						}
						if(categorias!=null) {
							//jc.setCategoria(ArrayUtils.arrayCategoria(categoria));
							jc.setCategoriaIDs(ArrayUtils.asInteger(categorias));
						}
						if(idioma!=null) {
							jc.setIdiomaIDs(idioma);
						}
						if(plataforma!=null) {
							jc.setPlataformaIDs(ArrayUtils.asInteger(plataforma));
						}
						if(creador!=null) {
							Integer creadorValid=Integer.valueOf(creador);
							jc.setIdCreador(creadorValid);
						}if(fecha!=null) {
							Integer fechaformat=Integer.valueOf(fecha);
							jc.setFechaLanzamiento(fechaformat);
						}else {
							jc.setNombre(nombreValid);
						}
						
						
					Resultados<Juego> resultados = jservice.findByJuegoCriteria(jc,"ES",(page-1)*pageSize+1, pageSize);
					
					// Buscar juegos que tiene incluidos en la biblioteca
					if(user!=null) {
						Resultados<ItemBiblioteca> results=userv.findByUsuario(user.getEmail(),(page-1)*pageSize+1, pageSize);
						List<Juego> j=new ArrayList<Juego>();
						Iterator iterador= ((Iterable<Throwable>) results).iterator();
						for (ItemBiblioteca it: results) {
							Juego addjuego=jservice.findById(it.getIdJuego(), "ES");
							j.add(addjuego);
						}
						request.setAttribute(AttributeNames.BIBLIOTECA_RESULTADOS, j);
					}
					
					request.setAttribute(AttributeNames.PRODUCTO_RESULTADOS, resultados.getResultados());
					request.setAttribute(AttributeNames.TOTAL, resultados.getTotal());
					
					int totalPages = (int) Math.ceil(resultados.getTotal()/(double)pageSize);
					int firstPagedPage = Math.max(1, page-pagingPageCount);
					int lastPagedPage = Math.min(totalPages, page+pagingPageCount);
					request.setAttribute(ParameterNames.PAGE, page);
					request.setAttribute(AttributeNames.TOTAL_PAGES, totalPages);
					request.setAttribute(AttributeNames.FIRST_PAGED_PAGES, firstPagedPage);
					request.setAttribute(AttributeNames.LAST_PAGED_PAGES, lastPagedPage);
					
					
					target = ViewPaths.BUSCADOR;
				
				}
			}else if(Actions.JUEGO.equalsIgnoreCase(action)) {
				Usuario user=(Usuario) SessionManager.get(request, SessionAttributeNames.USER);
				String id=request.getParameter(ParameterNames.ID);
				Integer idJuego= Integer.valueOf(id);
				
				Juego juego = jservice.findById(idJuego, "ES");
				Creador creador=crservice.findbyIdCreador(juego.getIdCreador());
				
				//Listado de comentarios
				List<ItemBiblioteca> comentarios=jservice.findByJuego(juego.getIdJuego());
				
				// Buscar juegos que tiene incluidos en la biblioteca para mostrar o no el boton de a�adir
				if(user!=null) {
					Resultados<ItemBiblioteca> results=userv.findByUsuario(user.getEmail(),(1)*pageSize+1, pageSize);
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
				request.setAttribute(AttributeNames.CREADOR_JUEGO, creador);
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
