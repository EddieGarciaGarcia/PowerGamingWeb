package com.eddie.web.filter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.eddie.ecommerce.exceptions.DataException;
import com.eddie.ecommerce.model.Categoria;
import com.eddie.ecommerce.service.CategoriaService;
import com.eddie.ecommerce.service.impl.CategoriaServiceImpl;

/**
 * Servlet Filter implementation class InitStaticDataFilter
 */
@WebFilter("/*")
public class InitStaticDataFilter implements Filter {

	private CategoriaService cservice=null;
	
    public InitStaticDataFilter() {
    	cservice = new CategoriaServiceImpl();
    }

	
	public void destroy() {

	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			List<Categoria> categorias= cservice.findAll("ES");
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
