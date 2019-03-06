package com.eddie.web.util;

import java.util.ArrayList;
import java.util.List;

import com.eddie.ecommerce.model.Categoria;
import com.eddie.ecommerce.model.Idioma;
import com.eddie.ecommerce.model.Plataforma;

public class ArrayUtils {
	public static List<Categoria> arrayCategoria(String [] arrayParameter){
		
			String[] categoria = arrayParameter;	
			List<Categoria> categorias = new ArrayList<Categoria>();
			for (String s: categoria) {
				Categoria c = new Categoria ();
				c.setIdCategria(Integer.valueOf(s));
				categorias.add(c);
			}
			return categorias;
		}
	
	public static List<Idioma> arrayIdioma(String [] arrayParameter){
			
			String[] idioma = arrayParameter;	
			List<Idioma> idiomas = new ArrayList<Idioma>();
			for (String c: idioma) {
				Idioma i = new Idioma ();
				i.setIdIdioma(c);
				idiomas.add(i);
			}
			return idiomas;
		}
	
	public static List<Plataforma> arrayPlataforma(String [] arrayParameter){
		
		String[] plataforma = arrayParameter;		
		List<Plataforma> lista = new ArrayList<Plataforma>();
		for (String c: plataforma) {
			Plataforma p = new Plataforma ();
			p.setIdPlatadorma(Integer.valueOf(c));
			lista.add(p);
		}
		return lista;
	}
	
}
