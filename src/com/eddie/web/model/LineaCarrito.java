package com.eddie.web.model;

import com.eddie.ecommerce.model.Edicion;

public class LineaCarrito {
	private Edicion edicionJuego = null;
	private double precioTotal;
	
	public LineaCarrito() {}

	public Edicion getEdicion() {
		return edicionJuego;
	}

	public void setEdicion(Edicion edicionJuego) {
		this.edicionJuego = edicionJuego;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}
	
	
}
