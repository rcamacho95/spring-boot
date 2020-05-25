package com.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class ClienteProductosId implements Serializable{
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Cliente cliente;
	@ManyToOne(cascade = CascadeType.ALL)
	private Producto producto;

	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	private static final long serialVersionUID = 1L;

}
