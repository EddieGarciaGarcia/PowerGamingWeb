package com.eddie.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eddie.ecommerce.model.Usuario;
import com.eddie.web.util.SessionAttributeNames;
import com.eddie.web.util.SessionManager;

/**
 * Servlet implementation class Puntuacion
 */
@WebServlet("/Puntuacion")
public class PuntuacionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public PuntuacionServlet() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Usuario user= (Usuario) SessionManager.get(request, SessionAttributeNames.USER);
		String id= request.getParameter(ParameterNames.ID);
		Integer idJuego= Integer.valueOf(id);
		String puntuacion=  request.getParameter(ParameterNames.PUNTUACION);
		Integer puntuacionInteger= Integer.valueOf(puntuacion);
		
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
