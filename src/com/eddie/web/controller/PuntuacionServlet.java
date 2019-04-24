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
import com.eddie.ecommerce.model.ItemBiblioteca;
import com.eddie.ecommerce.model.Usuario;
import com.eddie.ecommerce.service.UsuarioService;
import com.eddie.ecommerce.service.impl.UsuarioServiceImpl;
import com.eddie.web.util.SessionAttributeNames;
import com.eddie.web.util.SessionManager;

/**
 * Servlet implementation class Puntuacion
 */
@WebServlet("/puntuacion")
public class PuntuacionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UsuarioService usuarioService;   
    private static Logger logger = LogManager.getLogger(PuntuacionServlet.class);  
    
    public PuntuacionServlet() {
        super();
        usuarioService=new UsuarioServiceImpl();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario user= (Usuario) SessionManager.get(request, SessionAttributeNames.USER);
		
		String id= request.getParameter(ParameterNames.ID);
		Integer idJuego= Integer.valueOf(id);
		String puntuacion=  request.getParameter(ParameterNames.PUNTUACION);
		Integer puntuacionInteger= Integer.valueOf(puntuacion);
		ItemBiblioteca puntuacionUsuario=null;
		
		try {
			puntuacionUsuario=usuarioService.findByIdEmail(user.getEmail(), idJuego);
			puntuacionUsuario.setPuntuacion(puntuacionInteger);
			usuarioService.update(puntuacionUsuario);
			
			response.getOutputStream().write(puntuacionUsuario.getPuntuacion());
			
		} catch (DataException e) {
			logger.info(e);
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
