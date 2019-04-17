package com.eddie.web.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import com.eddie.ecommerce.model.Direccion;
import com.eddie.ecommerce.model.Pais;
import com.eddie.ecommerce.model.Provincia;
import com.eddie.ecommerce.model.Usuario;
import com.eddie.ecommerce.service.MailService;
import com.eddie.ecommerce.service.PaisService;
import com.eddie.ecommerce.service.ProvinciaService;
import com.eddie.ecommerce.service.UsuarioService;
import com.eddie.ecommerce.service.impl.MailServiceImpl;
import com.eddie.ecommerce.service.impl.PaisServiceImpl;
import com.eddie.ecommerce.service.impl.ProvinciaServiceImpl;
import com.eddie.ecommerce.service.impl.UsuarioServiceImpl;
import com.eddie.web.model.ErrorCodes;
import com.eddie.web.model.Errors;
import com.eddie.web.controller.Actions;
import com.eddie.web.controller.AttributeNames;
import com.eddie.web.util.DateUtils;
import com.eddie.web.controller.ParameterNames;
import com.eddie.web.util.SessionAttributeNames;
import com.eddie.web.util.SessionManager;
import com.eddie.web.util.CookieManager;
import com.eddie.web.util.LocaleManager;
import com.eddie.web.util.WebConstants;
import com.eddie.web.util.LimpiezaValidacion;
import com.eddie.web.controller.ViewPaths;

/**
 * Servlet implementation class UsuarioServlet
 */
