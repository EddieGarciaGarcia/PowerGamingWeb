package com.eddie.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.eddie.web.util.ArrayUtils;
import com.eddie.web.util.LimpiezaValidacion;
import com.eddie.web.util.SessionAttributeNames;
import com.eddie.web.util.SessionManager;
import com.eddie.web.util.WebConstants;
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
@WebServlet("/juego")
public class JuegoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static int pageSize = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
						ConfigurationParameterNames.RESULTS_PAGE_SIZE_DEFAULT)); 

	private static int pagingPageCount = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
						ConfigurationParameterNames.RESULTS_PAGING_PAGE_COUNT)); 
	
	private static Logger logger = LogManager.getLogger(JuegoServlet.class);

	private JuegoService juegoService = null;
	private UsuarioService usuarioService=null;
	private CreadorService creadorService=null;

	
	public JuegoServlet() {
		super();
		juegoService = new JuegoServiceImpl();
		usuarioService=new UsuarioServiceImpl();
		creadorService= new CreadorServiceImpl();

	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// MAÃ‘Ã� SEGUIREMOS...
			String action = request.getParameter(ParameterNames.ACTION);

			if (logger.isDebugEnabled()) {
				logger.debug("Action {}: {}", action, ToStringBuilder.reflectionToString(request.getParameterMap()));
			}

			String target = null;
			boolean redirect=false;
			boolean hasErrors = false;			
			Usuario user = (Usuario) SessionManager.get(request, SessionAttributeNames.USER);
			String idiomaPagina=SessionManager.get(request,WebConstants.USER_LOCALE).toString().substring(0,2).toUpperCase();
			
			if (Actions.BUSCAR.equalsIgnoreCase(action)) {				
				// Recuperar parametros
				String nombre = request.getParameter(ParameterNames.NOMBRE);
				String[] categorias=request.getParameterValues(ParameterNames.CATEGORIA);
				String creador=request.getParameter(ParameterNames.CREADOR);
				String[] plataforma=request.getParameterValues(ParameterNames.PLATAFORMA);
				String[] idioma=request.getParameterValues(ParameterNames.IDIOMA); 
				String fecha=request.getParameter(ParameterNames.FECHA);
				
				StringBuilder targetString=new StringBuilder();
				String nombreValid= LimpiezaValidacion.validNombreJuego(nombre);
				
				if (hasErrors) {
					
					target = ViewPaths.BUSCADOR;
				} else {
					int page = WebUtils.
							getPageNumber(request.getParameter(ParameterNames.PAGE), 1);
					
					JuegoCriteria jc=new JuegoCriteria();
					
					
						if(nombreValid!=null && nombreValid!="") {
							jc.setNombre(nombreValid);
							targetString.append("&nombre="+jc.getNombre());
						}
						if(categorias!=null) {
							jc.setCategoriaIDs(ArrayUtils.asInteger(categorias));
							for(Integer i : jc.getCategoriaIDs()) {
								targetString.append("&categoria="+i);
							}
						}
						if(idioma!=null) {
							jc.setIdiomaIDs(idioma);
							for(String i : jc.getIdiomaIDs()) {
								targetString.append("&idioma="+i);
							}
						}
						if(plataforma!=null) {
							jc.setPlataformaIDs(ArrayUtils.asInteger(plataforma));
							for(Integer i : jc.getPlataformaIDs()) {
								targetString.append("&plataforma="+i);
							}
						}
						if(creador!=null) {
							Integer creadorValid=Integer.valueOf(creador);
							jc.setIdCreador(creadorValid);
							
							targetString.append("&creador="+jc.getIdCreador());
								
						}if(fecha!=null) {
							Integer fechaformat=Integer.valueOf(fecha);
							jc.setFechaLanzamiento(fechaformat);
							targetString.append("&fecha="+jc.getFechaLanzamiento());
						}else {
							jc.setNombre(nombreValid);
						}
						
						
					Resultados<Juego> resultados = juegoService.findByJuegoCriteria(jc,idiomaPagina,(page-1)*pageSize+1, pageSize);
				
					List<Integer> idsJuegos = resultados.getResultados().stream().map(Juego::getIdJuego).collect(Collectors.toList());
						// Buscar juegos que tiene incluidos en la biblioteca
					if(user!=null) {						
						List<Integer> idsJuegosEnBiblioteca = usuarioService.existsInBiblioteca(user.getEmail(), idsJuegos);
							
						request.setAttribute(AttributeNames.PRODUCTOS_EN_BIBLIOTECA, idsJuegosEnBiblioteca);
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
					
					SessionManager.set(request, SessionAttributeNames.URL, targetString.toString());
					
					target = ViewPaths.BUSCADOR;
					
				}
			}else if(Actions.JUEGO.equalsIgnoreCase(action)) {
				
				String id=request.getParameter(ParameterNames.ID);
				Integer idJuego= Integer.valueOf(id);
				
				Juego juego = juegoService.findById(idJuego, idiomaPagina);
				Creador creador=creadorService.findbyIdCreador(juego.getIdCreador());
				
				//Puntuacion
				
				Integer puntuacion= juegoService.puntuacion(idJuego);
				
				//Listado de comentarios
				List<ItemBiblioteca> comentarios=juegoService.findByJuego(juego.getIdJuego());
				
				
				// Buscar juegos que tiene incluidos en la biblioteca para mostrar o no el boton de a�adir
				if(user!=null) {
					Boolean idJuegoEnBiblioteca = usuarioService.existsInBiblioteca(user.getEmail(), idJuego);

					request.setAttribute(AttributeNames.PRODUCTOS_EN_BIBLIOTECA, idJuegoEnBiblioteca);
				}
				Map<String, ItemBiblioteca> comentario=null;
				if(comentarios!=null && !comentarios.isEmpty()) {
					comentario= new HashMap<String, ItemBiblioteca>();
					for(ItemBiblioteca i:comentarios){
							Usuario u=usuarioService.findById(i.getEmail());
							comentario.put(u.getNombreUser(), i);
					}	
				}	
				request.setAttribute(AttributeNames.PUNTUACION, puntuacion);
				request.setAttribute(AttributeNames.COMENTARIOS_JUEGO, comentario);
				request.setAttribute(AttributeNames.CREADOR_JUEGO, creador);
				request.setAttribute(AttributeNames.PRODUCTO_RESULTADOS, juego);
				
				target =ViewPaths.JUEGO;
			}else if(Actions.ADDCOMENTARIO.equalsIgnoreCase(action)) {
				String comentario= request.getParameter(ParameterNames.COMENTARIO);
				String id=request.getParameter(ParameterNames.ID);
				Integer idJuego= Integer.valueOf(id);
				
				ItemBiblioteca it=new ItemBiblioteca();
				it.setIdJuego(idJuego);
				it.setComentario(comentario);
				it.setEmail(user.getEmail());
				Date d= new Date();
				it.setFechaComentario(new java.sql.Date(d.getTime()));
				juegoService.addComent(it);
			
				target = request.getHeader(ViewPaths.REFERER);
				redirect=true;
				
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
			} catch (DataException e) {
				logger.info(e.getMessage(),e);
			} 
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
