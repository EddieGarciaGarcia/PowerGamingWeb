package com.eddie.web.controller;

import java.io.IOException;
import java.util.Date;
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

import com.eddie.ecommerce.exceptions.DataException;
import com.eddie.ecommerce.exceptions.DuplicateInstanceException;
import com.eddie.ecommerce.model.LineaPedido;
import com.eddie.ecommerce.model.Pedido;
import com.eddie.ecommerce.model.Usuario;
import com.eddie.ecommerce.service.LineaPedidoService;
import com.eddie.ecommerce.service.PedidoService;
import com.eddie.ecommerce.service.Resultados;
import com.eddie.ecommerce.service.impl.LineaPedidoServiceImpl;
import com.eddie.ecommerce.service.impl.PedidoServiceImpl;
import com.eddie.web.config.ConfigurationManager;
import com.eddie.web.config.ConfigurationParameterNames;
import com.eddie.web.model.Carrito;
import com.eddie.web.model.LineaCarrito;
import com.eddie.web.util.SessionAttributeNames;
import com.eddie.web.util.SessionManager;
import com.eddie.web.util.WebUtils;

/**
 * Servlet implementation class HistorialServlet
 */
@WebServlet("/historial")
public class HistorialServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static int pageSize = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
						ConfigurationParameterNames.RESULTS_PAGE_SIZE_DEFAULT)); 

	private static int pagingPageCount = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
						ConfigurationParameterNames.RESULTS_PAGING_PAGE_COUNT));
	
    private PedidoService pedidoService=null;
    private LineaPedidoService lineaPedidoService=null;

    private static Logger logger = LogManager.getLogger(HistorialServlet.class);
    
    public HistorialServlet() {
        super();
        pedidoService=  new PedidoServiceImpl();
        lineaPedidoService= new LineaPedidoServiceImpl();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Carrito carrito = (Carrito) SessionManager.get(request, SessionAttributeNames.CARRITO);
		Usuario usuario= (Usuario) SessionManager.get(request, SessionAttributeNames.USER);
		Pedido pedido= new Pedido();
		LineaPedido lineaPedido=new LineaPedido();
		Boolean redirect=false;
		String action= request.getParameter(ParameterNames.ACTION);
		String target= null;
		
		if (logger.isDebugEnabled()) {
			logger.debug("Action {}: {}", action, ToStringBuilder.reflectionToString(request.getParameterMap()));
		}
		
		
		try {
			if(Actions.ADDHISTORIAL.equalsIgnoreCase(action)) {
				
				pedido.setEmail(usuario.getEmail());
				Date d= new Date();
				pedido.setFecha_pedido(new java.sql.Date(d.getTime()));
				pedido.setTotal(carrito.getTotal());
				
				
				pedidoService.create(pedido);
				pedido=pedidoService.findByEmail(usuario.getEmail());
				if(carrito.getLinea().size()<15){	
					for(LineaCarrito lineaCarrito: carrito.getLinea()) {
						lineaPedido.setIdEdicion(lineaCarrito.getEdicion().getId());
						lineaPedido.setPedido(pedido.getIdPedido());
						lineaPedido.setCantidad(1);
						lineaPedido.setPrecio(lineaCarrito.getEdicion().getPrecio());
						lineaPedidoService.create(lineaPedido);
					}
				}
				
				target = ViewPaths.REFERER;	
				
			}else if(Actions.HISTORIALPEDIDO.equalsIgnoreCase(action)) {
				
				int page = WebUtils.
						getPageNumber(request.getParameter(ParameterNames.PAGE), 1);
				
				Resultados<Pedido> resultados= pedidoService.findByEmail(usuario.getEmail(), (page-1)*pageSize+1, pageSize);
				
				List<Integer> idPedidos= resultados.getResultados().stream().map(Pedido::getIdPedido).collect(Collectors.toList());
				List<Pedido> pedidos=null;
				
				if(idPedidos!=null && !idPedidos.isEmpty()) {
					pedidos=  pedidoService.findByIds(idPedidos);
				}
				request.setAttribute(AttributeNames.LISTADO_PEDIDOS, pedidos);
				request.setAttribute(AttributeNames.TOTAL, resultados.getTotal());
				
				int totalPages = (int) Math.ceil(resultados.getTotal()/(double)pageSize);
				int firstPagedPage = Math.max(1, page-pagingPageCount);
				int lastPagedPage = Math.min(totalPages, page+pagingPageCount);
				request.setAttribute(ParameterNames.PAGE, page);
				request.setAttribute(AttributeNames.TOTAL_PAGES, totalPages);
				request.setAttribute(AttributeNames.FIRST_PAGED_PAGES, firstPagedPage);
				request.setAttribute(AttributeNames.LAST_PAGED_PAGES, lastPagedPage);
						
				target = ViewPaths.HISTORIALPEDIDO;
				
			}else if(Actions.ELIMINARPEDIDO.equalsIgnoreCase(action)) {
				String idPedido = request.getParameter(ParameterNames.ID);
				
				Integer id=Integer.valueOf(idPedido);
				
				pedidoService.delete(id);
				lineaPedidoService.deleteByPedido(id);
				
				target= ControllerPaths.HISTORIAL+"?"+ParameterNames.ACTION+"="+Actions.HISTORIALPEDIDO+"&"+ParameterNames.EMAIL+"="+usuario.getEmail();
				redirect=true;
			
			}else {
				logger.error("Action desconocida");
				target= ViewPaths.ERROR404;
			}
			if(redirect==true) {
				logger.info("Redirect to "+target);
				response.sendRedirect(target);
			}else {
				logger.info("forwarding to "+target);
				request.getRequestDispatcher(target).forward(request, response);
		}
		}catch (DuplicateInstanceException e) {
			logger.info(e.getMessage(),e);
		} catch (DataException e) {
			logger.info(e.getMessage(),e);
		}	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
