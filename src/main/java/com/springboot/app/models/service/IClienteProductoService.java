package com.springboot.app.models.service;

import java.util.List;

import com.springboot.app.models.entity.ClienteProductos;

public interface IClienteProductoService {
	
	public Iterable<ClienteProductos> save(List<ClienteProductos> cp);
	
	public Long valorSecuencia();
	
}
