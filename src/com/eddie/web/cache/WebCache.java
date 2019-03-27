package com.eddie.web.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eddie.ecommerce.exceptions.DataException;
import com.eddie.ecommerce.model.Formato;
import com.eddie.ecommerce.model.TipoEdicion;
import com.eddie.ecommerce.service.FormatoService;
import com.eddie.ecommerce.service.TipoEdicionService;
import com.eddie.ecommerce.service.impl.FormatoServiceImpl;
import com.eddie.ecommerce.service.impl.TipoEdicionServiceImpl;

public class WebCache {
	
	private static Map<String, List<TipoEdicion>> tipoEdicion= 
			new HashMap<String, List<TipoEdicion>>();
	
	private static Map<String, List<Formato>> formato= 
			new HashMap<String, List<Formato>>();
	
	private TipoEdicionService tipoEdicionService= null;
	private FormatoService formatoService=null;
	List<TipoEdicion> tipoEdiciones= null;
	List<Formato> formatos=null;
	
	public WebCache() {
		tipoEdicionService= new TipoEdicionServiceImpl();
		formatoService= new FormatoServiceImpl();
	}

	public void addFormato(String idiomaPagina) throws DataException {
		formatos=formatoService.findAll(idiomaPagina);
		formato.put(idiomaPagina, formatos);
	}
	
	public void addTipoEdicion(String idiomaPagina) throws DataException {
		tipoEdiciones=tipoEdicionService.findAll(idiomaPagina);
		tipoEdicion.put(idiomaPagina, tipoEdiciones);
	}

	public static List<TipoEdicion> getTipoEdicion(String idiomaPagina) {
		return tipoEdicion.get(idiomaPagina);
	}

	public static List<Formato> getFormato(String idiomaPagina) {
		return formato.get(idiomaPagina);
	}
	
}
