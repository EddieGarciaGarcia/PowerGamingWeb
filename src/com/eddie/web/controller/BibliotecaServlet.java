package com.eddie.web.controller;

import java.io.IOException;
import java.util.List;
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
import com.eddie.ecommerce.service.TipoEdicionService;
import com.eddie.ecommerce.exceptions.DataException;
import com.eddie.ecommerce.model.Edicion;
import com.eddie.ecommerce.model.Formato;
import com.eddie.ecommerce.model.ItemBiblioteca;
import com.eddie.ecommerce.model.Juego;
import com.eddie.ecommerce.model.TipoEdicion;
import com.eddie.ecommerce.model.Usuario;
import com.eddie.ecommerce.service.EdicionService;
import com.eddie.ecommerce.service.FormatoService;
import com.eddie.ecommerce.service.JuegoService;
import com.eddie.ecommerce.service.UsuarioService;
import com.eddie.ecommerce.service.impl.EdicionServiceImpl;
import com.eddie.ecommerce.service.impl.FormatoServiceImpl;
import com.eddie.ecommerce.service.impl.JuegoServiceImpl;
import com.eddie.ecommerce.service.impl.TipoEdicionServiceImpl;
import com.eddie.ecommerce.service.impl.UsuarioServiceImpl;
import com.eddie.web.cache.WebCache;
import com.eddie.web.config.ConfigurationManager;
import com.eddie.web.config.ConfigurationParameterNames;
import com.eddie.web.model.Errors;
import com.eddie.web.util.SessionAttributeNames;
import com.eddie.web.util.SessionManager;
import com.eddie.web.util.WebConstants;
import com.eddie.web.util.WebUtils;

/**
 * Servlet implementation class BibliotecaServlet
 */
@WebServlet("/biblioteca")
public class BibliotecaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static int pageSize = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
						ConfigurationParameterNames.RESULTS_PAGE_SIZE_DEFAULT)); 

	private static int pagingPageCount = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
						ConfigurationParameterNames.RESULTS_PAGING_PAGE_COUNT));
	
	private static Logger logger = LogManager.getLogger(UsuarioServlet.class);  
	private UsuarioService usuarioService=null;
	private JuegoService juegoService = null;
	
    public BibliotecaServlet() {
        super();
        usuarioService=new UsuarioServiceImpl();
        juegoService = new JuegoServiceImpl();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String action = request.getParameter(ParameterNames.ACTION);
			String idiomaPagina=SessionManager.get(request,WebConstants.USER_LOCALE).toString().substring(0,2).toUpperCase();
			
			if (logger.isDebugEnabled()) {
				logger.debug("Action {}: {}", action, ToStringBuilder.reflectionToString(request.getParameterMap()));
			}
			
			Errors errors = new Errors(); 
			String target = null;
			boolean redirect=false;
			Usuario user=(Usuario) SessionManager.get(request, SessionAttributeNames.USER);
			
			if(Actions.BIBLIOTECA.equalsIgnoreCase(action)) {
				String email = request.getParameter(ParameterNames.EMAIL);
				
				int page = WebUtils.
						getPageNumber(request.getParameter(ParameterNames.PAGE), 1);
				
				Resultados<ItemBiblioteca> results=usuarioService.findByUsuario(email,(page-1)*pageSize+1, pageSize);
				
				//Lambda expresion stream collectors
				List<Integer> juegoIDs = results.getResultados().stream().map(ItemBiblioteca::getIdJuego).collect(Collectors.toList());
				
				List<Juego> juegos =juegoService.findByIDs(juegoIDs, idiomaPagina);
				
				request.setAttribute(AttributeNames.LISTADO_RESULTADOS_BIBLIOTECA, juegos);
				request.setAttribute(AttributeNames.TOTAL, results.getTotal());
				
				int totalPages = (int) Math.ceil(results.getTotal()/(double)pageSize);
				int firstPagedPage = Math.max(1, page-pagingPageCount);
				int lastPagedPage = Math.min(totalPages, page+pagingPageCount);
				request.setAttribute(ParameterNames.PAGE, page);
				request.setAttribute(AttributeNames.TOTAL_PAGES, totalPages);
				request.setAttribute(AttributeNames.FIRST_PAGED_PAGES, firstPagedPage);
				request.setAttribute(AttributeNames.LAST_PAGED_PAGES, lastPagedPage);
				
				
				target= ViewPaths.BIBLIOTECA;
				
			}else if(Actions.DELETEJUEGO.equalsIgnoreCase(action)) {
				String idJuego = request.getParameter(ParameterNames.ID);
				
				Integer id=Integer.valueOf(idJuego);
				
				usuarioService.borrarJuegoBiblioteca(user.getEmail(), id);
				
				target= ControllerPaths.BIBLIOTECA+"?"+ParameterNames.ACTION+"="+Actions.BIBLIOTECA+"&"+ParameterNames.EMAIL+"="+user.getEmail();
				redirect=true;
			}else if(Actions.ADDJUEGO.equalsIgnoreCase(action)) {
				String idJuego = request.getParameter(ParameterNames.ID);
				Integer id=Integer.valueOf(idJuego);
			
				ItemBiblioteca it= new ItemBiblioteca();
				it.setEmail(user.getEmail());
				it.setIdJuego(id);
				
				Boolean results=usuarioService.existsInBiblioteca(user.getEmail(),id);

				if(results==false) {
					usuarioService.addJuegoBiblioteca(user.getEmail(), it);	
					target=ControllerPaths.JUEGO+"?"+ParameterNames.ACTION+"="+Actions.JUEGO+"&"+ParameterNames.ID+"="+id;
				}else if(results==true){
					target=ControllerPaths.JUEGO+"?"+ParameterNames.ACTION+"="+Actions.JUEGO+"&"+ParameterNames.ID+"="+id;
					
				}
				redirect=true;
			}
			else {
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
