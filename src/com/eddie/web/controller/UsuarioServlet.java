package com.eddie.web.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eddie.ecommerce.exceptions.DataException;
import com.eddie.ecommerce.model.Pais;
import com.eddie.ecommerce.model.Usuario;
import com.eddie.ecommerce.service.PaisService;
import com.eddie.ecommerce.service.UsuarioService;
import com.eddie.ecommerce.service.impl.PaisServiceImpl;
import com.eddie.ecommerce.service.impl.UsuarioServiceImpl;
import com.eddie.web.model.Errors;
import com.eddie.web.util.Actions;
import com.eddie.web.util.AttributeNames;
import com.eddie.web.util.DateUtils;
import com.eddie.web.util.ErrorCodes;
import com.eddie.web.util.ParameterNames;
import com.eddie.web.util.SessionAttributeNames;
import com.eddie.web.util.SessionManager;
import com.eddie.web.util.ViewPaths;

/**
 * Servlet implementation class UsuarioServlet
 */
@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static Logger logger = LogManager.getLogger(UsuarioServlet.class);
	
	private UsuarioService userv=null;
	private PaisService pserv=null; 
	
    public UsuarioServlet() {
        super();
        userv=new UsuarioServiceImpl();
        pserv=new PaisServiceImpl();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String action = request.getParameter(ParameterNames.ACTION);

			if (logger.isDebugEnabled()) {
				logger.debug("Action {}: {}", action, ToStringBuilder.reflectionToString(request.getParameterMap()));
			}
			
			 
			Errors errors = new Errors(); 
			String target = null;
			
			
			
			if(Actions.REGISTRO.equalsIgnoreCase(action)) {
				String nombre=request.getParameter(ParameterNames.NOMBRE);
				String apellido1=request.getParameter(ParameterNames.APELLIDO1);
				String apellido2=request.getParameter(ParameterNames.APELLIDO2);
				String email=request.getParameter(ParameterNames.EMAIL);
				String telefono=request.getParameter(ParameterNames.TELEFONO);
				String password=request.getParameter(ParameterNames.PASSWORD);
				String fecha=request.getParameter(ParameterNames.FECHANACIMIENTO);
				String genero=request.getParameter(ParameterNames.GENERO);
				
				Usuario u= new Usuario();
				
				SimpleDateFormat sdf=(SimpleDateFormat) DateUtils.SHORT_FORMAT_DATE;
				Date fechaformat=sdf.parse(fecha);
				
				u.setNombre(nombre);
				u.setApellido1(apellido1);
				u.setApellido2(apellido2);
				u.setEmail(email);
				u.setTelefono(telefono);
				u.setPassword(password);
				u.setFechaNacimiento(fechaformat);
				u.setGenero(genero);
				
				userv.create(u);
				target = ViewPaths.LOGIN;
			}
			
			
			if (Actions.LOGIN.equalsIgnoreCase(action)) {
				
				// Recuperacion
				String email = request.getParameter(ParameterNames.EMAIL);
				String password = request.getParameter(ParameterNames.PASSWORD);
				
				// Limpieza
				// ... 
	 
				
				// Validacion 
				// ...			
				if (StringUtils.isEmpty(email)) {
					errors.add(ParameterNames.EMAIL,ErrorCodes.MANDATORY_PARAMETER);
				}
				
				Usuario usuario = null;
				if (!errors.hasErrors()) {
					
						usuario = userv.login(email, password);
					
				}
				if(usuario == null) {
					errors.add(ParameterNames.ACTION,ErrorCodes.AUTHENTICATION_ERROR);
				}
				
				if (errors.hasErrors()) {	
					if (logger.isDebugEnabled()) {
						logger.debug("Autenticacion fallida: {}", errors);
					}
					errors.add(ParameterNames.ACTION,ErrorCodes.AUTHENTICATION_ERROR);				
					request.setAttribute(AttributeNames.ERRORS, errors);				
					target = ViewPaths.LOGIN;				
				} else {				
					SessionManager.set(request, SessionAttributeNames.USER, usuario);		
					target = ViewPaths.HOME;				
				}
			} else if (Actions.LOGOUT.equalsIgnoreCase(action)) {
				SessionManager.set(request, SessionAttributeNames.USER, null);
				target = ViewPaths.HOME;			
			} else if(Actions.PREREGISTRO.equalsIgnoreCase(action)){
				List<Pais> paises = pserv.findAll();
				request.setAttribute(AttributeNames.PAISES, paises);
				target = ViewPaths.REGISTRO;
			}else if(Actions.REGISTRO.equalsIgnoreCase(action)) {

			}else {
			
				// Mmm...
				logger.error("Action desconocida");
				// target ?
			}
			request.getRequestDispatcher(target).forward(request, response);
			} catch (SQLException e) {	
				e.printStackTrace();
			} catch (DataException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