@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static Logger logger = LogManager.getLogger(UsuarioServlet.class);
	
	private UsuarioService userv=null;
	private PaisService paisService=null; 
	private MailService mservice=null;
	private ProvinciaService provinciaService=null;
	
	
    public UsuarioServlet() {
        super();
        userv=new UsuarioServiceImpl();
        paisService=new PaisServiceImpl();
        mservice=new MailServiceImpl();
        provinciaService=new ProvinciaServiceImpl();
   
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
			
			if (Actions.LOGIN.equalsIgnoreCase(action)) {
				
				// Recuperacion
				String email = request.getParameter(ParameterNames.EMAIL);
				String password = request.getParameter(ParameterNames.PASSWORD);
				
				String emailValid = LimpiezaValidacion.validEmail(email);
				String passwordValid = LimpiezaValidacion.validPassword(password);
				
				if (StringUtils.isEmpty(emailValid)) {
					errors.add(ParameterNames.EMAIL,ErrorCodes.MANDATORY_PARAMETER);
				}
				if (StringUtils.isEmpty(password)) {
					errors.add(ParameterNames.PASSWORD, ErrorCodes.MANDATORY_PARAMETER);
				}
				
				Usuario usuario = null;
				if (!errors.hasErrors()) {
					usuario = userv.login(emailValid, passwordValid);
					
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
					target = request.getContextPath()+ViewPaths.INICIO;
					redirect=true;
				}
				
			}else if (Actions.LOGOUT.equalsIgnoreCase(action)) {
				SessionManager.set(request, SessionAttributeNames.USER, null);
				target = request.getContextPath()+ViewPaths.INICIO;
				redirect=true;
			} else if(Actions.PREREGISTRO.equalsIgnoreCase(action)){
				List<Pais> paises = paisService.findAll();
				request.setAttribute(AttributeNames.PAISES, paises);
				List<Provincia> provincias = provinciaService.findAllByIdPais(1);
				request.setAttribute(AttributeNames.PROVINCIA, provincias);
				target = ViewPaths.REGISTRO;
			}else if(Actions.REGISTRO.equalsIgnoreCase(action)) {
				String nombre=request.getParameter(ParameterNames.NOMBRE);
				String apellido1=request.getParameter(ParameterNames.APELLIDO1);
				String apellido2=request.getParameter(ParameterNames.APELLIDO2);
				String email=request.getParameter(ParameterNames.EMAIL);
				String telefono=request.getParameter(ParameterNames.TELEFONO);
				String password=request.getParameter(ParameterNames.PASSWORD);
				String fecha=request.getParameter(ParameterNames.FECHANACIMIENTO);
				String genero=request.getParameter(ParameterNames.GENERO);
				
				Usuario u= new Usuario();
				
				SimpleDateFormat sdf=(SimpleDateFormat) DateUtils.FORMATODATA;
				Date fechaformat=sdf.parse(fecha);
				
				u.setNombre(LimpiezaValidacion.validNombre(nombre));
				u.setApellido1(LimpiezaValidacion.validApellido1(apellido1));
				u.setApellido2(LimpiezaValidacion.validApellido2(apellido2));
				u.setEmail(LimpiezaValidacion.validEmail(email));
				u.setTelefono(LimpiezaValidacion.validTelefono(telefono));
				u.setPassword(LimpiezaValidacion.validPassword(password));
				u.setFechaNacimiento(fechaformat);
				u.setGenero(LimpiezaValidacion.validGenero(genero));
				u.setNombreUser(nombre+apellido1.charAt(0)+apellido2.charAt(0));
				
				userv.create(u);
				
				Direccion direccion= new Direccion();
				
				String pais=request.getParameter(ParameterNames.PAIS);
				Integer idPais= Integer.valueOf(pais);
				List<Provincia> provincias = provinciaService.findAllByIdPais(idPais);
				request.setAttribute(AttributeNames.PROVINCIA, provincias);
				
				String provincia=request.getParameter(ParameterNames.PROVINCIA);
				Integer idProvincia=Integer.valueOf(provincia);
				String localidad=request.getParameter(ParameterNames.LOCALIDAD);
				String codigoPostal=request.getParameter(ParameterNames.CODPOSTAL);
				String calle=request.getParameter(ParameterNames.NUMERO);
				String piso = request.getParameter(ParameterNames.PISO);
				String numero=request.getParameter(ParameterNames.NUMERO);
				
				direccion.setIdprovincia(idProvincia);
				direccion.setLocalidad(localidad);
				direccion.setCodigoPostal(codigoPostal);
				direccion.setCalle(calle);
				direccion.setPiso(piso);
				direccion.setEmail(u.getEmail());
				direccion.setNumero(numero);
				
				userv.createDireccion(direccion);
				
				target = request.getContextPath()+ViewPaths.LOGIN;
				redirect=true;
			}else if(Actions.PRECONFIGURACION.equalsIgnoreCase(action)) {
				List<Pais> paises = paisService.findAll();
				request.setAttribute(AttributeNames.PAISES, paises);
				List<Provincia> provincias = provinciaService.findAllByIdPais(1);
				request.setAttribute(AttributeNames.PROVINCIA, provincias);
				Usuario user=(Usuario) SessionManager.get(request, SessionAttributeNames.USER);
				request.setAttribute(AttributeNames.USER, user);

				target = ViewPaths.MYACCOUNT;
			
			}else if(Actions.CONFIGURACION.equalsIgnoreCase(action)) {
				Usuario user=(Usuario) SessionManager.get(request, SessionAttributeNames.USER);
				String nombre=request.getParameter(ParameterNames.NOMBRE);
				String apellido1=request.getParameter(ParameterNames.APELLIDO1);
				String apellido2=request.getParameter(ParameterNames.APELLIDO2);
				String telefono=request.getParameter(ParameterNames.TELEFONO);
				String passwordconfig=request.getParameter(ParameterNames.PASSWORD);
				String nombreUser=request.getParameter(ParameterNames.NOMBREUSER);
				
				Usuario userupdate=new Usuario();
				
				userupdate.setNombre(LimpiezaValidacion.validNombre(nombre));
				userupdate.setApellido1(LimpiezaValidacion.validApellido1(apellido1));
				userupdate.setApellido2(LimpiezaValidacion.validApellido2(apellido2));
				userupdate.setTelefono(LimpiezaValidacion.validTelefono(telefono));
				userupdate.setPassword(LimpiezaValidacion.validPassword(passwordconfig));
				userupdate.setNombreUser(LimpiezaValidacion.validNombreUser(nombreUser));
				userupdate.setEmail(user.getEmail());
				
				Direccion direccion= new Direccion();
				
				String pais=request.getParameter(ParameterNames.PAIS);
				Integer idPais= Integer.valueOf(pais);
				List<Provincia> provincias = provinciaService.findAllByIdPais(idPais);
				request.setAttribute(AttributeNames.PROVINCIA, provincias);
				
				String provincia=request.getParameter(ParameterNames.PROVINCIA);
				Integer idProvincia=Integer.valueOf(provincia);
				String localidad=request.getParameter(ParameterNames.LOCALIDAD);
				String codigoPostal=request.getParameter(ParameterNames.CODPOSTAL);
				String calle=request.getParameter(ParameterNames.NUMERO);
				String piso = request.getParameter(ParameterNames.PISO);
				String numero=request.getParameter(ParameterNames.NUMERO);
				
				direccion.setIdprovincia(idProvincia);
				direccion.setLocalidad(localidad);
				direccion.setCodigoPostal(codigoPostal);
				direccion.setCalle(calle);
				direccion.setPiso(piso);
				direccion.setEmail(user.getEmail());
				direccion.setNumero(numero);
				
				if(direccion.getIdprovincia()!=null || direccion.getLocalidad()!=null || direccion.getCodigoPostal()!=null 
						|| direccion.getCalle()!=null || direccion.getPiso()!=null || direccion.getNumero()!=null) {
					userv.updateDireccion(direccion);
				}
				
				if(userupdate.getNombre()!=null || userupdate.getApellido1()!=null || userupdate.getApellido2()!=null 
						|| userupdate.getTelefono()!=null || userupdate.getPassword()!=null || userupdate.getNombreUser()!=null) {
					userv.update(userupdate);
				}
				target = ControllerPaths.USUARIO+"?"+ParameterNames.ACTION+"="+Actions.PRECONFIGURACION;
				redirect=true;
			}else if(Actions.MENSAGE.equalsIgnoreCase(action)) {
				Usuario u=(Usuario) SessionManager.get(request, SessionAttributeNames.USER);
				String mensage= request.getParameter(ParameterNames.MENSAGE);
				if(u!=null) {
					mservice.sendMail("powergaming2019@gmail.com",u.getEmail(), mensage);
				}else {
					String email= request.getParameter(ParameterNames.EMAIL);
					mservice.sendMail("powergaming2019@gmail.com",email, mensage);
				}
				
				target=ViewPaths.CONTACTO;
			}else if(Actions.FORGOTPASS.equalsIgnoreCase(action)) {
				String email= request.getParameter(ParameterNames.EMAIL);
				String emailvalid=LimpiezaValidacion.validEmail(email);
				Usuario u=userv.findById(emailvalid);
				if(u==null) {
					target=ControllerPaths.USUARIO+"?"+ParameterNames.ACTION+"="+Actions.PREREGISTRO;
					redirect=true;
				}else {
					String enlace="http://localhost:8080/"+request.getContextPath()+ViewPaths.CHANGEPASS+"?"+ParameterNames.EMAIL+"="+emailvalid;
				mservice.sendMail(emailvalid,"Restauracion de contraseña", 
						"<html><h2>Power Gaming</h2><h4>Pulse en enlace o copielo entero para cambiar su contraseña:</h4>"
						+ "<a href='"+enlace+"'>Restablecer contraseña</a><p>"+enlace+"</p></html>");
					
				target=request.getContextPath()+ViewPaths.INICIO;
				redirect=true;
				}
			}else if(Actions.CHANGEPASS.equalsIgnoreCase(action)){
				String email= request.getParameter(ParameterNames.EMAIL);
				String pass= request.getParameter(ParameterNames.PASSWORD);
				Usuario u=userv.findById(email);
				
				userv.delete(u.getEmail());
			
				u.setPassword(pass);
				
				
				userv.create(u);
				
				target=request.getContextPath()+ViewPaths.LOGIN;
				redirect=true;
			} else if (Actions.CAMBIAR_IDIOMA.equalsIgnoreCase(action)) {
				String localeName = request.getParameter(ParameterNames.LOCALE);
				// Recordar que hay que validar... lo que nos envian, incluso en algo como esto.
				// Buscamos entre los Locale soportados por la web:
				List<Locale> results = LocaleManager.getMatchedLocales(localeName);
				Locale newLocale = null;
				if (results.size()>0) {
					newLocale = results.get(0);
				} else {
					logger.warn("Request locale not supported: "+localeName);
					newLocale = LocaleManager.getDefault();
				}

				SessionManager.set(request, WebConstants.USER_LOCALE, newLocale);			
				CookieManager.addCookie(response, WebConstants.USER_LOCALE, newLocale.toString(), "/", 365*24*60*60);

				if (logger.isDebugEnabled()) {
					logger.debug("Locale changed to "+newLocale);
				}

				target=request.getHeader(ViewPaths.REFERER);
				
				if (logger.isDebugEnabled()) {
					logger.debug("Locale changed to "+newLocale);
				}

				response.sendRedirect(target);
			}else {
				logger.error("Action desconocida");
				target= request.getContextPath()+ViewPaths.ERROR404;
			}
			if(!Actions.CAMBIAR_IDIOMA.equalsIgnoreCase(action)) {
				if(redirect) {
					logger.info("Redirect to "+target);
					response.sendRedirect(target);
				}else {
					logger.info("forwarding to "+target);
					request.getRequestDispatcher(target).forward(request, response);
				}
			}
			} catch (SQLException e) {	
				logger.info(e.getMessage(),e);
			} catch (DataException e) {
				logger.info(e.getMessage(),e);
			} catch (Exception e) {
				logger.info(e.getMessage(),e);
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
