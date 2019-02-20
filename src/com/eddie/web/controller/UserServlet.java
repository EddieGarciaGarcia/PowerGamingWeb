package com.eddie.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eddie.ecommerce.model.Usuario;
import com.eddie.ecommerce.service.UsuarioService;
import com.eddie.ecommerce.service.impl.UsuarioServiceImpl;
import com.eddie.web.util.Actions;
import com.eddie.web.util.AttributeNames;
import com.eddie.web.util.ParameterNames;
import com.eddie.web.util.SessionAttributeNames;
import com.eddie.web.util.SessionManager;
import com.eddie.web.util.Validacion;
import com.eddie.web.util.ViewPaths;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsuarioService userv=null; 
	private static Logger logger = LogManager.getLogger(UsuarioServlet.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        userv=new UsuarioServiceImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter(ParameterNames.ACTION);
		String target = null;
		boolean redirect=false;
		
		if (logger.isDebugEnabled()) {
			logger.debug("Action {}: {}", action, ToStringBuilder.reflectionToString(request.getParameterMap()));
		}
		
		
		if(Actions.PRECONFIGURACION.equalsIgnoreCase(action)) {
			
			request.setAttribute(AttributeNames.USER, SessionManager.get(request, SessionAttributeNames.USER));

			target = ViewPaths.MYACCOUNT;
		
		}else if(Actions.CONFIGURACION.equalsIgnoreCase(action)) {
			
			String nombre=request.getParameter(ParameterNames.NOMBRE);
			String apellido1=request.getParameter(ParameterNames.APELLIDO1);
			String apellido2=request.getParameter(ParameterNames.APELLIDO2);
			String telefono=request.getParameter(ParameterNames.TELEFONO);
			String passwordconfig=request.getParameter(ParameterNames.PASSWORD);
			String nombreUser=request.getParameter(ParameterNames.NOMBREUSER);
			
			Usuario userupdate= new Usuario();
			
			if(nombre!=null) {
				userupdate.setNombre(Validacion.validNombre(nombre));
			}else if(apellido1!=null) {
				userupdate.setApellido1(Validacion.validApellido1(apellido1));
			}else if(apellido2!=null) {
				userupdate.setApellido2(Validacion.validApellido2(apellido2));
			}else if(telefono!=null) {
				userupdate.setTelefono(telefono);
			}else if(passwordconfig!=null) {
				userupdate.setPassword(Validacion.validPassword(passwordconfig));
			}else if(nombreUser!=null) {
				userupdate.setNombreUser(nombreUser);
			}

			userv.update(userupdate);
			
			target = request.getContextPath()+ViewPaths.MYACCOUNT;
			redirect=true;
			else {
				// Mmm...
				logger.error("Action desconocida");
				// target ?
			}
			if(redirect==true) {
				logger.info("Redirect to "+target);
				response.sendRedirect(target);
			}else {
				logger.info("forwarding to "+target);
				request.getRequestDispatcher(target).forward(request, response);
			}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
