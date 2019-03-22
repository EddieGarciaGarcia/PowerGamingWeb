package com.eddie.web.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.eddie.ecommerce.exceptions.DataException;
import com.eddie.ecommerce.model.ItemBiblioteca;
import com.eddie.ecommerce.model.Juego;
import com.eddie.ecommerce.model.Usuario;
import com.eddie.ecommerce.service.JuegoService;
import com.eddie.ecommerce.service.UsuarioService;
import com.eddie.ecommerce.service.impl.JuegoServiceImpl;
import com.eddie.ecommerce.service.impl.UsuarioServiceImpl;
import com.eddie.web.config.ConfigurationManager;
import com.eddie.web.config.ConfigurationParameterNames;
import com.eddie.web.model.Errors;
import com.eddie.web.util.SessionAttributeNames;
import com.eddie.web.util.SessionManager;
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
	private UsuarioService userv=null;
	private JuegoService jservice = null;
	
    public BibliotecaServlet() {
        super();
        userv=new UsuarioServiceImpl();
        jservice = new JuegoServiceImpl();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String action = request.getParameter(ParameterNames.ACTION);
	
			if (logger.isDebugEnabled()) {
				logger.debug("Action {}: {}", action, ToStringBuilder.reflectionToString(request.getParameterMap()));
			}
			
			Errors errors = new Errors(); 
			String target = null;
			boolean redirect=false;
			
			if(Actions.BIBLIOTECA.equalsIgnoreCase(action)) {
				String email = request.getParameter(ParameterNames.EMAIL);
				
				int page = WebUtils.
						getPageNumber(request.getParameter(ParameterNames.PAGE), 1);
				
				Resultados<ItemBiblioteca> results=userv.findByUsuario(email,(page-1)*pageSize+1, pageSize);
				
				//Lambda expresion stream collectors
				List<Integer> juegoIDs = results.getResultados().stream().map(ItemBiblioteca::getIdJuego).collect(Collectors.toList());
				
				List<Juegos> juegos =jservice.findByIDs(juegosIDS,"ES");
				
				request.setAttribute(AttributeNames.BIBLIOTECA_RESULTADOS, juegos);
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
				Usuario user=(Usuario) SessionManager.get(request, SessionAttributeNames.USER);
				Integer id=Integer.valueOf(idJuego);
				
				userv.borrarJuegoBiblioteca(user.getEmail(), id);
				
				target= ControllerPaths.BIBLIOTECA+"?"+ParameterNames.ACTION+"="+Actions.BIBLIOTECA+"&"+ParameterNames.EMAIL+"="+user.getEmail();
				redirect=true;
			}else if(Actions.ADDJUEGO.equalsIgnoreCase(action)) {
				String idJuego = request.getParameter(ParameterNames.ID);
				Usuario user=(Usuario) SessionManager.get(request, SessionAttributeNames.USER);
				Integer id=Integer.valueOf(idJuego);
				
				Boolean anhadir=false;
			
				ItemBiblioteca it= new ItemBiblioteca();
				it.setEmail(user.getEmail());
				it.setIdJuego(id);
				
				List<ItemBiblioteca> results=userv.findByUsuarioComprobar(user.getEmail());

				if(results.size()<1) {
					userv.addJuegoBiblioteca(it);
					target=ControllerPaths.PRODUCTO+"?"+ParameterNames.ACTION+"="+Actions.BUSCAR+"&"+ParameterNames.ID+"="+id;
				}else if(results.size()>=1){
					for(ItemBiblioteca item: results) {
						if(item.getIdJuego()==id) {
							anhadir=true;
						}
					}			
					if(anhadir==false) {
						userv.addJuegoBiblioteca(it);	
						target=ControllerPaths.PRODUCTO+"?"+ParameterNames.ACTION+"="+Actions.JUEGO+"&"+ParameterNames.ID+"="+id;
					}else if(anhadir==true){
						target=ControllerPaths.PRODUCTO+"?"+ParameterNames.ACTION+"="+Actions.JUEGO+"&"+ParameterNames.ID+"="+id;
					}
				}
				redirect=true;
			}
			else {
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
		} catch (SQLException e) {
			logger.info(e.getMessage(),e);
		} catch (DataException e) {
			logger.info(e.getMessage(),e);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
