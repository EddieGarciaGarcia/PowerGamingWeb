package com.eddie.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eddie.web.model.Carrito;
import com.eddie.web.util.SessionAttributeNames;
import com.eddie.web.util.SessionManager;

/**
 * Servlet implementation class HistorialServlet
 */
@WebServlet("/HistorialServlet")
public class HistorialServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public HistorialServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Carrito carrito = (Carrito) SessionManager.get(request, SessionAttributeNames.CARRITO);
		
		String action= request.getParameter(ParameterNames.ACTION);
		String target= null;
		
		if(Actions.ADDHISTORIAL.equals(action)) {
			
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
